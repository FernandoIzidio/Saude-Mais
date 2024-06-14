package com.saude.mais.agendamento.Entities;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firt_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    private Short age;
    private String gender;

    @ManyToMany
    @JoinTable(
            name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<AddressEntity> address;

    public CustomerEntity() {}

    public CustomerEntity(String firstName, String lastName, Short age, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Short getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
