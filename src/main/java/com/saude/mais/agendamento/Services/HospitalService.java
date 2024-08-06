package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Dtos.HospitalDto;
import com.saude.mais.agendamento.Entities.AddressEntity;
import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Repositories.HospitalRepository;
import com.saude.mais.agendamento.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class HospitalService {

    private UserRepository userRepository;
    private AddressService addressService;
    private HospitalRepository hospitalRepository;
    private UserService userService;

    public HospitalService(UserRepository userRepository, AddressService addressService, HospitalRepository hospitalRepository, UserService userService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.hospitalRepository = hospitalRepository;
        this.userService = userService;
    }

    public HospitalEntity findById(Long id){
        return hospitalRepository.findById(id).orElse(null);
    }

    public HospitalEntity findByCnpj(String cnpj){
        return hospitalRepository.findByCnpj(cnpj);
    }

    public HospitalEntity findBySubdomain(String subdomain){
        return hospitalRepository.findBySubdomain(subdomain);
    }

    public HospitalEntity findByEmail(String email){
        return hospitalRepository.findByEmail(email);
    }

    public List<HospitalEntity> findAll(){
        return hospitalRepository.findAll();
    }


    public BindingResult validate(HospitalDto hospitalDto, BindingResult bindingResult){

        String hospitalName = hospitalDto.subdomain().trim().replaceAll("\\s+", "").toLowerCase();
        String subdomain = "www." + hospitalName + ".saude-mais.com.br";


        if (findBySubdomain(subdomain) != null){
            bindingResult.rejectValue("hospitalDto.subdomain", "error.hospitalDto", "Subdomínio já cadastrado.");
        }

        if (findByCnpj(hospitalDto.cnpj()) != null){
            bindingResult.rejectValue("hospitalDto.cnpj", "error.hospitalDto", "CNPJ já cadastrado.");
        }

        if (findByEmail(hospitalDto.email()) != null){
            bindingResult.rejectValue("hospitalDto.email", "error.hospitalDto", "Email já cadastrado");
        }


        return bindingResult;
    }

    @Transactional
    public void save(HospitalEntity hospital, UserEntity user, String platform) {

        String scriptPath = "src/main/java/com/saude/mais/agendamento/Scripts/tenantCreator.sh";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(scriptPath, hospital.getSubdomain(), platform);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }

        AddressEntity addressEntity = hospital.getAddress();
        addressService.save(addressEntity);
        hospitalRepository.save(hospital);

        user.getHospitals().add(hospital);
        userService.save(user);
    }



    public void saveAll(List<HospitalEntity> hospitalEntities){
        hospitalRepository.saveAll(hospitalEntities);
    }

    public void delete(Long id){
        hospitalRepository.deleteById(id);
    }

}
