package ru.examples.springdemo.services;

import lombok.RequiredArgsConstructor;
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

    @Override
    public List<TaskDto> getTasksByUser(Boolean isDone) {
        return isDone == null ? getAllByUserWithoutChoiceDone() : getAllByUserWithChoiceDone(isDone);
    }

    private List<TaskDto> getAllByUserWithoutChoiceDone() {
        User user = userService.getCurrentUser();
        List<Task> taskList = (List<Task>) taskRepository.findTasksByUserIdOrderById(user.getId());

        return taskList.stream().map(taskConverter::entityToDto).toList();
    }

    private List<TaskDto> getAllByUserWithChoiceDone(Boolean isDone) {
        User user = userService.getCurrentUser();
        List<Task> taskList = (List<Task>) taskRepository.findTasksByUserIdAndDoneOrderById(user.getId(), isDone);

        return taskList.stream().map(taskConverter::entityToDto).toList();
    }

    @Override
    public TaskDto getById(Long id) {
        Task task = getByIdAndUser(id);

        return taskConverter.entityToDto(task);
    }

    @Override
    public TaskDto putById(Long id, TaskDto taskDto) {
        Task taskOld = getByIdAndUser(id);
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
        getByIdAndUser(id);

        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось удалить задачу с id = %d", id));
        }
    }

    @Override
    public TaskDto patchById(Long id) {
        Task task = getByIdAndUser(id);
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
        Task task = getByIdAndUser(id);
        task.setDone(true);

        try {
            taskRepository.save(task);
            return taskConverter.entityToDto(task);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось изменить задачу с id = %d", id));
        }
    }

    private Task getByIdAndUser(Long id) {
        User user = userService.getCurrentUser();
        taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Таски с id = %d не найдено", id)));

        return taskRepository.findTasksByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceForbiddenException(format("К таске с id = %d нет доступа у данного пользователя", id)));
    }
}
