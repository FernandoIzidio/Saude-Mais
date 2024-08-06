package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Entities.ExpertisesEntity;
import com.saude.mais.agendamento.Repositories.ExpertisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertisesService {
    ExpertisesRepository expertisesRepository;

    @Autowired
    public ExpertisesService(ExpertisesRepository expertisesRepository){
        this.expertisesRepository = expertisesRepository;
    }

    public void save(ExpertisesEntity expertise){
        expertisesRepository.save(expertise);
    }

    public void saveAll(List<ExpertisesEntity> expertises){
        expertisesRepository.saveAll(expertises);
    }


    public List<ExpertisesEntity> findAll(){
        return expertisesRepository.findAll();
    }

    public ExpertisesEntity findById(Long id){
        return expertisesRepository.findById(id).get();
    }
    public void delete(ExpertisesEntity expertise){
        expertisesRepository.delete(expertise);
    }


}
