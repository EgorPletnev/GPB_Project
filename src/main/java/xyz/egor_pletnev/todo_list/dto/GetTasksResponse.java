package xyz.egor_pletnev.todo_list.dto;

import java.util.List;

public record GetTasksResponse(
        List<TaskResponse> tasks
) {}