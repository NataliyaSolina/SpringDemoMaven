package ru.examples.springdemo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.examples.springdemo.handlers.CustomAuthenticationSuccessHandler;
import ru.examples.springdemo.models.User;
import ru.examples.springdemo.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        return authenticationSuccessHandler.getUser();

    }
}
