package com.netkraken.controller;

import com.netkraken.model.Role;
import com.netkraken.model.User;
import com.netkraken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping()
    public String showUserListPage(Model model)
    {
        Iterable<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/{id}/edit")
    public String showEditUserPageAdmin(@PathVariable("id") User user, Model model)
    {
        model.addAttribute("user", userService.findByUsername(user.getUsername()));
        model.addAttribute("roles", Role.values());
        return "editUser";
    }

    @PostMapping("/edituser")
    public String editSelectedUserAdmin(@RequestParam("id") User user,
                                        @RequestParam Map<String, String> form,
                                        @RequestParam("username") String username,
                                        @RequestParam(value = "action", required = true, defaultValue = "false") boolean action,
                                        Model model)
    {
        user.setAction(action);
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key : form.keySet())
        {
            if(roles.contains(key))
            {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userService.saveUser(user);
        return "redirect:/users";
    }
}
