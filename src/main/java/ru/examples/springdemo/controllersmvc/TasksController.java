package ru.examples.springdemo.controllersmvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.examples.springdemo.dtos.TaskDto;
import ru.examples.springdemo.services.TaskServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ui")
public class TasksController {

    private final TaskServiceImpl taskService;
    private Boolean hiddenTop;
    private Boolean hiddenSingle;

    @GetMapping("/tasks")
    public String getAllTasks(@RequestParam(name = "isDone", required = false) Boolean isDone, Model model) {
        System.out.println("getall=============================");
        List<TaskDto> listTasks = taskService.getTasksByUser(isDone);
        hiddenSingle = true;
        model.addAttribute("listTasks", listTasks);
        model.addAttribute("hiddenSingle", hiddenSingle);
        return "/task";
    }

    @GetMapping("/tasks/{id}")
    public String getTaskById(@PathVariable Long id, Model model) {
        System.out.println("get=============================");
        TaskDto task = taskService.getById(id);
        model.addAttribute("listTasks", task);
        model.addAttribute("id", id);
        return "/task";
    }

    @GetMapping("/tasks/new")
    public String getNewTask(Model model) {
        System.out.println("getnew=============================");
        TaskDto newTask = TaskDto.builder()
                .userLogin("Nata")
                .build();
        model.addAttribute("newTask", newTask);
        return "/task_new";
    }

    @PostMapping(path = "/tasks/new")
    public String createTask(TaskDto taskDto, Model model) {
        System.out.println("post=============================" + model);
        System.out.println("post=============================" + taskDto);
        taskService.createTask(taskDto);
        return "redirect:/ui/tasks";
    }

    @PostMapping("/tasks/{id}:delete")
    public String deleteTaskById(@PathVariable Long id, Model model) {
        System.out.println("del=============================" + id);
        taskService.deleteById(id);
        hiddenSingle = true;
        model.addAttribute("hiddenSingle", hiddenSingle);
        model.addAttribute("id", id);
        model.addAttribute("str", "Таска с id = " + id + " успешно удалена");
        System.out.println("del=============================" + model);
        return "/task";
    }

    @GetMapping("/tasks/{id}:put")
    public String getChangeTask(@PathVariable Long id, Model model) {
        System.out.println("getchange=============================");
        System.out.println(id);
        TaskDto task = taskService.getById(id);
        TaskDto changeTask = TaskDto.builder()
                .id(id)
                .date(task.getDate())
                .description(task.getDescription())
                .userLogin(task.getUserLogin())
                .build();
        model.addAttribute("changeTask", changeTask);
        return "/task_change";
    }

    @PostMapping("/tasks/{id}:put")
    public String putTaskById(@PathVariable Long id, TaskDto taskDto, Model model) {
        System.out.println("put=============================" + id);
        hiddenSingle = true;
        model.addAttribute("hiddenSingle", hiddenSingle);
        TaskDto task = taskService.putById(id, taskDto);
        model.addAttribute("listTasks", task);
        model.addAttribute("str", "Таска с id = " + id + " успешно исправлена");
        System.out.println("put=============================" + model);
        return "/task";
    }

    @PostMapping("/tasks/{id}:patch")
    public String patchTaskById(@PathVariable Long id, Model model) {
        System.out.println("patch=============================" + id);
        hiddenSingle = true;
        model.addAttribute("hiddenSingle", hiddenSingle);
        TaskDto task = taskService.patchById(id);
        model.addAttribute("listTasks", task);
        model.addAttribute("str", "Таске с id = " + id + " успешно изменен статус");
        System.out.println("patch=============================" + model);
        return "/task";
    }


    @ExceptionHandler(value = Exception.class)
    public String exception(Exception e, Model model) {
        System.out.println("exception=============================");
        String error = e.getMessage();
        model.addAttribute("error", error);
        return "/error";
    }

//    @GetMapping("/error")
//    public String error(Model model) {
//        System.out.println("error=============================");
//
//        return "/error";
//    }
}
