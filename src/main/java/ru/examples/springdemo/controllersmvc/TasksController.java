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
        hiddenTop = false;
        hiddenSingle = true;
        model.addAttribute("listTasks", listTasks);
        model.addAttribute("hiddenTop", hiddenTop);
        model.addAttribute("hiddenSingle", hiddenSingle);
        return "/task";
    }

    @GetMapping("/tasks/{id}")
    public String getTaskById(
            @PathVariable Long id, Model model) {
        System.out.println("get=============================");
        TaskDto task = taskService.getById(id);
        model.addAttribute("listTasks", task);
        return "/task";
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTaskById(
            @PathVariable Long id, Model model) {
        System.out.println("del=============================" + id);
        taskService.deleteById(id);
        model.addAttribute("str", "Deleted");
        System.out.println("del=============================" + model);
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
    public String createTaskById(TaskDto taskDto, Model model) {

        System.out.println("post=============================" + model);
        System.out.println("post=============================" + taskDto);
        taskService.createTask(taskDto);
        return "redirect:/ui/tasks";
    }


    @ExceptionHandler(value = Exception.class)
    public String exception(Model model) {
        System.out.println("exception=============================");
        model.addAttribute("flag", false);
        return "/task";
    }

    @GetMapping("/error")
    public String error(Model model) {
        System.out.println("error=============================");

        return "/error";
    }
}
