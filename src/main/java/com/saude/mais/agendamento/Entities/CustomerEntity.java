package com.saude.mais.agendamento.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CustomerEntity implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
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
}
