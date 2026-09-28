package xyz.egor_pletnev.todo_list.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TaskDeleteRequest(
        @NotEmpty
        List<String> ids
) {}