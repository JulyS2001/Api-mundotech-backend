package com.backend.api_mundotech.services;

import java.util.Optional;

import com.backend.api_mundotech.models.Producto;

public interface ProductoService {
    Optional<Producto> traerPorId(int id);
    Producto guardar(Producto producto);
    void eliminar(int id);
    Producto editar(int id, Producto producto);
}