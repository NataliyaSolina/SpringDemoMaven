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
public class TaskAdminServiceImpl implements TaskAdminService{

    private final TaskRepository taskRepository;
    private final UserServiceImpl userService;
    private final TaskConverter taskConverter;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        User user = checkAccess();
        Task task = Task.builder()
                .user((taskDto.getUserLogin() == null) ? user : userService.getUserByLogin(taskDto.getUserLogin()))
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
    public List<TaskDto> getTasks(Boolean isDone) {
        checkAccess();
        return isDone == null ? getAllWithoutChoiceDone() : getAllWithChoiceDone(isDone);
    }

    private List<TaskDto> getAllWithoutChoiceDone() {
        List<Task> taskList = (List<Task>) taskRepository.findAll(Sort.by("id").ascending());

        return taskList.stream().map(taskConverter::entityToDto).toList();
    }

    private List<TaskDto> getAllWithChoiceDone(Boolean isDone) {
        List<Task> taskList = (List<Task>) taskRepository.findTasksByDoneOrderById(isDone);

        return taskList.stream().map(taskConverter::entityToDto).toList();
    }

    @Override
    public TaskDto putById(Long id, TaskDto taskDto) {
        checkAccess();
        Task taskOld = getTaskById(id);
        Task task = Task.builder()
                .id(id)
                .date(taskDto.getDate())
                .description(taskDto.getDescription())
                .user((taskDto.getUserLogin() == null) ? taskOld.getUser() : userService.getUserByLogin(taskDto.getUserLogin()))
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
        checkAccess();

        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось удалить задачу с id = %d", id));
        }
    }

    @Override
    public TaskDto patchById(Long id) {
        checkAccess();
        Task task = getTaskById(id);
        task.setDone(!task.isDone());

        try {
            taskRepository.save(task);
            return taskConverter.entityToDto(task);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось изменить задачу с id = %d", id));
        }
    }

    private User checkAccess() {
        User user = userService.getCurrentUser();
        if (!user.getIsAdmin()) {
            throw new ResourceForbiddenException("К админке нет доступа у данного пользователя");
        }
        return user;
    }

    private Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Таски с id = %d не найдено", id)));
    }
}
