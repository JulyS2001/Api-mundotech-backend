package com.backend.api_mundotech.dtos;

import com.backend.api_mundotech.models.Usuario;

import lombok.Getter;


@Getter
public class LoginResponse {
	
	private String token;
	private Usuario usuario; 

    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario; 
    }

}
