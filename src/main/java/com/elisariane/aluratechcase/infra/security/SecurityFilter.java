package com.elisariane.aluratechcase.infra.security;

import com.elisariane.aluratechcase.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private  TokenService tokenService;

    @Autowired
    private UserRepository repository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = getTokenJWT(request);

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            var user = repository.findByUsername(subject);

            var authetication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authetication);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenJWT(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            authorizationHeader.replace("Bearer", "");
        }

        return null;
    }
}
