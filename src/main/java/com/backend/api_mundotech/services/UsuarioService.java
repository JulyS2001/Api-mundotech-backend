package com.backend.api_mundotech.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.backend.api_mundotech.dtos.RegisterRequest;
import com.backend.api_mundotech.models.Usuario;

public interface UsuarioService extends UserDetailsService {
    void registrarUsuario(RegisterRequest request) throws Exception;
    Usuario obtenerPorEmail(String email);
}
