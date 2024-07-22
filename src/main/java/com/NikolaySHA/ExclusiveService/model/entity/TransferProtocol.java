package com.NikolaySHA.ExclusiveService.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "protocols")
@Getter
@Setter
@NoArgsConstructor
public class TransferProtocol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;
    private String customerName;
    private String licensePlate;
    private String make;
    private String model;
    private boolean finished;
   
    
    
}
