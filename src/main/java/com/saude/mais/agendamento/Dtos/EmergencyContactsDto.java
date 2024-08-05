package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.EmergencyContactsEntity;

import java.io.Serializable;

/**
 * DTO for {@link EmergencyContactsEntity}
 */
public record EmergencyContactsDto(String firstName, String lastName, String phone,
                                   String relationship) implements Serializable {

    public EmergencyContactsEntity toEmergencyContactsEntity() {
        return new EmergencyContactsEntity(firstName, lastName, phone, relationship);
    }
}