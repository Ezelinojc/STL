package com.example.STL.Model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date dataVenda = new Date();
	private double valorTotal;
	private double desconto;

	@Transient
	private double valorPago; 
	
	@Transient
	private double troco;
	
	private int Qtd;

	private String formaPagemento;

	@ManyToOne
	private Cliente cliente;

	 @ManyToOne
	private Produto produto;

}
