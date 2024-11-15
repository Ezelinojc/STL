package com.example.STL.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.STL.Model.EntradaProduto;

public interface EntraProdutoRepository extends JpaRepository<EntradaProduto, Long> {

}
