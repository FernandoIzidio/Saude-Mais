package com.saude.mais.agendamento.Dtos;

import java.io.Serializable;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.RoomEntity}
 */
public record RoomDto(String number, String type) implements Serializable {
}