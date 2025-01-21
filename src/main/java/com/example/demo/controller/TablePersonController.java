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
import java.util.Optional;

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

        // Отримуємо всіх користувачів, для яких останній запис був з isActive = true
        List<TablePerson> activeUsers = tablePersonRepository.findActiveUsers(true);

        // Оновлюємо їх статус на false та додаємо новий запис
        for (TablePerson user : activeUsers) {
            // Перевірка, чи останній запис користувача був з isActive = true
            List<TablePerson> userLogs = tablePersonRepository.findByEmailOrderByCreatedAtDesc(user.getEmail());

            if (!userLogs.isEmpty()) {
                TablePerson lastEntry = userLogs.get(0);

                // Якщо останній запис мав значення true, то додати новий запис зі значенням false
                if (lastEntry.isActive()) {
                    TablePerson newUser = TablePerson.builder()
                            .name(lastEntry.getName())
                            .email(lastEntry.getEmail())
                            .isActive(false)
                            .createdAt(now)
                            .roomNumber(lastEntry.getRoomNumber())
                            .build();

                    tablePersonRepository.save(newUser);  // Зберігаємо новий запис з false
                }
            }
        }

        return "redirect:/api/menu";  // Перенаправляємо на відповідну сторінку
    }
}
