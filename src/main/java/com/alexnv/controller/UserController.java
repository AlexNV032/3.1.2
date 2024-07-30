package com.alexnv.controller;

import com.alexnv.exception.UserNotFoundException;
import com.alexnv.model.User;
import com.alexnv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());
            return "users";
        }
        if (userService.isUserExistsByName(user.getName())) {
            result.rejectValue("name", "error.user", "User with this name already exists.");
            return "users";
        }
        if (userService.isUserExistsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "User with this email already exists.");
            return "users";
        }
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update";
        }
        try {
            userService.updateUser(user);
        } catch (UserNotFoundException ex) {
            result.rejectValue("id", "error.user", ex.getMessage());
            return "update";
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id, Model model) {
        try {
            userService.deleteUser(id);
        } catch (UserNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "error";
        }
        return "redirect:/";
    }
}
