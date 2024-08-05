package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.AddressEntity;
import com.saude.mais.agendamento.Entities.BrazilianStates;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link AddressDto}
 */
public record AddressDto(
        Long id,

        @NotBlank(message = "Rua é obrigatória")
        @Size(max = 255, message = "Rua não pode exceder 255 caracteres")
        String street,

        @NotBlank(message = "Bairro é obrigatório")
        @Size(max = 255, message = "Bairro não pode exceder 255 caracteres")
        String neighborhood,

        @NotBlank(message = "Número é obrigatório")
        @Size(max = 10, message = "Número não pode exceder 10 caracteres")
        String number,

        @NotBlank(message = "Cidade é obrigatória")
        @Size(max = 255, message = "Cidade não pode exceder 255 caracteres")
        String city,

        @NotNull(message = "Estado é obrigatório")
        BrazilianStates state,

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato XXXXX-XXX")
        String zip
) implements Serializable {

    public AddressDto cleanData() {
        return new AddressDto(
                this.id,
                this.street,
                this.neighborhood,
                this.number,
                this.city,
                this.state,
                this.zip.replaceAll("\\D", "")
        );
    }

    public AddressDto prettyData() {
        String formattedZip = this.zip.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
        return new AddressDto(
                this.id,
                this.street,
                this.neighborhood,
                this.number,
                this.city,
                this.state,
                formattedZip
        );
    }


    public AddressEntity toAddressEntity() {
        AddressDto addressDto = this.cleanData();
        return new AddressEntity(addressDto.id(), addressDto.street(), addressDto.neighborhood(), addressDto.number(), addressDto.city(), addressDto.state(), addressDto.zip());
    }


}

