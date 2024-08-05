package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.User.UserEntity;

public record AuthResponseDto(String token, UserDto user) {
}
