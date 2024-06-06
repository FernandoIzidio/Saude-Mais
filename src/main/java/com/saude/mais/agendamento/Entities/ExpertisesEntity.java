package com.saude.mais.agendamento.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expertises")
public class ExpertisesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(nullable = false)
    private String name;

    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "expertises")
    private List<WorkerEntity> doctors;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalEntity hospital;

    public ExpertisesEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}

