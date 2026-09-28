package xyz.egor_pletnev.todo_list.unit.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import xyz.egor_pletnev.todo_list.dto.UserEditRequest;
import xyz.egor_pletnev.todo_list.dto.UserEditResponse;
import xyz.egor_pletnev.todo_list.models.User;
import xyz.egor_pletnev.todo_list.repository.UserRepository;
import xyz.egor_pletnev.todo_list.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void editUser_success() {
        User existingUser = new User();
        existingUser.setId("user-1");
        existingUser.setLogin("old_login");
        existingUser.setEmail("old@mail.ru");
        existingUser.setName("OldName");
        existingUser.setSurname("OldSurname");

        UserEditRequest request = new UserEditRequest(
                "user-1",
                "new_login",
                "new@mail.ru",
                "NewName",
                "NewSurname"
        );

        when(userRepository.findById("user-1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserEditResponse response = userService.editUser(request);

        assertNotNull(response);
        assertEquals("new_login", response.login());
        assertEquals("new@mail.ru", response.email());
        assertEquals("NewName", response.name());
        assertEquals("NewSurname", response.surname());
        verify(userRepository).save(existingUser);
    }

    @Test
    void editUser_partialUpdate_success() {
        User existingUser = new User();
        existingUser.setId("user-1");
        existingUser.setLogin("original_login");
        existingUser.setEmail("original@mail.ru");
        existingUser.setName("OriginalName");
        existingUser.setSurname("OriginalSurname");

        UserEditRequest request = new UserEditRequest(
                "user-1",
                null,
                null,
                "UpdatedName",
                null
        );

        when(userRepository.findById("user-1")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserEditResponse response = userService.editUser(request);

        assertNotNull(response);
        assertEquals("original_login", response.login());
        assertEquals("original@mail.ru", response.email());
        assertEquals("UpdatedName", response.name());
        assertEquals("OriginalSurname", response.surname());
        verify(userRepository).save(existingUser);
    }

    @Test
    void editUser_userNotFound_throwsException() {
        UserEditRequest request = new UserEditRequest(
                "unknown-id",
                "login",
                "email@mail.ru",
                "Name",
                "Surname"
        );

        when(userRepository.findById("unknown-id")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.editUser(request));
        verify(userRepository, never()).save(any());
    }
}