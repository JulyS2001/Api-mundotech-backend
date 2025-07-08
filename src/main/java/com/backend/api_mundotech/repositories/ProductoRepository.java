package com.backend.api_mundotech.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.api_mundotech.models.Producto;


public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	
	List<Producto> findByCategoriaIdCategoria(int idCategoria);
	
	List<Producto> findByNombreContainingIgnoreCase(String nombre);
	
}
