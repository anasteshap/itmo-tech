package itmo.anasteshap.security;

import itmo.anasteshap.dto.create.CreateUserRequest;
import itmo.anasteshap.dto.responses.UserResponse;
import itmo.anasteshap.entities.User;
import itmo.anasteshap.repositories.OwnerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OwnerRepository ownerRepository) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.modelMapper = new ModelMapper();
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        User user = userRepository.findByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException(name);
        }
        return user; // создать DTO
    }

    public UserResponse save(CreateUserRequest createUserRequest) {
        var owner = ownerRepository
                .findById(createUserRequest.ownerId())
                .orElseThrow(RuntimeException::new);

        var userEntity = new User(
                createUserRequest.username(),
                encoder.encode(createUserRequest.password()),
                createUserRequest.role());
        userEntity.setOwner(owner);

        userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserResponse.class);
    }
}
