package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}