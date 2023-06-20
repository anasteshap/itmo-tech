package itmo.anasteshap.dto.responses;

import itmo.anasteshap.entities.models.Role;

public record UserResponse(
        Long id,
        String username,
        Role role,
        String password,
        Long ownerId) {
}
