package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.AppointmentEntity;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link AppointmentEntity}
 */
public record AppointmentEntityDto(Instant date, CustomerEntityDto customer, List<WorkerEntityDto> workers,
                                   RoomEntityDto room, String type, String state, String observations,
                                   String diagnostic, List<ProceduresEntityDto> procedures,
                                   List<MedicationEntityDto> medications) implements Serializable {
}