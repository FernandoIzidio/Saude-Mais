package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.EmergencyContactsEntity;

import java.io.Serializable;

/**
 * DTO for {@link EmergencyContactsEntity}
 */
public record EmergencyContactsEntityDto(Long id, String firstName, String lastName, String phone,
                                         String relationship) implements Serializable {
}