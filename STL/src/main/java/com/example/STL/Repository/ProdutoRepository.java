package com.example.STL.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.STL.Model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Query("select c from Produto c where c.codigoBarra =?1")
	List<Produto> findByCdgProduto(String codigo);

	
}
