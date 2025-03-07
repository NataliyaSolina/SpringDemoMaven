package ru.examples.springdemo.services;

import ru.examples.springdemo.dtos.UserDto;

import java.util.List;

public interface UserAdminService {

    List<UserDto> getAllUser();

    UserDto patchByLogin(String login, Boolean isAdmin);
}
