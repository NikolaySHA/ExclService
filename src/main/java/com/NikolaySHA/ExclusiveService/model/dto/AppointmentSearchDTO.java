package com.NikolaySHA.ExclusiveService.model.dto;


import com.NikolaySHA.ExclusiveService.model.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentSearchDTO {
    private String date;
    private String licensePlate;
    private String make;
    private String customer;
    private Status status;
    private boolean unread;

}
