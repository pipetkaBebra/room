package com.example.demo.service;

import com.example.demo.entity.TablePerson;
import com.example.demo.repository.TablePersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
@Service
public class TablePersonService {

    @Autowired
    private TablePersonRepository tablePersonRepository;

    // Додаємо нову особу з номером аудиторії
    public TablePerson addPerson(String name, String email, boolean isActive, String roomNumber) {
        TablePerson person = TablePerson.builder()
                .name(name)
                .email(email)
                .isActive(isActive)
                .roomNumber(roomNumber) // Додаємо номер аудиторії
                .createdAt(LocalDateTime.now()) // автоматично встановлюється поточний час
                .build();

        return tablePersonRepository.save(person); // Зберігаємо об'єкт у базі
    }

    // Видаляємо осіб, дата створення яких більше ніж 30 днів
    public void deletePersons() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);

        List<TablePerson> personsToDelete = tablePersonRepository.findAll().stream()
                .filter(person -> person.getCreatedAt().isBefore(thirtyDaysAgo))
                .toList();

        tablePersonRepository.deleteAll(personsToDelete);
    }

    // Отримуємо всіх осіб
    public List<TablePerson> getAllPersons() {
        return tablePersonRepository.findAll();
    }
    public List<TablePerson> getActivePersonsInRoom(String roomNumber) {
        return tablePersonRepository.findActivePersonsByRoom(roomNumber);
    }


}
