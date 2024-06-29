package com.saude.mais.agendamento.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


public record AddressEntityDto(
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

        @NotBlank(message = "Estado é obrigatório")
        String state,

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato XXXXX-XXX")
        String zip
) implements Serializable {

        public AddressEntityDto cleanData() {
                return new AddressEntityDto(
                        this.street,
                        this.neighborhood,
                        this.number,
                        this.city,
                        this.state,
                        this.zip.replaceAll("[^\\d]", "")
                );
        }

        public static AddressEntityDto createNullAddressEntityDto(){
                return new AddressEntityDto("", "", "", "", "", "");
        }
}

