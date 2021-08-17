package com.netkraken.controller;

import com.netkraken.model.Project;
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

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/projects")
@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MANAGER')")
public class ProjectController
{
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showProjectListPage(@AuthenticationPrincipal User user, Model model)
    {
        model.addAttribute("projects", projectService.findAllProjects());
        return "projectList";
    }

    @GetMapping("/new")
    public String showAddProjectPage(@ModelAttribute Project project)
    {
        return "addProject";
    }

    @GetMapping("/{id}/edit")
    public String editSelectedProject(@AuthenticationPrincipal User user, Model model,
                                      @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                                      @PathVariable("id") Long id)
    {
        if(id != null)
        {
            user.setSelectedId(id);
        }

        if(!filter.isEmpty())
        {
            model.addAttribute("users", userService.findBySpecializationAndStatus(filter, true));
        }
        else
        {
            model.addAttribute("users", userService.findAllUsers());
        }

        model.addAttribute("filter", filter);
        model.addAttribute("project", projectService.findById(user.getSelectedId()).get());

        return "addProject";
    }

    @PostMapping("/save")
    public String saveSelectedProject(@AuthenticationPrincipal User user,
                                      @RequestParam Map<String, String> form,
                                      @RequestParam("title") String title)
    {
        Optional<Project> project = projectService.findById(user.getSelectedId());

        project.get().setTitle(title);

        project.get().getProgrammers().clear();

        User addedUser;

        for(String key : form.keySet())
        {
            if(!project.get().getProgrammers().contains(userService.findByUsername(key)))
            {
                addedUser = userService.findByUsername(key);
                project.get().getProgrammers().add(addedUser);
                addedUser.setStatus(false);
                userService.saveUser(addedUser);
            }
        }

        projectService.saveProject(project.get());

        return "redirect:/projects/" + user.getSelectedId() + "/edit";
    }

}
