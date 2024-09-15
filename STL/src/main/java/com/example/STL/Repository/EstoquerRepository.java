package com.example.STL.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.STL.Model.Estoque;


public interface EstoquerRepository extends JpaRepository<Estoque, Long> {

}
