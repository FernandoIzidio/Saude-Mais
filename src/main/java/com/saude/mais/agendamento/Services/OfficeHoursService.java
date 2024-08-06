package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Entities.OfficeHoursEntity;
import com.saude.mais.agendamento.Repositories.OfficeHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeHoursService {

    public OfficeHoursRepository officeHoursRepository;

    @Autowired
    public OfficeHoursService(OfficeHoursRepository officeHoursRepository) {
        this.officeHoursRepository = officeHoursRepository;
    }

    public OfficeHoursEntity findById(Long id) {
        return officeHoursRepository.findById(id).orElse(null);
    }

    public List<OfficeHoursEntity> findAll(){
        return officeHoursRepository.findAll();
    }

    public void save(OfficeHoursEntity officeHoursEntity) {
         officeHoursRepository.save(officeHoursEntity);
    }


    public void saveAll(List<OfficeHoursEntity> officeHoursEntities) {
        officeHoursRepository.saveAll(officeHoursEntities);
    }

}
