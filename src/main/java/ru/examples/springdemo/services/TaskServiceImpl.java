package ru.examples.springdemo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.examples.springdemo.converters.TaskConverter;
import ru.examples.springdemo.dtos.TaskDto;
import ru.examples.springdemo.exeptions.ResourceForbiddenException;
import ru.examples.springdemo.exeptions.ResourceInternalServerErrorException;
import ru.examples.springdemo.exeptions.ResourceNotFoundException;
import ru.examples.springdemo.models.Task;
import ru.examples.springdemo.models.User;
import ru.examples.springdemo.repositories.TaskRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserServiceImpl userService;
    private final TaskConverter taskConverter;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        User user = userService.getCurrentUser();
        Task task = Task.builder()
                .user(user)
                .date(taskDto.getDate())
                .description(taskDto.getDescription())
                .done(false)
                .build();

        try {
            taskRepository.save(task);
            return taskConverter.entityToDto(task);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException("Не получилось создать");
        }
    }

    // TODO: 05.03.2025 22:36 соеденить эти 2 метода
    @Override
    public List<TaskDto> getAllByUser() {
        User user = userService.getCurrentUser();
        List<Task> taskList = (List<Task>) taskRepository.findTasksByUserIdOrderById(user.getId());

        return taskList.stream().map(taskConverter::entityToDto).toList();
    }

    public List<TaskDto> getByUserAndDone(Boolean isDone) {
        User user = userService.getCurrentUser();
        List<Task> taskList = (List<Task>) taskRepository.findTasksByUserIdAndDoneOrderById(user.getId(), isDone);

        return taskList.stream().map(taskConverter::entityToDto).toList();
    }

    @Override
    public TaskDto getById(Long id) {
        User user = userService.getCurrentUser();
        taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Таски с id = %d не найдено", id)));

        Task taskById = taskRepository.findTasksByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceForbiddenException(format("К таске с id = %d нет доступа у данного пользователя", id)));

        return taskConverter.entityToDto(taskById);
    }

    @Override
    public TaskDto putById(Long id, TaskDto taskDto) {
        Task taskOld = taskConverter.dtoToEntity(getById(id));
        Task task = Task.builder()
                .id(id)
                .date(taskDto.getDate())
                .description(taskDto.getDescription())
                .user(taskOld.getUser())
                .done(taskOld.isDone())
                .build();

        try {
            taskRepository.save(task);
            return taskConverter.entityToDto(task);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось изменить задачу с id = %d", id));
        }
    }

    @Override
    public void deleteById(Long id) {
        taskConverter.dtoToEntity(getById(id));

        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось удалить задачу с id = %d", id));
        }
    }

    @Override
    public TaskDto patchById(Long id) {
        Task task = taskConverter.dtoToEntity(getById(id));

        task.setDone(!task.isDone());

        try {
            taskRepository.save(task);
            return taskConverter.entityToDto(task);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось изменить задачу с id = %d", id));
        }
    }

    @Override
    public TaskDto patchByIdMark(Long id) {
        Task task = taskConverter.dtoToEntity(getById(id));

        task.setDone(true);

        try {
            taskRepository.save(task);
            return taskConverter.entityToDto(task);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось изменить задачу с id = %d", id));
        }
    }
}
