package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.AddressEntity;

import java.io.Serializable;

/**
 * DTO for {@link AddressEntity}
 */
public record AddressEntityDto(String street, String city, String state, String zip) implements Serializable {
}