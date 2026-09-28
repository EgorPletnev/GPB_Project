package xyz.egor_pletnev.todo_list.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.egor_pletnev.todo_list.dto.UserEditRequest;
import xyz.egor_pletnev.todo_list.dto.UserEditResponse;
import xyz.egor_pletnev.todo_list.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Редактирование данных пользователя")
    @ApiResponse(responseCode = "200", description = "Данные пользователя отредактированы")
    @PatchMapping
    public ResponseEntity<UserEditResponse> editUser(
            @Parameter(description = "Новые данные пользователя", required = true)
            @Valid
            @RequestBody
            UserEditRequest request
    ) {
        UserEditResponse response = userService.editUser(request);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @Operation(summary = "Удаление аккаунта пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь удалён")
    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        // TODO: сделать UserDeleteResponse пустым
        return ResponseEntity.ok().build();
    }
}