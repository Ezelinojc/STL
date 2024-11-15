package com.example.STL.Model;

import java.math.BigDecimal;

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
	private BigDecimal precoUnitario;
	private BigDecimal total;
	
	@ManyToOne
    private Produto produto;
	
	public void calcularSubtotal() {
		this.total = this.precoUnitario.multiply(new BigDecimal(this.quantidade));
	}

}
