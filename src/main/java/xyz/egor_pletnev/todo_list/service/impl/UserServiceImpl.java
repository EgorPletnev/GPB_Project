package xyz.egor_pletnev.todo_list.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.egor_pletnev.todo_list.dto.UserEditRequest;
import xyz.egor_pletnev.todo_list.dto.UserEditResponse;
import xyz.egor_pletnev.todo_list.service.UserService;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserEditResponse editUser(UserEditRequest request) {
        return new UserEditResponse(
                request.login(),
                request.email(),
                request.name(),
                request.surname()
        );
    }
}
