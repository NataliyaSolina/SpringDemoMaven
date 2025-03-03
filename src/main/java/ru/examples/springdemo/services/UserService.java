package ru.examples.springdemo.services;

import ru.examples.springdemo.models.User;

public interface UserService {

    User save(User user);

    User getCurrentUser();

}
