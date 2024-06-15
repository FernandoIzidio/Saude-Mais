package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.ProceduresEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProceduresRepository extends JpaRepository<ProceduresEntity, Long> {
}