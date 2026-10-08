package xyz.egor_pletnev.todo_list.dto;


public record AuthResponse(
        String accessToken,
        String sessionToken,
        String tokenType
) {
    public AuthResponse(String accessToken, String sessionToken) {
        this(accessToken, sessionToken, "Bearer");
    }
}