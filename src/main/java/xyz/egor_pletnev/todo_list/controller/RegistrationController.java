package xyz.egor_pletnev.todo_list.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.egor_pletnev.todo_list.dto.AuthRequest;
import xyz.egor_pletnev.todo_list.dto.AuthResponse;
import xyz.egor_pletnev.todo_list.dto.RegisterRequest;
import xyz.egor_pletnev.todo_list.service.AuthService;

@Tag(name = "Регистрация и Авторизация")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final AuthService authService;

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid
            @RequestBody
            RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Вход в систему")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid
            @RequestBody
            AuthRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Получение нового JWT по токену сессии")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @RequestParam
            String sessionToken
    ) {
        return ResponseEntity.ok(authService.refresh(sessionToken));
    }
}