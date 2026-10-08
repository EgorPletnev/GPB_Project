package xyz.egor_pletnev.todo_list.service;

import xyz.egor_pletnev.todo_list.models.User;


public interface SessionService {
    String createSession(User user);
    User validateAndGetUser(String sessionToken);
    void invalidateSession(String sessionToken);
}