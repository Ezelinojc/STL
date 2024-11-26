package com.example.STL.Model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private LocalDate dataVenda = LocalDate.now();

	private String formaPagamento;

	private Double valorTotal = 0.;

	private Double desconto = 0.;
	private Double imposto;
	@Transient
	private Double troco;
	private Double valorPago = 0.;

	@ManyToOne
	private Cliente cliente;

	@OneToMany
	private List<ItemVenda> itens;

}
