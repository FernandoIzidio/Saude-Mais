package com.saude.mais.agendamento.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    private Instant date;


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

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String state;

    @Column(length = 1000, nullable = false)
    private String observations;

    @Column(nullable = false)
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

    public AppointmentEntity(Instant date, CustomerEntity customer, WorkerEntity worker, RoomEntity room, String type, String state, String observations, String diagnostic) {
        this.date = date;
        this.customer = customer;
        this.room = room;
        this.type = type;
        this.state = state;
        this.observations = observations;
        this.diagnostic = diagnostic;
    }


}