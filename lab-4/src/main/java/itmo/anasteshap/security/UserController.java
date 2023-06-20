package itmo.anasteshap.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import itmo.anasteshap.dto.create.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(userService.save(createUserRequest), HttpStatus.CREATED);
    }
}
