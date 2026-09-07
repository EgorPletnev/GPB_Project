package xyz.egor_pletnev.todo_list.service;

import xyz.egor_pletnev.todo_list.dto.UserEditRequest;
import xyz.egor_pletnev.todo_list.dto.UserEditResponse;

public interface UserService {
    UserEditResponse editUser(UserEditRequest request);
}
