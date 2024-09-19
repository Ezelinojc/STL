package com.example.STL.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.STL.Model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {


	@Query("SELECT c FROM Produto c WHERE c.codigoBarra = :codigo OR c.marca =:codigo  OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :codigo, '%'))")
	List<Produto> findByCdgProduto(@Param("codigo") String codigo);


	
}
