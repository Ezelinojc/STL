package com.example.STL.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.STL.Model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}