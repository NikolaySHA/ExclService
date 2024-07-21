package com.ExclusiveService.repo;

import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.enums.Status;
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
    List<Appointment> findByUser_EmailAndCar_LicensePlate(String email, String licensePlate);
    
    List<Appointment> findByDate(LocalDate today);
    @Query("SELECT a FROM Appointment a JOIN FETCH a.user u JOIN FETCH u.cars WHERE a.id = :id")
    Optional<Appointment> findByIdWithUserAndCars(@Param("id") Long id);

}
