package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}