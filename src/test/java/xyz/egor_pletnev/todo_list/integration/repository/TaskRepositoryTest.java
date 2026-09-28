package xyz.egor_pletnev.todo_list.integration.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import xyz.egor_pletnev.todo_list.models.Task;
import xyz.egor_pletnev.todo_list.models.TaskStatus;
import xyz.egor_pletnev.todo_list.models.User;
import xyz.egor_pletnev.todo_list.models.UserStatus;
import xyz.egor_pletnev.todo_list.repository.TaskRepository;
import xyz.egor_pletnev.todo_list.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update"
})
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin("test_user");
        testUser.setEmail("test@mail.ru");
        testUser.setName("Egor");
        testUser.setPassword("password123");
        testUser.setStatus(UserStatus.ACTIVE);
        testUser = userRepository.save(testUser);
    }

    @Test
    void saveTask_success() {
        Task task = new Task();
        task.setUser(testUser);
        task.setTitle("Купить продукты");
        task.setPriority(1);
        task.setStatus(TaskStatus.NEW);

        Task savedTask = taskRepository.save(task);

        assertNotNull(savedTask.getId());
        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());
        assertTrue(foundTask.isPresent());
        assertEquals("Купить продукты", foundTask.get().getTitle());
    }

    @Test
    void findTasksWithOrder_byPriority() {
        Task lowPriorityTask = new Task();
        lowPriorityTask.setUser(testUser);
        lowPriorityTask.setTitle("Низкий приоритет");
        lowPriorityTask.setPriority(1);
        lowPriorityTask.setStatus(TaskStatus.NEW);

        Task highPriorityTask = new Task();
        highPriorityTask.setUser(testUser);
        highPriorityTask.setTitle("Высокий приоритет");
        highPriorityTask.setPriority(10);
        highPriorityTask.setStatus(TaskStatus.NEW);

        taskRepository.save(lowPriorityTask);
        taskRepository.save(highPriorityTask);

        List<Task> tasks = taskRepository.findTasksWithOrder(false, true, false);

        assertEquals(2, tasks.size());
        assertEquals("Высокий приоритет", tasks.get(0).getTitle());
        assertEquals("Низкий приоритет", tasks.get(1).getTitle());
    }

    @Test
    void findTasksWithOrder_byDate() {
        Task futureTask = new Task();
        futureTask.setUser(testUser);
        futureTask.setTitle("Позже");
        futureTask.setPriority(1);
        futureTask.setStatus(TaskStatus.NEW);
        futureTask.setDueDate(LocalDateTime.now().plusDays(5));

        Task earlierTask = new Task();
        earlierTask.setUser(testUser);
        earlierTask.setTitle("Раньше");
        earlierTask.setPriority(1);
        earlierTask.setStatus(TaskStatus.NEW);
        earlierTask.setDueDate(LocalDateTime.now().plusDays(1));

        taskRepository.save(futureTask);
        taskRepository.save(earlierTask);

        List<Task> tasks = taskRepository.findTasksWithOrder(false, false, true);

        assertEquals(2, tasks.size());
        assertEquals("Раньше", tasks.get(0).getTitle());
        assertEquals("Позже", tasks.get(1).getTitle());
    }
}