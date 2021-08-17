package com.netkraken.controller;

import com.netkraken.model.Project;
import com.netkraken.model.Role;
import com.netkraken.model.Task;
import com.netkraken.model.User;
import com.netkraken.service.ProjectService;
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
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showTaskListPage(@AuthenticationPrincipal User user, @ModelAttribute Task task, Model model)
    {
        if(user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.MANAGER))
        {
            model.addAttribute("tasks", taskService.findAllByProject(false));
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
        task.setProject(false);
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
    public String showAddProjectPage(@AuthenticationPrincipal User user, @PathVariable("id") Task task, Model model)
    {
        Project project = new Project();
        project.setTitle(task.getTitle());
        project.setTaskId(task.getId());
        project.setCustomerId(task.getUserId());
        project.setManagerId(user.getId());
        project.setCountProgrammers(task.getCountProgrammers());
        project.setStatus("Оформляется");

        task.setProject(true);
        taskService.saveTask(task);

        if(projectService.findByTaskId(task.getId()) == null)
        {
            projectService.saveProject(project);
            user.setSelectedId(projectService.findByTaskId(task.getId()).getId());

            return "redirect:/projects/" + user.getSelectedId() + "/edit";
        }
        else
        {
            return "redirect:/tasks";
        }


    }

}
