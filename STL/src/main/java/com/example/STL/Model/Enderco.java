package com.example.STL.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Enderco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String provincia;
	private String bairro;
	private String telefone;
	private String email;

}
