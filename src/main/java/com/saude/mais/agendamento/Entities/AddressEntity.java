package com.saude.mais.agendamento.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class AddressEntity implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String neighborhood;
    private String number;
    private String city;
    private String state;
    private String zip;


    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private HospitalEntity hospital;


    public AddressEntity(String street, String neighborhood, String number, String city, String state, String zip) {
        this.street = street;
        this.neighborhood = neighborhood;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}
