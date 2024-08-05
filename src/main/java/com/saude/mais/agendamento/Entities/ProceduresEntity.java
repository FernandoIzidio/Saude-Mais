package com.saude.mais.agendamento.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@ToString
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ProceduresEntity that = (ProceduresEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

