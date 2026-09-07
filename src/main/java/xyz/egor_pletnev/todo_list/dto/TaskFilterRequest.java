package xyz.egor_pletnev.todo_list.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TaskFilterRequest(
        @Schema(
                description = "Активация фильтрации по приоритету",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Boolean filterByPriority,

        @Schema(
                description = "Активация фильтрации по дате",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Boolean filterByDate,

        @Schema(
                description = "Активация фильтрации по статусу",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        Boolean filterByStatus
) {}