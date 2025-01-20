package com.example.demo.controller;

import com.example.demo.entity.UserAccount;
import com.example.demo.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/api")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/add")
    @ResponseBody
    public String addUser(@RequestParam String email,
                          @RequestParam String password) {
        UserAccount newUser = new UserAccount();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setCreatedAt(LocalDateTime.now());

        userAccountService.save(newUser);
        return "User added successfully";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Повертає HTML-сторінку login.html з папки templates
    }

    @GetMapping("/menu")
    public String showMenuPage() {
        return "menu";  // Повертає HTML-сторінку menu.html з папки templates
    }

        @PostMapping("/login")
    public String handleLogin(@RequestParam String email, @RequestParam String password, Model model) {

        UserAccount userAccount = userAccountService.findByEmail(email);

        if (userAccount != null && userAccount.getPassword().equals(password)) {
            return "redirect:/api/menu"; // Перенаправлення на сторінку меню
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";  // Вказуємо шлях у межах API
        }
    }


}
