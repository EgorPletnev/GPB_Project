package xyz.egor_pletnev.todo_list.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.egor_pletnev.todo_list.models.Session;
import xyz.egor_pletnev.todo_list.models.User;
import xyz.egor_pletnev.todo_list.repository.SessionRepository;
import xyz.egor_pletnev.todo_list.service.SessionService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    public String createSession(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setSessionToken(UUID.randomUUID().toString());
        session.setExpiresAt(LocalDateTime.now().plusDays(90));
        session.setUpdateAt(LocalDateTime.now());

        sessionRepository.save(session);
        return session.getSessionToken();
    }

    @Transactional(readOnly = true)
    public User validateAndGetUser(String sessionToken) {
        Session session = sessionRepository.findBySessionToken(sessionToken)
                .orElseThrow(() -> new IllegalArgumentException("Сессия не найдена или недействительна"));

        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Срок действия сессии истек");
        }

        return session.getUser();
    }

    @Transactional
    public void invalidateSession(String sessionToken) {
        sessionRepository.deleteBySessionToken(sessionToken);
    }
}