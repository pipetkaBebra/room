package com.example.demo.controller;

import com.example.demo.entity.TablePerson;
import com.example.demo.service.TablePersonService;
import com.example.demo.repository.TablePersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@Controller
@RequestMapping("/api/menu")
public class TablePersonController {

    @Autowired
    private TablePersonService tablePersonService;

    @Autowired
    private TablePersonRepository tablePersonRepository;

    @GetMapping("/0")
    public String show0() {
        return "0"; // Повертає HTML-сторінку login.html з папки templates
    }

    @GetMapping("/1")
    public String show1() {
        return "1"; // Повертає HTML-сторінку login.html з папки templates
    }

    @GetMapping("/2")
    public String show2() {
        return "2"; // Повертає HTML-сторінку login.html з папки templates
    }



    // Додаємо нову особу з номером аудиторії
    @PostMapping("/add")
    public TablePerson addPerson(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam boolean isActive,
            @RequestParam String roomNumber // Новий параметр для номера аудиторії
    ) {
        return tablePersonService.addPerson(name, email, isActive, roomNumber);
    }

    // Видаляємо осіб, чия дата створення більше ніж 30 днів
    @DeleteMapping("/deleteOld")
    public String deleteOldPersons() {
        tablePersonService.deletePersons();
        return "Old persons deleted successfully!";
    }

    // Отримуємо всіх осіб
    @GetMapping("/table-persons")
    @ResponseBody
    public List<TablePerson> getAllPersons() {
        return tablePersonService.getAllPersons();
    }

    @GetMapping("/active-in-room")
    @ResponseBody
    public List<TablePerson> getActivePersonsInRoom(@RequestParam String roomNumber) {
        return tablePersonService.getActivePersonsInRoom(roomNumber);
    }

    @GetMapping("/room/{roomNumber}/logs")
    @ResponseBody
    public List<TablePerson> getRoomLogs(@PathVariable String roomNumber) {
        return tablePersonRepository.findLogsByRoomNumber(roomNumber);
    }
    @PostMapping("/off")
    public String turnOffActiveUsers() {
        LocalDateTime now = LocalDateTime.now();

        // Знайдемо всіх користувачів, де isActive = true
        List<TablePerson> activeUsers = tablePersonRepository.findActiveUsers(true);

        // Оновимо їх статус на false та додамо новий запис для кожного користувача
        for (TablePerson user : activeUsers) {
            // Перевірка, чи остання дія користувача була з isActive = true
            if (user.isActive()) {  // Замінили getIsActive() на isActive() без геттера


                // Додати новий запис лише в тому випадку, якщо його стан змінений
                TablePerson newUser = TablePerson.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .isActive(false)
                        .createdAt(now)
                        .roomNumber(user.getRoomNumber())
                        .build();

                tablePersonRepository.save(newUser);  // Зберігаємо новий запис
            }
        }


        return "redirect:/api/menu";
    }




}
