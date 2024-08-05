package com.saude.mais.agendamento.Entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saude.mais.agendamento.Dtos.AddressDto;
import com.saude.mais.agendamento.Dtos.HospitalDto;
import com.saude.mais.agendamento.Dtos.UserDto;
import com.saude.mais.agendamento.Entities.AddressEntity;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity implements Serializable, UserDetails {


    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;


    @Column(nullable = false, name = "username", unique = true)
    private String user;

    @Column(nullable = false)
    private String password;


    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false, length = 11)
    private String phone;


    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;


    @Column(nullable = false)
    private UserRole userRole;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthdate;



    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(
            name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<AddressEntity> addresses = new ArrayList<>();



    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name = "user_hospital",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hospital_id")
    )
    List<HospitalEntity> hospitals = new ArrayList<>();


    public UserEntity(String firstName, String lastName, Gender gender, String username, String password, String email, String phone, String cpf, UserRole userRole, LocalDate birthdate, List<AddressEntity> addresses, List<HospitalEntity> hospitals) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.user = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.userRole = userRole;
        this.birthdate = birthdate;
        this.addresses = addresses;
        this.hospitals = hospitals;
    }

    public UserEntity(String firstName, String lastName, Gender gender, String username, String password, String email, String phone, String cpf, UserRole userRole, LocalDate birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.user = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.userRole = userRole;
        this.birthdate = birthdate;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userRole == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_WORKER"), new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        if (this.userRole == UserRole.WORKER) return List.of(new SimpleGrantedAuthority("ROLE_WORKER"));

        return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    
    @Override
    public String getPassword() {
        return password;
    }

    public Integer getAge(){
        return Period.between(this.birthdate, LocalDate.now()).getYears();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return user;
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDto toUserDto() {
        return new UserDto(this.id, this.firstName, this.lastName, this.gender, this.user, this.password, this.email, this.phone, this.cpf, this.userRole, this.birthdate, toAddressDtoList(), toHospitalDtoList()).prettyData();
    }


    public List<AddressDto> toAddressDtoList(){
        List<AddressDto> addresses = new ArrayList();

        getAddresses().forEach((address) -> {
            addresses.add(address.toAddressDto().prettyData());
        });

        return addresses;
    }


    public List<HospitalDto> toHospitalDtoList(){
        List<HospitalDto> hospitals = new ArrayList();

        getHospitals().forEach((hospital) -> {
            hospitals.add(hospital.toHospitalDto().prettyData());
        });

        return hospitals;
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserEntity that = (UserEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
