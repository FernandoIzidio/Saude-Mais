package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}