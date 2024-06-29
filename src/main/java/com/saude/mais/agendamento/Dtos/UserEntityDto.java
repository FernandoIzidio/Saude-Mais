package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.Gender;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.catalina.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para {@link com.saude.mais.agendamento.Entities.User.UserEntity}
 */
public record UserEntityDto(
        @NotBlank(message = "O nome não pode estar em branco")
        String firstName,

        @NotBlank(message = "O sobrenome não pode estar em branco")
        String lastName,

        @NotNull(message = "O sexo biológico não pode ser nulo")
        Gender gender,

        @NotBlank(message = "O nome de usuário não pode estar em branco")
        String username,

        @NotBlank(message = "A senha não pode estar em branco")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{8,}$",
                message = "A senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula, um número e um caractere especial")
        String password,

        @Email(message = "Formato de email inválido")
        @NotBlank(message = "O email não pode estar em branco")
        String email,

        @Pattern(regexp = "^\\(\\d{2}\\)\\s9\\d{4}-\\d{4}$",
                message = "Número de celular deve estar no formato (XX) 9XXXX-XXXX ")
        @NotBlank(message = "O telefone não pode estar em branco")
        String phone,

        @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
                message = "Formato de CPF inválido. Deve estar no formato XXX.XXX.XXX-XX")
        @NotBlank(message = "O CPF não pode estar em branco")
        String cpf,

        @NotNull(message = "O papel do usuário não pode ser nulo")
        UserRole role,

        @NotNull(message = "A data de nascimento não pode ser nula")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate,

        @Valid
        List<AddressEntityDto> addresses,

        @Valid
        List<HospitalEntityDto> hospitals

) implements Serializable {

    public UserEntityDto cleanData() {
        return new UserEntityDto(
                this.firstName.trim(),
                this.lastName.trim(),
                this.gender,
                this.username.trim(),
                this.password,
                this.email.trim(),
                this.phone.replaceAll("\\D", ""),
                this.cpf.replaceAll("\\D", ""),
                this.role,
                this.birthdate,
                this.addresses,
                this.hospitals
        );
    }

    public UserEntityDto prettyData() {
        String formattedPhone = this.phone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");

        String formattedCpf = this.cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");

        return new UserEntityDto(
                this.firstName,
                this.lastName,
                this.gender,
                this.username,
                this.password,
                this.email,
                formattedPhone,
                formattedCpf,
                this.role,
                this.birthdate,
                this.addresses,
                this.hospitals
        );
    }


    public  UserEntity createUserEntity() {
        return new UserEntity(firstName(),lastName(), gender(), username(), password(), email(), phone(), cpf(), role(), birthdate());
    }

    public static UserEntityDto createNullUserEntityDto(){
        List<AddressEntityDto> address = new ArrayList<>();
        List<HospitalEntityDto> hospitals = new ArrayList<>();

        return new UserEntityDto("", "", null, "", "", "", "", "", null, null, address, hospitals);
    }
}

