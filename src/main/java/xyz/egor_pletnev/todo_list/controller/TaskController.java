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


    @ApiResponse(responseCode = "200", description = "Задача успешно создана")
    @Operation(summary = "Создание задачи")
    @PostMapping
    public ResponseEntity<TaskCreateResponse> createTask(
            @Parameter(description = "Новая задача", required = true)
            @RequestBody
            TaskCreateRequest request
    ) {

        return ResponseEntity
                .ok()
                .body(taskService.createTask(request));
    }


    @ApiResponse(responseCode = "200", description = "Задача успешно отредактирована")
    @Operation(summary = "Редактирование задачи по id")
    @PatchMapping("/{id}")
    public ResponseEntity<TaskEditResponse> editTask(
            @Parameter(description = "Отредактированные данные задачи", required = true)
            @RequestBody
            TaskEditRequest request
    ) {
        return ResponseEntity
                .ok()
                .body(taskService.editTask(request));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @Operation(summary = "Удаление задачи по id")
    @DeleteMapping
    public ResponseEntity<TaskDeleteResponse> deleteTasks(
            @Parameter(description = "Список id задач для удаления", required = true)
            @RequestBody
            TaskDeleteRequest request
    ) {
        return ResponseEntity
                .ok()
                .body(taskService.deleteTasks(request));
    }


    @ApiResponse(responseCode = "200", description = "Задачи успешно возвращены")
    @Operation(summary = "Возвращает задачи отсортированные по фильтрам")
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
