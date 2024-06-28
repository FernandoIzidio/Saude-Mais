package com.saude.mais.agendamento.Security;

import com.saude.mais.agendamento.Entities.User.UserEntity;
import com.saude.mais.agendamento.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUser(username);

        if (user == null) throw new UsernameNotFoundException("User not found with email:" + username);

        return new org.springframework.security.core.userdetails.User(user.getUser(), user.getPassword(), user.getAuthorities());
    }
}
