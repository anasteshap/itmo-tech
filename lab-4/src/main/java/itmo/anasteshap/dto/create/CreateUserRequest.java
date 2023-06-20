package itmo.anasteshap.dto.create;

import itmo.anasteshap.entities.models.Role;

public record CreateUserRequest(
        String username,
        Role role,
        String password,
        Long ownerId) {
}
