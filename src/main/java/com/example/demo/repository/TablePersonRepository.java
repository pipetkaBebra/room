package com.example.demo.repository;


import com.example.demo.entity.TablePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TablePersonRepository extends JpaRepository<TablePerson, Long> {
    // Метод для отримання всіх користувачів, які зараз у кабінеті
    @Query("SELECT p FROM TablePerson p WHERE p.roomNumber = :roomNumber AND p.isActive = true AND p.createdAt = " +
            "(SELECT MAX(sub.createdAt) FROM TablePerson sub WHERE sub.name = p.name)")
    List<TablePerson> findActivePersonsByRoom(@Param("roomNumber") String roomNumber);

    @Query("SELECT p FROM TablePerson p WHERE p.roomNumber = :roomNumber ORDER BY p.createdAt DESC")
    List<TablePerson> findLogsByRoomNumber(@Param("roomNumber") String roomNumber);

    @Query("SELECT t FROM TablePerson t WHERE t.isActive = :isActive")
    List<TablePerson> findActiveUsers(boolean isActive);

}
