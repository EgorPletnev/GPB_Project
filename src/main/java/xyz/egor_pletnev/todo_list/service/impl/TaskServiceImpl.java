package xyz.egor_pletnev.todo_list.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.egor_pletnev.todo_list.TaskMapper;
import xyz.egor_pletnev.todo_list.dto.*;
import xyz.egor_pletnev.todo_list.manager.WeatherManager;
import xyz.egor_pletnev.todo_list.models.Task;
import xyz.egor_pletnev.todo_list.models.TaskStatus;
import xyz.egor_pletnev.todo_list.models.User;
import xyz.egor_pletnev.todo_list.repository.TaskRepository;
import xyz.egor_pletnev.todo_list.repository.UserRepository;
import xyz.egor_pletnev.todo_list.service.TaskService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final WeatherManager weatherManager;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskCreateResponse createTask(TaskCreateRequest request) {
        User user = userRepository
                .findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден: " + request.userId()));

        Task savedTask = new Task();
        savedTask.setUser(user);
        savedTask.setTitle(request.title());
        savedTask.setDescription(request.description());
        savedTask.setPriority(request.priority());
        savedTask.setStatus(TaskStatus.NEW);
        savedTask.setIsOutdoor(request.isOutdoor());
        savedTask.setDueDate(request.dueDate());
        savedTask.setLatitude(request.latitude());
        savedTask.setLongitude(request.longitude());
        savedTask.setMinTemp(request.minTemp());
        savedTask.setMaxTemp(request.maxTemp());
        savedTask.setRequiredWeather(request.requiredWeather());
        savedTask.setForecastWeather(request.forecastWeather());
        savedTask.setForecastTemp(request.forecastTemp());

        savedTask = taskRepository.save(savedTask);

        return new TaskCreateResponse(
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus(),
                savedTask.getCreatedAt(),
                savedTask.getIsOutdoor(),
                savedTask.getForecastWeather(),
                savedTask.getForecastTemp(),
                savedTask.getMinTemp(),
                savedTask.getMaxTemp(),
                savedTask.getRequiredWeather()
        );
    }

    @Override
    @Transactional
    public TaskEditResponse editTask(TaskEditRequest request) {
        Task editTask = taskRepository
                .findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена: " + request.id()));

        if (request.title() != null) {
            editTask.setTitle(request.title());
        }
        if (request.description() != null) {
            editTask.setDescription(request.description());
        }
        if (request.priority() != null) {
            editTask.setPriority(request.priority());
        }
        if (request.dueDate() != null) {
            editTask.setDueDate(request.dueDate());
        }
        if (request.isOutdoor() != null) {
            editTask.setIsOutdoor(request.isOutdoor());
        }
        if (request.latitude() != null) {
            editTask.setLatitude(request.latitude());
        }
        if (request.longitude() != null) {
            editTask.setLongitude(request.longitude());
        }
        if (request.minTemp() != null) {
            editTask.setMinTemp(request.minTemp());
        }
        if (request.maxTemp() != null) {
            editTask.setMaxTemp(request.maxTemp());
        }
        if (request.requiredWeather() != null) {
            editTask.setRequiredWeather(request.requiredWeather());
        }

        // Проверка на уличную задачу: если задача уличная, проверяет наличие данных о текущей погоде
        // (если есть, то задаёт их, если нет — запрашивает у сервиса погоды)
        if (Boolean.TRUE.equals(editTask.getIsOutdoor())) {
            if (request.forecastWeather() != null && request.forecastTemp() != null) {
                editTask.setForecastWeather(request.forecastWeather());
                editTask.setForecastTemp(request.forecastTemp());
            } else {
                WeatherResponse weatherResponse = weatherManager.getWeather(
                        editTask.getLatitude(),
                        editTask.getLongitude(),
                        editTask.getDueDate()
                );

                editTask.setForecastWeather(weatherResponse.condition());
                editTask.setForecastTemp(weatherResponse.temperature());
            }
        }

        Task savedTask = taskRepository.save(editTask);

        return new TaskEditResponse(
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getIsOutdoor(),
                savedTask.getPriority(),
                savedTask.getLatitude(),
                savedTask.getLongitude(),
                savedTask.getMinTemp(),
                savedTask.getMaxTemp(),
                savedTask.getRequiredWeather(),
                savedTask.getDueDate()
        );
    }

    @Override
    @Transactional
    public TaskDeleteResponse deleteTasks(TaskDeleteRequest request) {
        // TODO: через некоторое время задачи со статусом DELETED удалять из бд
        List<Task> tasksToDelete = taskRepository.findAllById(request.ids());

        tasksToDelete.forEach(task -> task.setStatus(TaskStatus.DELETED));

        List<Task> savedTasks = taskRepository.saveAll(tasksToDelete);

        List<String> deletedIds = savedTasks.stream()
                .map(Task::getId)
                .toList();

        return new TaskDeleteResponse(deletedIds);
    }

    @Override
    @Transactional(readOnly = true)
    public GetTasksResponse getTasks(TaskFilterRequest request) {
        List<Task> tasks = taskRepository.findTasksWithOrder(
                request.filterByStatus(),
                request.filterByPriority(),
                request.filterByDate()
        );

        List<TaskResponse> taskResponses = tasks.stream()
                .map(taskMapper::taskToTaskResponseDTO)
                .toList();

        return new GetTasksResponse(taskResponses);
    }
}