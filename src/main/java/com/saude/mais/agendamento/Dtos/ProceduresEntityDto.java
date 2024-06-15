package com.saude.mais.agendamento.Dtos;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.saude.mais.agendamento.Entities.ProceduresEntity}
 */
public record ProceduresEntityDto(String name, Instant date, String duration, String result) implements Serializable {
}