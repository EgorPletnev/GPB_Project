package xyz.egor_pletnev.todo_list.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.egor_pletnev.todo_list.dto.UserEditRequest;
import xyz.egor_pletnev.todo_list.dto.UserEditResponse;
import xyz.egor_pletnev.todo_list.models.User;
import xyz.egor_pletnev.todo_list.repository.UserRepository;
import xyz.egor_pletnev.todo_list.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEditResponse editUser(UserEditRequest request) {
        User user = userRepository
                .findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден: " + request.id()));

        if (request.login() != null) {
            user.setLogin(request.login());
        }
        if (request.email() != null) {
            user.setEmail(request.email());
        }
        if (request.name() != null) {
            user.setName(request.name());
        }
        if (request.surname() != null) {
            user.setSurname(request.surname());
        }

        User savedUser = userRepository.save(user);

        return new UserEditResponse(
                savedUser.getLogin(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getSurname()
        );
    }
}
