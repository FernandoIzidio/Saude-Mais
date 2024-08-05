package com.saude.mais.agendamento.Dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthDto(
        @NotBlank(message = "Usuário inválido")
        String username,
        @NotBlank(message = "Senha inválida")
        String password) {
}
