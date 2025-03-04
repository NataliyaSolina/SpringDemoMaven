package ru.examples.springdemo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.examples.springdemo.converters.UserConverter;
import ru.examples.springdemo.dtos.UserDto;
import ru.examples.springdemo.handlers.CustomAuthenticationSuccessHandler;
import ru.examples.springdemo.models.User;
import ru.examples.springdemo.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final UserConverter userConverter;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userConverter.dtoToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return userConverter.entityToDto(user);
    }

    @Override
    public User getCurrentUser() {

        return authenticationSuccessHandler.getUser();
    }

    @Override
    public UserDto getCurrentUserDto() {

        return userConverter.entityToDto(getCurrentUser());

    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
