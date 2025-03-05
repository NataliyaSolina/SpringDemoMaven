package ru.examples.springdemo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.examples.springdemo.converters.UserConverter;
import ru.examples.springdemo.dtos.UserDto;
import ru.examples.springdemo.exeptions.ResourceForbiddenException;
import ru.examples.springdemo.exeptions.ResourceInternalServerErrorException;
import ru.examples.springdemo.handlers.CustomAuthenticationSuccessHandler;
import ru.examples.springdemo.models.User;
import ru.examples.springdemo.repositories.UserRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final UserConverter userConverter;
    private final UserServiceImpl userService;


    public List<UserDto> getAllUser() {
        checkAccess();
        List<User> userList = (List<User>) userRepository.findAll(Sort.by("isAdmin").descending().and(Sort.by("id").ascending()));

        return userList.stream().map(userConverter::entityToDto).toList();
    }

    public UserDto patchByLogin(String login, Boolean isAdmin) {
        checkAccess();
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        user.setIsAdmin(isAdmin);

        try {
            userRepository.save(user);
            return userConverter.entityToDto(user);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException("Не получилось изменить пользователя");
        }
    }

    private User checkAccess() {
        User user = userService.getCurrentUser();
        if (!user.getIsAdmin()) {
            throw new ResourceForbiddenException("К админке нет доступа у данного пользователя");
        }
        return user;
    }
}
