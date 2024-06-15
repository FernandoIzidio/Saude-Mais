package com.saude.mais.agendamento.Dtos;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.MedicationEntity}
 */
public record MedicationEntityDto(String name, String dosage, String administrationRoute, String administrationInterval,
                                  Instant prescriptionDate, Instant expirationDate, Integer quantity,
                                  String usageInstructions, String indications, String sideEffects,
                                  String contraIndications, String medicationInteractions,
                                  String storageInstructions) implements Serializable {
}