package com.backend.api_mundotech.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.api_mundotech.models.Pedido;


public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	List<Pedido> findByUsuarioIdUsuario(int idUsuario);
}
