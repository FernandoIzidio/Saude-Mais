package com.saude.mais.agendamento.Services;

import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Entities.WorkerEntity;
import com.saude.mais.agendamento.Repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    public WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository){
        this.workerRepository = workerRepository;
    }

    public Optional<WorkerEntity> findWorkerById(Long id){
        return workerRepository.findById(id);
    }

    public List<WorkerEntity> findAllById(List<Long> ids) {
        return workerRepository.findAllById(ids);
    }

    public List<WorkerEntity> findAllWorkers() {
        return workerRepository.findAll();
    }

//    public List<WorkerEntity> findAllByHospital(Long hospitalId){
//        return workerRepository.findAllByHospitalId(hospitalId);
//    }

    public WorkerEntity save(WorkerEntity workerEntity) {
        return workerRepository.save(workerEntity);
    }


    public void deleteWorkerById(Long id) {
        workerRepository.deleteById(id);
    }
}
