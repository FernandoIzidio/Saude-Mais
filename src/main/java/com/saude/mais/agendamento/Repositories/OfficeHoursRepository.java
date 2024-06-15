package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.OfficeHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeHoursRepository extends JpaRepository<OfficeHoursEntity, Long> {
}