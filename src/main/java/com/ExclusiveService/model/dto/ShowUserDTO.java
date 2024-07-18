package com.ExclusiveService.model.dto;

import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ShowUserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Car> cars;
    private List<Appointment> appointments;
    
    public ShowUserDTO() {
        this.cars = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }
}