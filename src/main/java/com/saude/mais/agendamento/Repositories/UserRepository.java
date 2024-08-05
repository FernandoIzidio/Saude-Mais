package com.saude.mais.agendamento.Repositories;

import com.saude.mais.agendamento.Entities.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserDetails findByUser(String username);
    UserEntity findByCpf(String cpf);
    UserEntity findByEmail(String email);
}
