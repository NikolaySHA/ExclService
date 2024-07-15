package com.ExclusiveService.model.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
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
    private String viberNumber;
    @OneToMany(mappedBy = "owner")
    private List<Car> cars;
    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> userRoles;
    
    public List<UserRole> getRoles() {
        return userRoles;
    }
    
    public void setRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
    
    public User() {
       this.cars = new ArrayList<>();
       this.appointments = new ArrayList<>();
       this.userRoles = new ArrayList<>();
    }
    
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.appointments = new ArrayList<>();
        this.cars = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public List<Appointment> getAppointments() {
        return appointments;
    }
    
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getViberNumber() {
        return viberNumber;
    }
    
    public void setViberNumber(String viberNumber) {
        this.viberNumber = viberNumber;
    }
    
    public List<Car> getCars() {
        return cars;
    }
    
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
