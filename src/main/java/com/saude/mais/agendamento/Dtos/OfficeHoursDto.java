package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.OfficeHoursEntity;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.OfficeHoursEntity}
 */
public record OfficeHoursDto(
        @NotBlank(message = "Número de dias da semana é obrigatório")
        String weekDay,
        @NotBlank(message = "Horário de inicio de expediente é obrigatório")
        String startHour,
        @NotBlank(message = "Horário de fim de expediente é obrigatório")
        String endHour
) implements Serializable {

    public OfficeHoursEntity toOfficeHoursEntity() {
        return new OfficeHoursEntity(this.weekDay, this.startHour, this.endHour);
    }

}