package com.example.STL.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.STL.Model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
