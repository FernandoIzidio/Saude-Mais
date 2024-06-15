package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<HospitalEntity, Long> {
}