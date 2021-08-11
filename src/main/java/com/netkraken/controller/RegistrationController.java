package com.netkraken.controller;

import com.netkraken.model.Role;
import com.netkraken.model.User;
import com.netkraken.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController
{
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public String addNewUser(@ModelAttribute User user, Model model)
    {
        User userFromDb = userService.findByUsername(user.getUsername());

        if(userFromDb == null)
        {
            user.setRoles(Collections.singleton(Role.USER));
            user.setAction(true);
            user.setStatus(true);
            userService.saveUser(user);
            return "login";
        }
        else
        {
            model.addAttribute("errRegMessage", "Данный ник уже присутствует в системе");
            return "registration";
        }
        
    }
}
