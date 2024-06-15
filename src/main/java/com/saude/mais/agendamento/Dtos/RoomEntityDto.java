package com.saude.mais.agendamento.Dtos;

import java.io.Serializable;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.RoomEntity}
 */
public record RoomEntityDto(String number, String type) implements Serializable {
}