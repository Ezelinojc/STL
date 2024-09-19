package com.example.STL.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String codigoBarra;
	private String nome;
	private Double preco;
	private String categoria;	
	private String marca;
	private Double quantidadeStok=0.;

	

}
