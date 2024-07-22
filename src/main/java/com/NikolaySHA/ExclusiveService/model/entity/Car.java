package com.NikolaySHA.ExclusiveService.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String licensePlate;
    @Column(nullable = false)
    private String make;
    @Column(nullable = false)
    private String model;
    @Column(unique = true)
    private String vin;
    @Column
    private String color;
    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private List<Appointment> appointments;
    @ManyToOne
    private User owner;
    
    public Car() {
        this.appointments = new ArrayList<>();
    }
    
}
