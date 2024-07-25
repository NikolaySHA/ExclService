package com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO;


import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.PaymentMethod;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShowAppointmentDTO {
    private LocalDate date;
    private User user;
    private Car car;
    private int paintDetails;
    private PaymentMethod paymentMethod;
    private Status status;
    private String comment;
    private List<TransferProtocol> protocols;
    
    public ShowAppointmentDTO() {
        this.protocols = new ArrayList<>();
    }
}