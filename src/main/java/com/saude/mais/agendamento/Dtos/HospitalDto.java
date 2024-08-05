package com.saude.mais.agendamento.Dtos;

import com.saude.mais.agendamento.Entities.HospitalEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO for {@link HospitalDto}
 */
public record HospitalDto(
        Long id,

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
        AddressDto address,

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

    public HospitalDto cleanData() {
        return new HospitalDto(
                this.id,
                this.name,
                this.cnpj.replaceAll("\\D", ""),
                this.subdomain,
                this.address.cleanData(),
                this.primaryPhone.replaceAll("\\D", ""),
                this.secondaryPhone.replaceAll("\\D", ""),
                this.email
        );
    }

    public HospitalDto prettyData() {
        String formattedCnpj = this.cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
        String formattedPrimaryPhone = this.primaryPhone.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        String formattedSecondaryPhone = this.secondaryPhone.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        String formattedSubdomain = this.subdomain.replace("www.", "").replace(".saude-mais.com.br", "");

        return new HospitalDto(
                this.id,
                this.name,
                formattedCnpj,
                formattedSubdomain,
                this.address.prettyData(),
                formattedPrimaryPhone,
                formattedSecondaryPhone,
                this.email
        );
    }


    public HospitalEntity toHospitalEntity(){
        String hospitalName = subdomain().trim().replaceAll("\\s+", "").toLowerCase();
        String subdomain = "www." + hospitalName + ".saude-mais.com.br";
        HospitalDto hospitalDto = this.cleanData();

        return new HospitalEntity(hospitalDto.id(), hospitalDto.name(), hospitalDto.cnpj(), subdomain, hospitalDto.address().toAddressEntity(), hospitalDto.primaryPhone(), hospitalDto.secondaryPhone(), hospitalDto.email());
    }
}