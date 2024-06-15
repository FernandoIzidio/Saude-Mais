package com.saude.mais.agendamento.Dtos;

import java.io.Serializable;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.OfficeHoursEntity}
 */
public record OfficeHoursEntityDto(String weekDay, String startHour, String endHour) implements Serializable {
}