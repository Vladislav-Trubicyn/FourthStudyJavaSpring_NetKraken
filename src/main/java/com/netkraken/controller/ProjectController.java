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

@Controller
@RequestMapping("/projects")
@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MANAGER')")
public class ProjectController
{
    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

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

        model.addAttribute("selectedUsers" , projectService.findById(user.getSelectedId()).get().getProgrammers());

        if(!filter.isEmpty())
        {
            model.addAttribute("users", userService.findBySpecializationAndStatus(filter, true));
        }
        else
        {
            model.addAttribute("users", userService.findAllByStatus(true));
        }

        Project project = projectService.findById(user.getSelectedId()).get();

        model.addAttribute("filter", filter);
        model.addAttribute("project", project);
        model.addAttribute("price", taskService.findById(project.getTaskId()).getPrice());

        return "addProject";
    }

    @GetMapping("/user/{id}/select")
    public String selectingUserForSelectedProject(@AuthenticationPrincipal User user, @PathVariable("id") User selectUser)
    {
        Project project = projectService.findById(user.getSelectedId()).get();

        if(project.getProgrammers().size() < project.getCountProgrammers())
        {
            project.getProgrammers().add(selectUser);
            selectUser.setProject(user.getSelectedId());
            selectUser.setStatus(false);
            userService.saveUser(selectUser);
            projectService.saveProject(project);
        }

        return "redirect:/projects/" + user.getSelectedId() + "/edit";
    }

    @GetMapping("/user/{id}/delete")
    public String deleteSelectingUserFromSelectedProject(@AuthenticationPrincipal User user, @PathVariable("id") User selectUser)
    {
        Project project = projectService.findById(user.getSelectedId()).get();

        project.getProgrammers().remove(selectUser);
        selectUser.setProject(null);
        selectUser.setStatus(true);
        userService.saveUser(selectUser);
        projectService.saveProject(project);

        return "redirect:/projects/" + user.getSelectedId() + "/edit";
    }

    @PostMapping("/save")
    public String saveSelectedProject(@AuthenticationPrincipal User user,
                                      @RequestParam("title") String title,
                                      @RequestParam("price") int price)
    {
        Project project = projectService.findById(user.getSelectedId()).get();

        Task task = taskService.findById(project.getTaskId());

        project.setTitle(title);
        task.setPrice(price);
        taskService.saveTask(task);
        projectService.saveProject(project);

        return "redirect:/projects/" + user.getSelectedId() + "/edit";
    }

}
