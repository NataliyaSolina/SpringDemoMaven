package ru.examples.springdemo.services;

import ru.examples.springdemo.dtos.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getAllByUser();

    TaskDto getById(Long id);

    TaskDto putById(Long id, TaskDto taskDto);

    void deleteById(Long id);

    TaskDto patchById(Long id);

    TaskDto patchByIdMark(Long id);
}
