package ru.petrov.user.dto;

public record UserResponse(
        Long id,
        String keycloakUserId,
        String email,
        String displayName
) {
}
