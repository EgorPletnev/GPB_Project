package xyz.egor_pletnev.todo_list.unit.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.egor_pletnev.todo_list.TaskMapper;
import xyz.egor_pletnev.todo_list.dto.*;
import xyz.egor_pletnev.todo_list.manager.WeatherManager;
import xyz.egor_pletnev.todo_list.models.Task;
import xyz.egor_pletnev.todo_list.models.TaskStatus;
import xyz.egor_pletnev.todo_list.models.User;
import xyz.egor_pletnev.todo_list.models.WeatherStatus;
import xyz.egor_pletnev.todo_list.repository.TaskRepository;
import xyz.egor_pletnev.todo_list.repository.UserRepository;
import xyz.egor_pletnev.todo_list.service.impl.TaskServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private WeatherManager weatherManager;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User testUser;
    private LocalDateTime testDueDate;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId("user-uuid-1");
        testUser.setLogin("test_user");
        testUser.setEmail("test@example.com");
        testUser.setName("Egor");

        testDueDate = LocalDateTime.of(2026, 10, 15, 14, 0);
    }

    @Test
    void createTask_success() {
        TaskCreateRequest request = new TaskCreateRequest(
                "user-uuid-1", 1, "Купить продукты", "Молоко, хлеб",
                false, null, null, null, null, null, testDueDate, null, null
        );

        when(userRepository.findById("user-uuid-1")).thenReturn(Optional.of(testUser));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskCreateResponse response = taskService.createTask(request);

        assertNotNull(response);
        assertEquals("Купить продукты", response.title());
        assertEquals("Молоко, хлеб", response.description());
        assertEquals(TaskStatus.NEW, response.taskStatus());
        assertFalse(response.isOutdoor());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_userNotFound() {
        TaskCreateRequest request = new TaskCreateRequest(
                "non-existent-user", 1, "Задача", "Описание",
                false, null, null, null, null, null, testDueDate, null, null
        );

        when(userRepository.findById("non-existent-user")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.createTask(request));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void editTask_taskNotFound() {
        TaskEditRequest request = new TaskEditRequest(
                "unknown-task-id", 2, "Новый заголовок", null, null,
                null, null, null, null, null, null, null, null
        );

        when(taskRepository.findById("unknown-task-id")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.editTask(request));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void editTask_indoorTask_success() {
        Task existingTask = new Task();
        existingTask.setId("task-1");
        existingTask.setTitle("Старый заголовок");
        existingTask.setDescription("Старое описание");
        existingTask.setPriority(1);
        existingTask.setIsOutdoor(false);

        TaskEditRequest request = new TaskEditRequest(
                "task-1", null, "Новый заголовок", null, null,
                null, null, null, null, null, null, null, null
        );

        when(taskRepository.findById("task-1")).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskEditResponse response = taskService.editTask(request);

        assertNotNull(response);
        assertEquals("Новый заголовок", response.title());
        assertEquals("Старое описание", response.description());
        assertEquals(1, response.priority());
        verify(weatherManager, never()).getWeather(any(), any(), any());
    }

    @Test
    void editTask_outdoorWithForecast_success() {
        Task existingTask = new Task();
        existingTask.setId("task-1");
        existingTask.setIsOutdoor(false);

        TaskEditRequest request = new TaskEditRequest(
                "task-1", 3, "Пробежка", "В парке", true,
                55.75, 37.61, 10.0, 25.0, WeatherStatus.CODE_0, testDueDate,
                WeatherStatus.CODE_1, 18.5
        );

        when(taskRepository.findById("task-1")).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        taskService.editTask(request);

        assertTrue(existingTask.getIsOutdoor());
        assertEquals(WeatherStatus.CODE_1, existingTask.getForecastWeather());
        assertEquals(18.5, existingTask.getForecastTemp());
        verifyNoInteractions(weatherManager);
    }

    @Test
    void editTask_outdoorWithoutForecast_callsWeatherManager() {
        Task existingTask = new Task();
        existingTask.setId("task-1");
        existingTask.setIsOutdoor(false);

        TaskEditRequest request = new TaskEditRequest(
                "task-1", null, "Прогулка", null, true,
                59.93, 30.31, null, null, null, testDueDate, null, null
        );

        WeatherResponse mockWeather = new WeatherResponse(
                59.93, 30.31, testDueDate, WeatherStatus.CODE_2, 15.0
        );

        when(taskRepository.findById("task-1")).thenReturn(Optional.of(existingTask));
        when(weatherManager.getWeather(59.93, 30.31, testDueDate)).thenReturn(mockWeather);
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        taskService.editTask(request);

        verify(weatherManager).getWeather(59.93, 30.31, testDueDate);
        assertEquals(WeatherStatus.CODE_2, existingTask.getForecastWeather());
        assertEquals(15.0, existingTask.getForecastTemp());
    }

    @Test
    void deleteTasks_success() {
        Task task1 = new Task();
        task1.setId("task-id-1");
        task1.setStatus(TaskStatus.NEW);

        Task task2 = new Task();
        task2.setId("task-id-2");
        task2.setStatus(TaskStatus.IN_PROGRESS);

        List<String> idsToDelete = List.of("task-id-1", "task-id-2");
        TaskDeleteRequest request = new TaskDeleteRequest(idsToDelete);

        when(taskRepository.findAllById(idsToDelete)).thenReturn(List.of(task1, task2));
        when(taskRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        TaskDeleteResponse response = taskService.deleteTasks(request);

        assertNotNull(response);
        assertEquals(2, response.ids().size());
        assertEquals(TaskStatus.DELETED, task1.getStatus());
        assertEquals(TaskStatus.DELETED, task2.getStatus());
        verify(taskRepository).saveAll(List.of(task1, task2));
    }

    @Test
    void getTasks_success() {
        TaskFilterRequest filterRequest = new TaskFilterRequest(true, false, true);

        Task task1 = new Task();
        task1.setId("task-1");
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setId("task-2");
        task2.setTitle("Task 2");

        TaskResponse responseDto1 = new TaskResponse(
                "task-1", "Task 1", null, 1, TaskStatus.NEW, false,
                null, null, null, null, null, null, null, null, null
        );
        TaskResponse responseDto2 = new TaskResponse(
                "task-2", "Task 2", null, 2, TaskStatus.DONE, false,
                null, null, null, null, null, null, null, null, null
        );

        when(taskRepository.findTasksWithOrder(true, true, false))
                .thenReturn(List.of(task1, task2));
        when(taskMapper.taskToTaskResponseDTO(task1)).thenReturn(responseDto1);
        when(taskMapper.taskToTaskResponseDTO(task2)).thenReturn(responseDto2);

        GetTasksResponse result = taskService.getTasks(filterRequest);

        assertNotNull(result);
        assertEquals(2, result.tasks().size());
        assertEquals(responseDto1, result.tasks().get(0));
        assertEquals(responseDto2, result.tasks().get(1));

        verify(taskRepository).findTasksWithOrder(true, true, false);
        verify(taskMapper, times(2)).taskToTaskResponseDTO(any(Task.class));
    }
}