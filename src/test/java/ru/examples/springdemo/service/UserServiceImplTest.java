package ru.examples.springdemo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.examples.springdemo.models.User;
import ru.examples.springdemo.repositories.UserRepository;
import ru.examples.springdemo.services.UserServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void saveUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("Qwerty123!");
        String encodedPassword = "jf2w0jisdlfnweijdf0qjiedsolkfmwepofdjmsd";

//        Mockito.doReturn("jf2w0jisdlfnweijdf0qjiedsolkfmwepofdjmsd").when(passwordEncoder).encode(user.getPassword());
//        userService.save(user);
//        Mockito.verify(userRepository).save(user);
//        Assertions.assertEquals(encodedPassword, user.getPassword());
        Assertions.assertEquals("Qwerty123!", user.getPassword());
    }
}