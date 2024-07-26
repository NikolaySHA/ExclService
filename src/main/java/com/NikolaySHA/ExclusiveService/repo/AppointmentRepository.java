package com.NikolaySHA.ExclusiveService.repo;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    @Query("SELECT a FROM Appointment a " +
            "WHERE (:date IS NULL OR a.date >= :date) " +
            "AND (:licensePlate IS NULL OR a.car.licensePlate LIKE %:licensePlate%) " +
            "AND (:make IS NULL OR LOWER(a.car.make) LIKE LOWER(CONCAT('%', :make, '%'))) " +
            "AND (:client IS NULL OR LOWER(a.user.name) LIKE LOWER(CONCAT('%', :client, '%'))) " +
            "AND (:status IS NULL OR a.status = :status)")
    List<Appointment> findAppointments(@Param("date") LocalDate date,
                                       @Param("licensePlate") String licensePlate,
                                       @Param("make") String make,
                                       @Param("client") String client,
                                       @Param("status") Status status);

    List<Appointment> findByUser_Email(String email);
    
    List<Appointment> findByDate(LocalDate today);
    @Query("SELECT a FROM Appointment a JOIN FETCH a.user u LEFT JOIN FETCH u.cars WHERE a.id = :id")
    Optional<Appointment> findByIdWithUserAndCars(@Param("id") Long id);
    @Query("SELECT COALESCE(SUM(a.paintDetails), 0) FROM Appointment a WHERE a.date BETWEEN :startOfWeek AND :endOfWeek")
    int findTotalDetailsForWeek(@Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek);
    
}
