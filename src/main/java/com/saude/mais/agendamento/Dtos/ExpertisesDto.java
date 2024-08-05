package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.ExpertisesEntity;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.ExpertisesEntity}
 */
public record ExpertisesDto(
        @NotBlank(message = "Nome de expertise é obrigatório")
        String name
) implements Serializable {

    public ExpertisesEntity toExpertisesEntity() {
        return new ExpertisesEntity(name);
    }
}