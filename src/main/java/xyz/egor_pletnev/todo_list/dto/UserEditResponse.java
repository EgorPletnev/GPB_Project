package xyz.egor_pletnev.todo_list.dto;

public record UserEditResponse(
        String login,
        String email,
        String name,
        String surname
) { }
