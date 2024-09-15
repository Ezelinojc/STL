package com.example.STL.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private float valorTotal;

	@Transient
	private float troco;

	@ManyToOne
	private Cliente cliente;
	@ManyToOne
	private ItemVenda itemVenda;

}
