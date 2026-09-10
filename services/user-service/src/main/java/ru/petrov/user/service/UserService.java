package ru.petrov.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import ru.petrov.user.dto.UserResponse;
import ru.petrov.user.entity.User;
import ru.petrov.user.repository.UserRepository;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getCurrentUser(Jwt jwt) {

        String keycloakUserId = jwt.getSubject();

        User user = userRepository
                .findByKeycloakUserId(keycloakUserId)
                .orElseGet(() -> createUser(jwt));

        return new UserResponse(
                user.getId(),
                user.getKeycloakUserId(),
                user.getEmail(),
                user.getDisplayName()
        );
    }

    private User createUser(Jwt jwt) {

        OffsetDateTime now = OffsetDateTime.now();

        User user = new User();

        user.setKeycloakUserId(jwt.getSubject());
        user.setEmail(jwt.getClaimAsString("email"));
        user.setDisplayName(resolveDisplayName(jwt));
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }

    private String resolveDisplayName(Jwt jwt) {

        String preferredUsername =
                jwt.getClaimAsString("preferred_username");

        if (preferredUsername != null) {
            return preferredUsername;
        }

        return jwt.getClaimAsString("email");
    }
}
