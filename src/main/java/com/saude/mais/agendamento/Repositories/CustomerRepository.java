package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.CustomerEntity;
import com.saude.mais.agendamento.Entities.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
//    @Query("SELECT c FROM CustomerEntity c WHERE c.user.hospital.id = :hospitalId")
//    List<CustomerEntity> findAllByHospitalId(@Param("hospitalId") Long hospitalId);
}