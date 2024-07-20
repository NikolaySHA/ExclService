package com.ExclusiveService.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@Entity
@Table(name = "transfer_protocols")
@Getter
@Setter
@NoArgsConstructor
public class TransferProtocol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;
    @ManyToOne
    private Appointment appointment;
    
}
