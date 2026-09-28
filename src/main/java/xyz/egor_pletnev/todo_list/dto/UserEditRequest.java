package xyz.egor_pletnev.todo_list.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserEditRequest(
        @NotBlank
        String id,

        @Size(max = 50)
        String login,

        @Email
        @Size(max = 100)
        String email,

        @Size(max = 50)
        String name,

        @Size(max = 50)
        String surname
) {}