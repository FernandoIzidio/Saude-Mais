package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.AppointmentEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link AppointmentEntity}
 */
public record AppointmentDto(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   LocalDate date, Long customerId, List<Long> workersId,
                             String room, String type) implements Serializable {
}