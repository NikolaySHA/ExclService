package com.ExclusiveService.model.entity;

import com.ExclusiveService.model.enums.PaymentMethod;
import com.ExclusiveService.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDate date;
    @ManyToOne
    @NotNull
    private User user;
    @ManyToOne
    private Car car;
    @Column(nullable = false)
    private int paintDetails;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String comment;
//    private boolean unread;
}
