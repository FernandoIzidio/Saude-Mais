package com.saude.mais.agendamento.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

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
    private String website;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @EqualsAndHashCode.Include
    @Column(nullable = false, length = 11)
    private String primaryPhone;

    @EqualsAndHashCode.Include
    @Column(length = 11)
    private String secondaryPhone;

    @EqualsAndHashCode.Include
    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private String sanitaryLicenseNumber;

    @Column(nullable = false)
    private Instant licenseExpirationDate;

    public HospitalEntity(String name, String cnpj, String website,  AddressEntity address, String primaryPhone, String secondaryPhone, String email, String sanitaryLicenseNumber, Instant licenseExpirationDate) {
        this.name = name;
        this.cnpj = cnpj;
        this.website = website;
        this.address = address;
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.email = email;
        this.sanitaryLicenseNumber = sanitaryLicenseNumber;
        this.licenseExpirationDate = licenseExpirationDate;
    }
}
