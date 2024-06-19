package com.saude.mais.agendamento.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkerEntity implements Serializable {

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToMany
    @JoinTable(name = "worker_expertises",
               joinColumns = @JoinColumn(name = "worker_id"),
               inverseJoinColumns = @JoinColumn(name = "expertise_id")
              )
    List<ExpertisesEntity> expertises = new ArrayList<>();



    @ManyToMany
    @JoinTable(name = "worker_hours",
               joinColumns = @JoinColumn(name = "worker_id"),
               inverseJoinColumns = @JoinColumn(name = "hours_id")
              )
    List<OfficeHoursEntity> officeHours = new ArrayList<>();


    @EqualsAndHashCode.Include
    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "workers")
    List<AppointmentEntity> appointments = new ArrayList<>();



    public WorkerEntity(String firstName, String lastName, List<ExpertisesEntity> expertises, List<OfficeHoursEntity> officeHours, UserEntity user) {
        this.expertises = expertises;
        this.officeHours = officeHours;
        this.user = user;
    }

}
