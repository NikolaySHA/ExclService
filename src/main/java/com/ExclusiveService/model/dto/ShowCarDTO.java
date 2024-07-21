package com.ExclusiveService.model.dto;

import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShowCarDTO {
    private String licensePlate;
    private String make;
    private String model;
    private String vin;
    private String color;
    private User owner;
    private List<Appointment> appointments;
    public ShowCarDTO() {
        this.appointments = new ArrayList<>();
    }
}