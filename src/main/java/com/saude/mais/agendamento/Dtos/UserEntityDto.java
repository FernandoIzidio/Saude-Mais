package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.User.UserRole;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.User.UserEntity}
 */
public record UserEntityDto(
        String firstName,
        String lastName,
        String username,
        String password,
        String email,
        String phone,
        String cpf,
        UserRole role,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
        List<AddressEntityDto> address
) implements Serializable {
}

