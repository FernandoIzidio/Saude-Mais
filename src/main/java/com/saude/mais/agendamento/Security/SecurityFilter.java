package com.saude.mais.agendamento.Security;

import com.saude.mais.agendamento.Repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    TokenService tokenService;
    UserRepository userRepository;

    @Autowired
    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("SecurityFilter: doFilterInternal called");
        System.out.println(request.getRequestURI());
        System.out.println(request.getMethod());
        String token = recoverToken(request);
        if (token != null) {
            System.out.println("Token found: " + token);
            String username = tokenService.validateToken(token);
            if (username != null) {
                UserDetails user = userRepository.findByUser(username);
                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("User authenticated: " + username);
                } else {
                    System.out.println("User not found for user: " + username);
                }
            } else {
                System.out.println("Token is invalid or expired");
            }
        } else {
            System.out.println("Token is null or empty");
        }
        filterChain.doFilter(request, response);
    }

    public String recoverToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty()) {
            System.out.println("Authorization header is missing or empty");
            return null;
        }

        System.out.println("Token received: " + token);
        return token.replace("Bearer ", "");
    }
}
