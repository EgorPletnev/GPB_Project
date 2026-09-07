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
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Стоит ли разделить смену email и других параметров?
    @ApiResponse(responseCode = "200", description = "Данные пользователя отредактированы")
    @Operation(summary = "Редактирование данных пользователя")
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


    @ApiResponse(responseCode = "200", description = "Пользователь удалён")
    @Operation(summary = "Удаление аккаунта пользователя")
    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        return ResponseEntity.ok().build();
    } // TODO: сделать UserDeleteResponse пустым
}
