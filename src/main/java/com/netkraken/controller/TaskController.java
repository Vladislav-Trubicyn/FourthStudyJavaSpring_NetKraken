package com.netkraken.controller;

import com.netkraken.model.Role;
import com.netkraken.model.Task;
import com.netkraken.model.User;
import com.netkraken.service.TaskService;
import com.netkraken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showTaskListPage(@AuthenticationPrincipal User user, @ModelAttribute Task task, Model model)
    {
        if(user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.MANAGER))
        {
            model.addAttribute("tasks", taskService.findAllTasks());
            model.addAttribute("roleAdmin", true);
        }
        else if(user.getRoles().contains(Role.USER))
        {
            model.addAttribute("tasks", taskService.findAllByUserId(user.getId()));
            model.addAttribute("roleAdmin", false);
        }

        return "taskList";
    }

    @GetMapping("/new")
    public String showAddTaskPage(@ModelAttribute Task task)
    {
        return "addTask";
    }

    @PostMapping("/add")
    public String addNewTask(@AuthenticationPrincipal User user, @ModelAttribute Task task)
    {
        task.setUserId(user.getId());
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String showEditTaskPage(@AuthenticationPrincipal User user, @PathVariable("id") Task task, Model model)
    {
        if(task.getUserId().equals(user.getId()))
        {
            user.setSelectedId(task.getId());
            model.addAttribute("task", task);
            return "editTask";
        }
        else
        {
            return "redirect:/tasks";
        }
    }

    @PatchMapping("/edit")
    public String editSelectedTask(@AuthenticationPrincipal User user, @ModelAttribute Task task)
    {
        task.setId(user.getSelectedId());
        task.setUserId(user.getId());
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/formalization")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MANAGER')")
    public String showAddProjectPage(@AuthenticationPrincipal User user, @PathVariable("id") Task task, Model model, @RequestParam(value = "filter", required = false) String filter)
    {
        user.setSelectedId(task.getId());
        model.addAttribute("task", task);

        if(!filter.isEmpty())
        {
            model.addAttribute("users", userService.findBySpecializationAndStatus(filter, true));
        }
        else
        {
            model.addAttribute("users", userService.findAllUsers());
        }

        model.addAttribute("filter", filter);

        return "addProject";
    }

}
