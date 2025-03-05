package ru.examples.springdemo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.examples.springdemo.dtos.UserDto;
import ru.examples.springdemo.services.UserAdminServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "2.AdminUsers", description = "Админка для методов работы с пользователями")
public class UserAdminController {

    private final UserAdminServiceImpl userAdminService;

    @GetMapping("/users")
    @Operation(summary = "Вывод всех пользователей",
            description = "Позволяет выести всех пользователей")
    public List<UserDto> getAllUsers() {
        return userAdminService.getAllUser();
    }

    @PatchMapping("/user/{login}")
    @Operation(summary = "Редактирование звания админ у пользователей",
            description = "Позволяет редактирование звания админ у пользователей по логину")
    public UserDto patchUserByLogin(@PathVariable String login,
     @RequestParam(name = "isAdmin", required = true) Boolean isAdmin) {
        return userAdminService.patchByLogin(login, isAdmin);
    }

}
