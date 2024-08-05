package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.EmergencyContactsEntity;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.CustomerEntity}
 */
public record CustomerEntityDto(
                                @NotNull(message = "Usuario é obrigatório")
                                UserDto user,
                                @NotNull(message = "Id de hospital é obrigatório")
                                Long hospitalId,
                                List<EmergencyContactsDto> emergencyContacts) implements Serializable {

    public static List<EmergencyContactsEntity> toEmergencyContactsEntities(List<EmergencyContactsDto> emergencyContactsDtos) {
        return emergencyContactsDtos.stream().map(EmergencyContactsDto::toEmergencyContactsEntity).toList();
    }

}