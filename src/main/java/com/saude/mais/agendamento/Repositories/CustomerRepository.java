package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}