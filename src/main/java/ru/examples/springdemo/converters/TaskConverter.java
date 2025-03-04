package ru.examples.springdemo.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.examples.springdemo.dtos.TaskDto;
import ru.examples.springdemo.models.Task;
import ru.examples.springdemo.services.UserServiceImpl;

@Component
@RequiredArgsConstructor
public class TaskConverter {

    private final UserServiceImpl userService;

    public TaskDto entityToDto(Task task){

        return TaskDto.builder()
                .id(task.getId())
                .userLogin(task.getUser().getLogin())
                .description(task.getDescription())
                .date(task.getDate())
                .done(task.isDone())
                .build();
    }

    public Task dtoToEntity(TaskDto taskDto){

        return Task.builder()
                .id(taskDto.getId())
                .user(userService.getUserByLogin(taskDto.getUserLogin()))
                .description(taskDto.getDescription())
                .date(taskDto.getDate())
                .done(taskDto.isDone())
                .build();
    }
}
