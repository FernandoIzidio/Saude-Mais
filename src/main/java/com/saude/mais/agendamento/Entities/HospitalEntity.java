package com.saude.mais.agendamento.Entities;

import com.saude.mais.agendamento.Dtos.HospitalEntityDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HospitalEntity implements Serializable {
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @EqualsAndHashCode.Include
    @Column(nullable = false, length = 14, unique = true)
    private String cnpj;

    @Column(nullable = false, unique = true)
    private String subdomain;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @EqualsAndHashCode.Include
    @Column(nullable = false, length = 10)
    private String primaryPhone;

    @EqualsAndHashCode.Include
    @Column(length = 10)
    private String secondaryPhone;

    @EqualsAndHashCode.Include
    @Column(nullable = false, length = 255)
    private String email;

    public HospitalEntity(String name, String cnpj, String subdomain, AddressEntity address, String primaryPhone, String secondaryPhone, String email) {
        this.name = name;
        this.cnpj = cnpj;
        this.subdomain = subdomain;
        this.address = address;
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.email = email;
    }

    public HospitalEntityDto toHospitalEntityDto(){
        return new HospitalEntityDto(getName(), getCnpj(), getSubdomain(), getAddress().toAddressEntityDto(), getPrimaryPhone(), getSecondaryPhone(), getEmail());
    }
}
