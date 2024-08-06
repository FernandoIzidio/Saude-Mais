package com.saude.mais.agendamento.Dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;


public record HospitalRegisterDto(
        @Valid
        @NotNull(message = "Usuário não pode ser nulo")
        UserDto userDto,

        @Valid
        @NotNull(message = "Hospital não pode ser nullo")
        HospitalDto hospitalDto,

        @NotBlank
        String platform
) implements Serializable {
}
