package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.HospitalEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link HospitalEntity}
 */
public record HospitalEntityDto(String name, String cnpj, String domainName, AddressEntityDto  address, String primaryPhone,
                                String secondaryPhone, String email, String sanitaryLicenseNumber,
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate licenseExpirationDate) implements Serializable {
}