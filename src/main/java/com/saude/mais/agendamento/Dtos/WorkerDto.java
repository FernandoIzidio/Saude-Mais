package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.ExpertisesEntity;
import com.saude.mais.agendamento.Entities.WorkerEntity;
import jakarta.validation.Valid;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.WorkerEntity}
 */
public record WorkerDto(
                                @Valid
                                UserDto user,

                                @Valid
                                List<ExpertisesDto> expertises,

                                @Valid
                                OfficeHoursDto officeHours
) implements Serializable {
    public WorkerEntity toWorkerEntity() {
        List<ExpertisesEntity> expertisesEntities = expertises.stream()
                .map(ExpertisesDto::toExpertisesEntity)
                .toList();


        return new WorkerEntity(user.cleanData().toUserEntity(), expertisesEntities, officeHours.toOfficeHoursEntity());
    }



}