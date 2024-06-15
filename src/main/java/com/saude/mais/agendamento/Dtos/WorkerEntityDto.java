package com.saude.mais.agendamento.Dtos;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.WorkerEntity}
 */
public record WorkerEntityDto(String firstName, String lastName, List<ExpertisesEntityDto> expertises,
                              List<OfficeHoursEntityDto> officeHours) implements Serializable {
}