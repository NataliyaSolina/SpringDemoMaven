package ru.examples.springdemo.service;

import ru.examples.springdemo.model.User;

public interface UserService {

    User save(User user);

    User getCurrentUser();

}
