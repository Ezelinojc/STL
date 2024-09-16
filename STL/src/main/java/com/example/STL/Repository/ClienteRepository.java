package com.example.STL.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.STL.Model.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	@Query("select c from Cliente c where c.nome =?1")
	Cliente findByCliente(String nome);

}
