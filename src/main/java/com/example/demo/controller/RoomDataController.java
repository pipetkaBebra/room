package com.example.demo.controller;

import com.example.demo.entity.RoomData;
import com.example.demo.entity.TablePerson;
import com.example.demo.service.RoomDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/menu")
public class RoomDataController {

    private final RoomDataService roomDataService;

    @Autowired
    public RoomDataController(RoomDataService roomDataService) {
        this.roomDataService = roomDataService;
    }

    // Додаємо дані для кімнати
    @PostMapping("/addRoom")
    @ResponseBody
    public String addRoomData(@RequestParam int roomNumber,
                              @RequestParam float temperature,
                              @RequestParam float humidity,
                              @RequestParam float gasAmount) {
        roomDataService.saveRoomData(roomNumber, temperature, humidity, gasAmount);
        return "Room data added successfully!";
    }

    // Отримуємо всі дані для кімнат
    @GetMapping("/roomData")
    @ResponseBody
    public List<RoomData> getAllRoomData() {
        return roomDataService.getAllRoomData();
    }

    // Отримуємо дані для конкретної кімнати
    @GetMapping("/room/{roomNumber}/data")
    @ResponseBody
    public List<RoomData> getRoomData(@PathVariable int roomNumber) {
        return roomDataService.getRoomDataByRoomNumber(roomNumber);  // отримаємо список результатів
    }


    // Отримуємо дані для конкретної кімнати з конкретними параметрами
    @GetMapping("/room/{roomNumber}/data/parameters")
    @ResponseBody
    public RoomData getRoomDataByParams(@PathVariable int roomNumber,
                                        @RequestParam float temperature,
                                        @RequestParam float humidity,
                                        @RequestParam float gasAmount) {
        return roomDataService.getRoomDataByParams(roomNumber, temperature, humidity, gasAmount);
    }
}
