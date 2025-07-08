package com.backend.api_mundotech.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {
	
	    private String nombre;
	    private String email;
	    private String contrasenia;
	    private String rol;

}
