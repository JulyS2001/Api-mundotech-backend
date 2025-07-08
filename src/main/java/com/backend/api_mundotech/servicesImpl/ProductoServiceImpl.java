package com.backend.api_mundotech.servicesImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.api_mundotech.models.Producto;
import com.backend.api_mundotech.repositories.ProductoRepository;
import com.backend.api_mundotech.services.ProductoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Optional<Producto> traerPorId(int id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(int id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto editar(int id, Producto producto) {
        Optional<Producto> productoExistente = productoRepository.findById(id);
        
        if (productoExistente.isPresent()) {
            Producto p = productoExistente.get();
            p.setNombre(producto.getNombre());
            p.setDescripcion(producto.getDescripcion());
            p.setPrecio(producto.getPrecio());
            p.setImagen(producto.getImagen());
            p.setStock(producto.getStock());
            p.setCategoria(producto.getCategoria());
            return productoRepository.save(p);
        } else {
            throw new RuntimeException("Producto con ID " + id + " no encontrado");
        }
    }
}