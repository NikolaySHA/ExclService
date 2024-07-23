package com.NikolaySHA.ExclusiveService.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String phoneNumber;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Car> cars;
    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> roles;
    
    public User() {
       this.cars = new ArrayList<>();
       this.appointments = new ArrayList<>();
       this.roles = new ArrayList<>();
    }
    
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.appointments = new ArrayList<>();
        this.cars = new ArrayList<>();
    }
    
}
