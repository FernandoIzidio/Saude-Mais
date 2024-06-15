package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {
}