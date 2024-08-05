package com.saude.mais.agendamento.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "appointments")
public class AppointmentEntity implements Serializable {


    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;


    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name = "appointment_worker",
               joinColumns = @JoinColumn(name = "appointment_id"),
               inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    private List<WorkerEntity> workers;


    private String room;

    @Column(nullable = false)
    private String type;


    private String state;


    private String observations;


    private String diagnostic;


    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "appointment")
    private List<ProceduresEntity> procedures = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "appointment")
    private List<MedicationEntity> medications = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalEntity hospital;



    public AppointmentEntity(LocalDate date, CustomerEntity customer, List<WorkerEntity> workers, String room, String type) {
        this.date = date;
        this.customer = customer;
        this.workers = workers;
        this.room = room;
        this.type = type;
    }
}