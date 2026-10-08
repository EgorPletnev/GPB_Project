package xyz.egor_pletnev.todo_list.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank
        String email,

        @NotBlank
        String password,

        Boolean rememberMe
) {}