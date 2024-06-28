package com.saude.mais.agendamento.Dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.io.Serializable;

public record HospitalEntityDto(
        @NotBlank(message = "Nome do hospital é obrigatório")
        @Size(max = 255, message = "Nome do hospital não pode exceder 255 caracteres")
        String name,

        @NotBlank(message = "CNPJ é obrigatório")
        @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX")
        String cnpj,

        @NotBlank(message = "Subdominio é obrigatório")
        @Size(max = 70, message = "Subdominio não pode exceder 70 caracteres")
        @Pattern(regexp = "^[\\w-]+$", message = "Subdominio pode conter apenas letras, números, underscores e hífens")
        String subdomain,

        @Valid
        AddressEntityDto address,

        @NotBlank(message = "Telefone principal é obrigatório")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{4}-\\d{4}", message = "Telefone principal deve estar no formato (XX) XXXX-XXXX ")
        String primaryPhone,

        @NotBlank(message = "Telefone secundário é obrigatório")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{4}-\\d{4}", message = "Telefone secundário deve estar no formato (XX) XXXX-XXXX")
        String secondaryPhone,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        String email
) implements Serializable {

        public HospitalEntityDto cleanData() {
                return new HospitalEntityDto(
                        this.name,
                        this.cnpj.replaceAll("\\D", ""),
                        this.subdomain,
                        this.address,
                        this.primaryPhone.replaceAll("\\D", ""),
                        this.secondaryPhone.replaceAll("\\D", ""),
                        this.email
                );
        }
}