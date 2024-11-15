package com.example.STL.Model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codigoBarra;
	private String nome;
	private Double preco =0.0;
	private String categoria;
	private String marca;
	private int quantidadeStok;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFabrico;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataExpiracao;

}
