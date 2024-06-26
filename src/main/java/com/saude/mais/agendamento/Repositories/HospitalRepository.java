package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<HospitalEntity, Long> {
    HospitalEntity findByCnpj(String cnpj);
    HospitalEntity findByWebsite(String website);
}