package ru.examples.springdemo.converters;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.examples.springdemo.dtos.UserDto;
import ru.examples.springdemo.models.User;

import java.lang.reflect.Field;

@Component
public class UserConverter {

    public UserDto entityToDto(User user){

        return UserDto.builder()
                .login(user.getLogin())
                .password("*****")
                .isAdmin(user.getIsAdmin())
                .build();
    }

    public User dtoToEntity(UserDto userDto){

        return User.builder()
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .isAdmin(userDto.getIsAdmin())
                .build();
    }

//    UserDto userDto = new UserDto();
//
//    @SneakyThrows
//    @Override
//    public <UserDto, User> UserDto entityToDto(User obj) {
//
//        Field f = obj.getClass().getDeclaredField("login");
//        f.setAccessible(true);
//        String str = f.get(obj).toString();
//
//        userDto.setLogin(str);
//        userDto.setPassword("*****");
//
//        return (UserDto) userDto;
//    }
//
//    @Override
//    public <D, T> T dtoToEntity(D objDto) {
//        return null;
//    }
}
