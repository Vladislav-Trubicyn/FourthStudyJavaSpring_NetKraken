package com.netkraken.controller;

import com.netkraken.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MainController
{
    @GetMapping("/logout")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response)
    {

        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();

        session = request.getSession(false);
        if (session != null)
        {
            session.invalidate();
        }

        for (Cookie cookie : request.getCookies())
        {
            cookie.setMaxAge(0);
        }

        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String showRegPage(@ModelAttribute User user)
    {
        return "registration";
    }

}
