package com.saude.mais.agendamento.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "procedures")
public class ProceduresEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private String duration;

    @ToString.Exclude
    @Column(nullable = false, length = 1000)
    private String result;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private WorkerEntity worker;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private AppointmentEntity appointment;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalEntity hospital;

    public ProceduresEntity(String name, Instant date, String duration, WorkerEntity worker, AppointmentEntity appointment, String result) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.result = result;
        this.worker = worker;
        this.appointment = appointment;
    }

}

