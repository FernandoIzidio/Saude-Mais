package com.saude.mais.agendamento.Dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;


public record HospitalRegisterDto(
        @Valid
        @NotNull(message = "User entity cannot be null")
        RegisterEntityDto registerEntityDto,

        @Valid
        @NotNull(message = "Hospital entity cannot be null")
        HospitalEntityDto hospitalEntityDto
) implements Serializable {
}
