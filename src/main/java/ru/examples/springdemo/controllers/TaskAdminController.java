package ru.examples.springdemo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.examples.springdemo.dtos.TaskDto;
import ru.examples.springdemo.services.TaskAdminServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Задача изменена"),
        @ApiResponse(responseCode = "400", description = "Ошибка в запросе",
                content = @Content(examples = {@ExampleObject(
                        value = "{\n  \"statusCode\": 400,\n  \"status\": \"Bad Request\",\n  \"message\": \"JSON parse error\"\n}")})),
        @ApiResponse(responseCode = "404", description = "Задача не найдена",
                content = @Content(examples = {@ExampleObject(
                        value = "{\n  \"statusCode\": 404,\n  \"status\": \"Not Found\",\n  \"message\": \"Таски с id = 10 не найдено\"\n}")})),
        @ApiResponse(responseCode = "403", description = "У пользователя нет доступа к задаче",
                content = @Content(examples = {@ExampleObject(
                        value = "{\n  \"statusCode\": 403,\n  \"status\": \"Forbidden\",\n  \"message\": \"К таске с id = 2 нет доступа у данного пользователя\"\n}")})),
        @ApiResponse(responseCode = "500", description = "Задачу изменить не удалось",
                content = @Content(examples = {@ExampleObject(
                        value = "{\n  \"statusCode\": 500,\n  \"status\": \"Internal Server Error\",\n  \"message\": \"Не получилось изменить задачу с id = 1\"\n}")}))
})
@Tag(name = "AdminTasks", description = "Методы для работы со списками задач")
public class TaskAdminController {

    private final TaskAdminServiceImpl taskAdminService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача создана"),
            @ApiResponse(responseCode = "500", description = "Задачу создать не удалось",
                    content = @Content(examples = {@ExampleObject(
                            value = "{\n  \"statusCode\": 500,\n  \"status\": \"Internal Server Error\",\n  \"message\": \"Не получилось создать задачу\"\n}")}))
    })
    @PostMapping("/tasks")
    @Operation(summary = "Создание задачи",
            description = "Позволяет создать задачу пользователю")
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return taskAdminService.createTask(taskDto);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список задач выведен")
    })
    @GetMapping("/tasks")
    @Operation(summary = "Вывод всех задач",
            description = "Позволяет вывести всего списка задач")
    public List<TaskDto> getAllTasks() {
        return taskAdminService.getAllByUser();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача выведена"),
            @ApiResponse(responseCode = "500", description = "Задачу вывести не удалось",
                    content = @Content(examples = {@ExampleObject(
                            value = "{\n  \"statusCode\": 500,\n  \"status\": \"Internal Server Error\",\n  \"message\": \"Не получилось вывести задачу\"\n}")}))
    })
    @GetMapping("/tasks/{id}")
    @Operation(summary = "Вывод задачи по id",
            description = "Позволяет вывести задачу по заданному id")
    public TaskDto getTaskById(
            @Parameter(description = "ID задачи, данные по которой запрашиваются",
                    required = true)
            @PathVariable Long id) {
        return taskAdminService.getById(id);
    }

    @PutMapping("/tasks/{id}")
    @Operation(summary = "Редактирование задачи по id",
            description = "Позволяет редактировать задачу с заданным id")
    public TaskDto putTaskById(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return taskAdminService.putById(id, taskDto);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача удалена"),
            @ApiResponse(responseCode = "500", description = "Задачу удалить не удалось",
                    content = @Content(examples = {@ExampleObject(
                            value = "{\n  \"statusCode\": 500,\n  \"status\": \"Internal Server Error\",\n  \"message\": \"Не получилось удалить задачу\"\n}")}))
    })
    @DeleteMapping("/tasks/{id}")
    @Operation(summary = "Удаление задачи по id",
            description = "Позволяет удалить задачу по заданному id")
    public void deleteTaskById(@PathVariable Long id) {
        taskAdminService.deleteById(id);
    }

    @PatchMapping("/tasks/{id}")
    @Operation(summary = "Редактирование отметки выполнения задачи по id",
            description = "Позволяет изменить отметку выполнения на противоположную у задачи с заданным id")
    public TaskDto patchTaskById(@PathVariable Long id) {
        return taskAdminService.patchById(id);
    }


}
