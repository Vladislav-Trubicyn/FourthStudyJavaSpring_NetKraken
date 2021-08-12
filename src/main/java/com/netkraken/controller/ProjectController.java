package com.netkraken.controller;

import com.netkraken.model.Project;
import com.netkraken.model.Task;
import com.netkraken.model.User;
import com.netkraken.service.ProjectService;
import com.netkraken.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MANAGER')")
public class ProjectController
{
    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @GetMapping()
    public String showProjectListPage(@AuthenticationPrincipal User user, Model model)
    {
        model.addAttribute("projects", projectService.findAllProjects());
        return "projectList";
    }

    @GetMapping("/new")
    public String showAddProjectPage(@AuthenticationPrincipal User user, @ModelAttribute Project project, Model model)
    {
        Task task = taskService.findById(user.getSelectedId());
        return "addProject";
    }

}
