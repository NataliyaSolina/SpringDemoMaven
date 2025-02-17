package ru.examples.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.examples.springdemo.exeption.ResourceForbiddenException;
import ru.examples.springdemo.exeption.ResourceInternalServerErrorException;
import ru.examples.springdemo.exeption.ResourceNotFoundException;
import ru.examples.springdemo.model.Task;
import ru.examples.springdemo.model.User;
import ru.examples.springdemo.repository.TaskRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl {

    private final TaskRepository taskRepository;
    private final UserServiceImpl userService;

    public Task create(Task task) {
        User user = userService.getCurrentUser();

        if (user.getLogin().equalsIgnoreCase("admin")) {
            if (task.getUser() == null) {
                task.setUser(user);
            }
        } else {
            task.setUser(user);
        }

        try {
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException("Не получилось создать");
        }
    }

    public List<Task> getAllByUser() {
        User user = userService.getCurrentUser();

        return (List<Task>) (user.getLogin().equalsIgnoreCase("admin")
                ? taskRepository.findAll(Sort.by("id").ascending())
                : taskRepository.findTasksByUserIdOrderById(user.getId()));
    }

    public Task getById(Long id) {
        User user = userService.getCurrentUser();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Таски с id = %d не найдено", id)));

        return (user.getLogin().equalsIgnoreCase("admin")
                ? task
                : taskRepository.findTasksByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceForbiddenException(format("К таске с id = %d нет доступа у данного пользователя", id))));
    }

    public Task putById(Long id, Task task) {
        Task taskOld = getById(id);

        task.setId(id);
        task.setUser(taskOld.getUser());
        task.setDone(taskOld.isDone());

        try {
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось изменить задачу с id = %d", id));
        }
    }

    public void deleteById(Long id) {
        Task task = getById(id);

        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось удалить задачу с id = %d", id));
        }
    }

    public Task patchById(Long id) {
        Task task = getById(id);

        task.setDone(!task.isDone());

        try {
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось изменить задачу с id = %d", id));
        }
    }

    public Task patchByIdMark(Long id) {
        Task task = getById(id);

        task.setDone(true);

        try {
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new ResourceInternalServerErrorException(format("Не получилось изменить задачу с id = %d", id));
        }
    }
}
