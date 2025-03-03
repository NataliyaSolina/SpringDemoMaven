package ru.examples.springdemo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.examples.springdemo.models.User;
import ru.examples.springdemo.services.UserServiceImpl;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Users", description = "Методы для работы с пользователями")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/users")
    @Operation(summary = "Регистрация пользователя",
            description = "Позволяет создать пользователя")
    public User create(@RequestBody User user) {
        log.debug("Post /user user {}", user);
        return userService.save(user);
    }

    @GetMapping("/users/me")
    @Operation(summary = "Вывод текущего пользователя",
            description = "Позволяет получить текущего пользователя")
    public User getCurrentUser() {
        log.debug("Get /users/me");
        return userService.getCurrentUser();
    }
}
