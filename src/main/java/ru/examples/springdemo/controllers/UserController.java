package ru.examples.springdemo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.examples.springdemo.dtos.UserDto;
import ru.examples.springdemo.services.UserServiceImpl;

// TODO: 05.03.2025 21:37 может быть получиться смена пароля, но не придумала реализацию
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "1.Users", description = "Методы для работы с пользователями")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/users")
    @Operation(summary = "Регистрация пользователя",
            description = "Позволяет создать пользователя")
    public UserDto create(@RequestBody UserDto userDto) {
        log.debug("Post /user user {}", userDto);

        return userService.createUser(userDto);
    }

    @GetMapping("/users/me")
    @Operation(summary = "Вывод текущего пользователя",
            description = "Позволяет получить текущего пользователя")
    public UserDto getCurrentUser() {
        log.debug("Get /users/me");

        return userService.getCurrentUserDto();
    }
}
