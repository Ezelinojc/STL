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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int quantidade;
	private Double precoUnitario=0.;
	private Double valortotal=0.;
	
	@ManyToOne
    private Produto produto;
	
	@ManyToOne
	private Venda venda;
	
}
