package com.backend.api_mundotech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.backend.api_mundotech.dtos.*;
import com.backend.api_mundotech.dtos.LoginRequest;
import com.backend.api_mundotech.dtos.LoginResponse;
import com.backend.api_mundotech.dtos.RegisterRequest;
import com.backend.api_mundotech.models.Usuario;
import com.backend.api_mundotech.security.JwtUtil;
import com.backend.api_mundotech.services.UsuarioService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest request) {
        try {
            usuarioService.registrarUsuario(request);
            return ResponseEntity.ok().body("Usuario registrado con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasenia())
            );

            final UserDetails userDetails = usuarioService.loadUserByUsername(request.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);

            Usuario usuario = usuarioService.obtenerPorEmail(request.getEmail());

            return ResponseEntity.ok(new LoginResponse(jwt, usuario));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
