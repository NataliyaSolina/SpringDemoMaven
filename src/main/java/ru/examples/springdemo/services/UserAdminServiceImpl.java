package ru.examples.springdemo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.examples.springdemo.converters.UserConverter;
import ru.examples.springdemo.dtos.UserDto;
import ru.examples.springdemo.exeptions.ResourceForbiddenException;
import ru.examples.springdemo.exeptions.ResourceInternalServerErrorException;
import ru.examples.springdemo.models.User;
import ru.examples.springdemo.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final UserServiceImpl userService;

    @Override
    public List<UserDto> getAllUser() {
        checkAccess();
        List<User> userList = (List<User>) userRepository.findAll(Sort.by("isAdmin").descending().and(Sort.by("id").ascending()));

        return userList.stream().map(userConverter::entityToDto).toList();
    }

    @Override
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
