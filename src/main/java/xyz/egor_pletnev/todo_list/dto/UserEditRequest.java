package xyz.egor_pletnev.todo_list.dto;

import jakarta.validation.constraints.NotBlank;

public record UserEditRequest (
        @NotBlank
        String id,

        String login,
        String email,
        String name,
        String surname
) { }
