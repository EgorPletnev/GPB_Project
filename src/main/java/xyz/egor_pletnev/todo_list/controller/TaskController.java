package xyz.egor_pletnev.todo_list.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.egor_pletnev.todo_list.dto.*;
import xyz.egor_pletnev.todo_list.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Создание задачи")
    @ApiResponse(responseCode = "200", description = "Задача успешно создана")
    @PostMapping
    public ResponseEntity<TaskCreateResponse> createTask(
            @Parameter(description = "Новая задача", required = true)
            @Valid
            @RequestBody
            TaskCreateRequest request
    ) {
        return ResponseEntity
                .ok()
                .body(taskService.createTask(request));
    }

    @Operation(summary = "Редактирование задачи по id")
    @ApiResponse(responseCode = "200", description = "Задача успешно отредактирована")
    @PatchMapping("/{id}")
    public ResponseEntity<TaskEditResponse> editTask(
            @Parameter(description = "Отредактированные данные задачи", required = true)
            @Valid
            @RequestBody
            TaskEditRequest request
    ) {
        return ResponseEntity
                .ok()
                .body(taskService.editTask(request));
    }

    @Operation(summary = "Удаление задачи по id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @DeleteMapping
    public ResponseEntity<TaskDeleteResponse> deleteTasks(
            @Parameter(description = "Список id задач для удаления", required = true)
            @Valid
            @RequestBody
            TaskDeleteRequest request
    ) {
        return ResponseEntity
                .ok()
                .body(taskService.deleteTasks(request));
    }

    @Operation(summary = "Возвращает задачи отсортированные по фильтрам")
    @ApiResponse(responseCode = "200", description = "Задачи успешно возвращены")
    @GetMapping
    public ResponseEntity<GetTasksResponse> getAllTasks(
            @Valid
            @ModelAttribute
            TaskFilterRequest filter
    ) {
        return ResponseEntity
                .ok()
                .body(taskService.getTasks(filter));
    }
}