package com.NikolaySHA.ExclusiveService.model.entity;

import com.NikolaySHA.ExclusiveService.model.enums.PaymentMethod;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "appointments")
@Getter
@Setter
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
    @Column(columnDefinition = "TEXT")
    private String comment;
    @OneToMany(fetch = FetchType.EAGER)
    private List<TransferProtocol> protocols;
    @ElementCollection
    @CollectionTable(name = "appointment_expenses", joinColumns = @JoinColumn(name = "appointment_id"))
    @Column(name = "expense_id")
    private List<Long> expensesList;
    public Appointment() {
        this.protocols = new ArrayList<>();
        this.expensesList = new ArrayList<>();
    }
}
