package ru.examples.springdemo.services;

import ru.examples.springdemo.dtos.TaskDto;

import java.util.List;

public interface TaskAdminService {

    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getTasks(Boolean isDone);

    TaskDto putById(Long id, TaskDto taskDto);

    void deleteById(Long id);

    TaskDto patchById(Long id);
}
