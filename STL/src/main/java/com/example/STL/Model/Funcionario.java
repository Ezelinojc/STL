package com.example.STL.Model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Nome não pode ser vazio")
	@Size(min = 3, max = 55, message = "Nome deve ter entre 3 e 55 caracteres")
	@Pattern(regexp = "^[\\p{L}a-zA-Z ]+$", message = "O nome deve conter apenas letras e acentos!")
	private String nome;

	@Email
	private String email;

	private String codigoRecuperarSenha;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnvioCdg;

	@NotBlank(message = "login n pode ser vazio")
	@Size(min = 3, max = 15, message = "Nome deve ter entre 3 e 15 caracteres")
	private String login;

	@NotBlank(message = "senha não pode ser vazio")
	@Size(min = 4, message = "No minímo 4 caracteres")
	private String senha;

	@NotBlank(message = "Número de telefone não pode estar em branco")
	private String telefone;
	@NotBlank(message = "Número de papel não pode estar em branco")
	private String role;
	private String caminhoImg;

	private boolean estado = true;

	@ManyToOne
	private Enderco enderco;

}
