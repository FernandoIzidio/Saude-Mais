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

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerEntity implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;


    @OneToMany(mappedBy = "customer")
    List<EmergencyContactsEntity> emergencyContacts;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    List<AppointmentEntity> appointments = new ArrayList<>();



    public CustomerEntity(UserEntity user, List<EmergencyContactsEntity> emergencyContacts) {
        this.user = user;
        this.emergencyContacts = emergencyContacts;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CustomerEntity customer = (CustomerEntity) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
