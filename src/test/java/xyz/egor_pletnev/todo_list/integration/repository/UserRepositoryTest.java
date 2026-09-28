package xyz.egor_pletnev.todo_list.integration.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import xyz.egor_pletnev.todo_list.models.User;
import xyz.egor_pletnev.todo_list.models.UserStatus;
import xyz.egor_pletnev.todo_list.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=update"
})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser_success() {
        User user = new User();
        user.setLogin("test_user");
        user.setEmail("test@mail.ru");
        user.setName("Egor");
        user.setPassword("password123");
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("test_user", foundUser.get().getLogin());
        assertEquals("test@mail.ru", foundUser.get().getEmail());
    }

    @Test
    void findUserById_notFound() {
        Optional<User> foundUser = userRepository.findById("unknown-id");
        assertFalse(foundUser.isPresent());
    }

    @Test
    void deleteUser_success() {
        User user = new User();
        user.setLogin("delete_user");
        user.setEmail("delete@mail.ru");
        user.setName("Ivan");
        user.setPassword("password123");
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getId());

        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertFalse(foundUser.isPresent());
    }
}