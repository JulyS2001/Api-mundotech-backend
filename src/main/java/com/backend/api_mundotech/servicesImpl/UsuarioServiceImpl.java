package com.backend.api_mundotech.servicesImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.api_mundotech.dtos.RegisterRequest;
import com.backend.api_mundotech.models.Usuario;
import com.backend.api_mundotech.repositories.UsuarioRepository;
import com.backend.api_mundotech.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
	
	private final PasswordEncoder passwordEncoder;

    
    private final UsuarioRepository usuarioRepository;

    @Override
    public void registrarUsuario(RegisterRequest request) throws Exception {
        if(usuarioRepository.existsByEmail(request.getEmail())) {
            throw new Exception("El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setContrasenia(passwordEncoder.encode(request.getContrasenia()));
        usuario.setRol(request.getRol() != null ? request.getRol() : "USER");

        usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(usuario.getEmail(), usuario.getContrasenia(), new ArrayList<>());
    }

    @Override
    public Usuario obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
}
