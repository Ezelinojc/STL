package com.example.STL.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import lombok.Data;

@Entity
@Data
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDate dataVenda = LocalDate.now();
	private String formaPagamento;
	private BigDecimal valorTotal;

	private BigDecimal imposto;
	private BigDecimal troco;


	@ManyToOne
	private Cliente cliente;

	@ManyToMany
	private List<Produto> produtos;

	@OneToMany
	private List<ItemVenda> itens;

}
