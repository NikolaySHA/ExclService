package com.exclusiveService.repo;

import com.exclusiveService.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
    
    List<Appointment> findByUser_Email(String email);
}
