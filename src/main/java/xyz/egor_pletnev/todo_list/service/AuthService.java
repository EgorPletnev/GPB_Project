package xyz.egor_pletnev.todo_list.service;

import xyz.egor_pletnev.todo_list.dto.AuthRequest;
import xyz.egor_pletnev.todo_list.dto.AuthResponse;
import xyz.egor_pletnev.todo_list.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
    AuthResponse refresh(String sessionToken);
}