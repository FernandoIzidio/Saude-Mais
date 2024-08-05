package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.HospitalEntity;
import com.saude.mais.agendamento.Entities.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {

//    @Query("SELECT w FROM WorkerEntity w WHERE w.user.hospital.id = :hospitalId")
//    List<WorkerEntity> findAllByHospitalId(@Param("hospitalId") Long hospitalId);

}