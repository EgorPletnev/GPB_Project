package xyz.egor_pletnev.todo_list.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.egor_pletnev.todo_list.dto.AuthRequest;
import xyz.egor_pletnev.todo_list.dto.AuthResponse;
import xyz.egor_pletnev.todo_list.dto.RegisterRequest;
import xyz.egor_pletnev.todo_list.models.User;
import xyz.egor_pletnev.todo_list.models.UserStatus;
import xyz.egor_pletnev.todo_list.repository.UserRepository;
import xyz.egor_pletnev.todo_list.security.JwtService;
import xyz.egor_pletnev.todo_list.service.AuthService;
import xyz.egor_pletnev.todo_list.service.SessionService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SessionService sessionService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }
        if (userRepository.existsByLogin(request.login())) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует");
        }

        User user = new User();
        user.setLogin(request.login());
        user.setEmail(request.email());
        user.setName(request.name());
        user.setSurname(request.surname());

        user.setPassword(passwordEncoder.encode(request.password()));
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        String accessToken = jwtService.generateToken(user);
        return new AuthResponse(accessToken, null);
    }

    @Override
    @Transactional
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        String accessToken = jwtService.generateToken(user);
        String sessionToken = null;

        if (Boolean.TRUE.equals(request.rememberMe())) {
            sessionToken = sessionService.createSession(user);
        }

        return new AuthResponse(accessToken, sessionToken);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse refresh(String sessionToken) {
        User user = sessionService.validateAndGetUser(sessionToken);
        String newAccessToken = jwtService.generateToken(user);

        return new AuthResponse(newAccessToken, sessionToken);
    }
}