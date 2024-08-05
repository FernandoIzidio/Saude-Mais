package com.saude.mais.agendamento.Entities;

import com.saude.mais.agendamento.Dtos.HospitalDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HospitalEntity implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;


    @Column(nullable = false, length = 14, unique = true)
    private String cnpj;

    @Column(nullable = false, unique = true)
    private String subdomain;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;


    @Column(nullable = false, length = 10)
    private String primaryPhone;


    @Column(length = 10)
    private String secondaryPhone;


    @Column(nullable = false, length = 255, unique = true)
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

    public HospitalDto toHospitalDto(){
        return new HospitalDto(getId(), getName(), getCnpj(), getSubdomain(), getAddress().toAddressDto(), getPrimaryPhone(), getSecondaryPhone(), getEmail()).prettyData();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        HospitalEntity that = (HospitalEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
