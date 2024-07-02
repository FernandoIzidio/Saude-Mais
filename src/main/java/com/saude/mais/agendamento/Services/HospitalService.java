package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Dtos.AddressEntityDto;
import com.saude.mais.agendamento.Dtos.HospitalEntityDto;
import com.saude.mais.agendamento.Entities.AddressEntity;
import com.saude.mais.agendamento.Entities.BrazilianStates;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Repositories.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.Instant;

@Service
public class HospitalService {

    private HospitalRepository hospitalRepository;

    @Autowired
    public HospitalService(HospitalRepository hospitalRepository){
        this.hospitalRepository = hospitalRepository;
    }

    public HospitalEntity findById(Long id){
        return hospitalRepository.findById(id).orElse(null);
    }

    public HospitalEntity findByCnpj(String cnpj){
        return hospitalRepository.findByCnpj(cnpj);
    }

    public HospitalEntity findBySubdomain(String website){
        return hospitalRepository.findBySubdomain(website);
    }

    public BindingResult validate(HospitalEntityDto hospitalEntityDto, BindingResult bindingResult){

        String hospitalName = hospitalEntityDto.subdomain().trim().replaceAll("\\s+", "").toLowerCase();
        String subdomain = "www." + hospitalName + ".saude-mais.com.br";

        if (findBySubdomain(subdomain) != null){
            bindingResult.rejectValue("hospitalEntityDto.subdomain", "error.hospitalEntityDto", "Website já cadastrado.");
        }

        if (findByCnpj(hospitalEntityDto.cnpj()) != null){
            bindingResult.rejectValue("hospitalEntityDto.cnpj", "error.hospitalEntityDto", "CNPJ já cadastrado.");
        }

        return bindingResult;
    }

    public void save(HospitalEntity hospitalEntity){
        hospitalRepository.save(hospitalEntity);
    }

}
