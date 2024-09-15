package com.example.STL.Model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class ItemVenda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int Qtd;
	
	private float precoUnitario;
	
	private float subtotal;
	
	@ManyToOne
	private Produto produto;
	
	

}
