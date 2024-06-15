package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.EmergencyContactsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactsRepository extends JpaRepository<EmergencyContactsEntity, Long> {
}