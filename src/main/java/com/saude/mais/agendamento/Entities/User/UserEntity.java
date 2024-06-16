package com.saude.mais.agendamento.Entities.User;
import com.saude.mais.agendamento.Entities.AddressEntity;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="tb_user")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class UserEntity implements Serializable, UserDetails {

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @EqualsAndHashCode.Include
    @Column(nullable = false, name = "username", unique = true)
    private String user;

    @Column(nullable = false)
    private String password;

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String phone;

    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;


    @Column(nullable = false)
    private UserRole role;

    @Column(name = "birth_date", nullable = false)
    private Instant birthDate;



    @Setter(AccessLevel.NONE)
    @ManyToMany
        @JoinTable(
            name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
        )
    private List<AddressEntity> address = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name = "user_hospital",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hospital_id")
    )
    List<HospitalEntity> hospitals = new ArrayList<>();

    
    public UserEntity(String firstName, String lastName, String username, String password, String email, String phone, String cpf, UserRole role, Instant birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.role = role;
        this.birthDate = birthDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_WORKER"), new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        if (this.role == UserRole.WORKER) return List.of(new SimpleGrantedAuthority("ROLE_WORKER"));

        return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }



    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
