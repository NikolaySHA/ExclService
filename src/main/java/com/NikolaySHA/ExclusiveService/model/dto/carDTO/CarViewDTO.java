package com.NikolaySHA.ExclusiveService.model.dto.carDTO;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CarViewDTO {
    private String id;
    private String licensePlate;
    private String make;
    private String model;
    private String vin;
    private String color;
    private User owner;
    private List<Appointment> appointments;
    public CarViewDTO() {
        this.appointments = new ArrayList<>();
    }
}