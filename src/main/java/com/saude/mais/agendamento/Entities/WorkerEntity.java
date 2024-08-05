package com.saude.mais.agendamento.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkerEntity implements Serializable {


    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;



    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;


    @ManyToMany
    @JoinTable(name = "worker_expertises",
               joinColumns = @JoinColumn(name = "worker_id"),
               inverseJoinColumns = @JoinColumn(name = "expertise_id")
              )
    List<ExpertisesEntity> expertises = new ArrayList<>();



    @ManyToOne
    @JoinColumn(
            name = "officehour_id"
    )
    OfficeHoursEntity officeHours;



    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "workers")
    List<AppointmentEntity> appointments = new ArrayList<>();



    public WorkerEntity( UserEntity user, List<ExpertisesEntity> expertises, OfficeHoursEntity officeHours) {
        this.expertises = expertises;
        this.officeHours = officeHours;
        this.user = user;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        WorkerEntity that = (WorkerEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
