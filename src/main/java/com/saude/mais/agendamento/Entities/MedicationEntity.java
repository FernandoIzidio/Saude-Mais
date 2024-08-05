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
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medication")
public class MedicationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ToString.Include
    @Column(nullable = false)
    private String name;

    @ToString.Include
    @Column(nullable = false)
    private String dosage;

    @Column(name = "administration_route", nullable = false)
    private String administrationRoute;

    @Column(name = "administration_interval", nullable = false)
    private String administrationInterval;

    @Setter(AccessLevel.NONE)
    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "prescriber_worker_id", nullable = false)
    private WorkerEntity prescriberWorker;

    @Column(name = "prescription_date", nullable = false)
    private Instant prescriptionDate;

    @Column(name = "expiration_date", nullable = false)
    private Instant expirationDate;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "usage_instructions", length = 1000, nullable = false)
    private String usageInstructions;

    @Column(nullable = false)
    private String indications;

    @Column(name = "side_effects", length = 1000, nullable = false)
    private String sideEffects;

    @Column(nullable = false)
    private String contraIndications;

    @Column(name = "medication_interactions", length = 1000, nullable = false)
    private String medicationInteractions;

    @Column(nullable = false)
    private String storageInstructions;

    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalEntity hospital;

    public MedicationEntity(String name, String dosage, String administrationRoute, String administrationInterval, WorkerEntity prescriberWorker, Instant prescriptionDate, Instant expirationDate, Integer quantity, String usageInstructions, String indications, String sideEffects, String contraIndications, String medicationInteractions, String storageInstructions, AppointmentEntity appointment) {
        this.name = name;
        this.dosage = dosage;
        this.administrationRoute = administrationRoute;
        this.administrationInterval = administrationInterval;
        this.prescriberWorker = prescriberWorker;
        this.prescriptionDate = prescriptionDate;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.usageInstructions = usageInstructions;
        this.indications = indications;
        this.sideEffects = sideEffects;
        this.contraIndications = contraIndications;
        this.medicationInteractions = medicationInteractions;
        this.storageInstructions = storageInstructions;
        this.appointment = appointment;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        MedicationEntity that = (MedicationEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}