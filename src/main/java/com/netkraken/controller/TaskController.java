package com.netkraken.controller;

import com.netkraken.model.Task;
import com.netkraken.model.User;
import com.netkraken.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showTaskListPageAdmin(@ModelAttribute Task task, Model model)
    {
        model.addAttribute("tasks", taskService.findAllTasks());
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
        return "redirect:/tasks/my";
    }

    @GetMapping("/my")
    public String showMyTasksPage(@AuthenticationPrincipal User user, Model model)
    {
        model.addAttribute("tasks", taskService.findAllByUserId(user.getId()));
        return "myTasks";
    }


}
