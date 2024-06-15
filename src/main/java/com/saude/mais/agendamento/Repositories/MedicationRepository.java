package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {
}