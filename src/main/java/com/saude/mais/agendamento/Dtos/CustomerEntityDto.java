package com.saude.mais.agendamento.Dtos;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.CustomerEntity}
 */
public record CustomerEntityDto(UserEntityDto user,
                                List<EmergencyContactsEntityDto> emergencyContacts) implements Serializable {
}