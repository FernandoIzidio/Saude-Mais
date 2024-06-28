package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.User.Gender;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.User.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;


public record RegisterEntityDto(
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

        @NotBlank(message = "A senha não pode estar em branco")
        String password2,


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
        LocalDate birthDate

) implements Serializable {
        public RegisterEntityDto cleanData() {
                return new RegisterEntityDto(
                        this.firstName.trim(),
                        this.lastName.trim(),
                        this.gender,
                        this.username.trim(),
                        this.password,
                        this.password2,
                        this.email.trim(),
                        this.phone.replaceAll("\\D", ""),
                        this.cpf.replaceAll("\\D", ""),
                        this.role,
                        this.birthDate
                );
        }

        public RegisterEntityDto prettyData() {
                String formattedPhone = this.phone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");

                String formattedCpf = this.cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");

                return new RegisterEntityDto(
                        this.firstName,
                        this.lastName,
                        this.gender,
                        this.username,
                        this.password,
                        this.password2,
                        this.email,
                        formattedPhone,
                        formattedCpf,
                        this.role,
                        this.birthDate
                );
        }

        public UserEntity createUserEntity() {
                LocalDate birthdate = birthDate().atStartOfDay(ZoneId.systemDefault()).toLocalDate();
                String hash = new BCryptPasswordEncoder().encode(password());
                return new UserEntity(firstName(),lastName(), gender(), username(), hash, email(), phone(), cpf(), role(), birthdate);
        }

}
