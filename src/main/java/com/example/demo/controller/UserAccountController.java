package com.example.demo.controller;

import com.example.demo.entity.UserAccount;
import com.example.demo.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/room/api")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, BCryptPasswordEncoder passwordEncoder) {
        this.userAccountService = userAccountService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/add")
    @ResponseBody
    public String addUser(@RequestParam String email,
                          @RequestParam String password) {
        UserAccount newUser = new UserAccount();
        newUser.setEmail(email);

        // Хешуємо пароль перед збереженням
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);

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

        if (userAccount != null && passwordEncoder.matches(password, userAccount.getPassword())) {
            return "redirect:/api/menu"; // Перенаправлення на сторінку меню
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";  // Вказуємо шлях у межах API
        }
    }
}
