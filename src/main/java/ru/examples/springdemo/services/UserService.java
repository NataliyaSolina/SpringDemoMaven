package ru.examples.springdemo.services;

import ru.examples.springdemo.dtos.UserDto;
import ru.examples.springdemo.models.User;

public interface UserService {

    UserDto createUser(UserDto userDto);

    User getCurrentUser();

    UserDto getCurrentUserDto();

    User getUserByLogin(String login);
}
