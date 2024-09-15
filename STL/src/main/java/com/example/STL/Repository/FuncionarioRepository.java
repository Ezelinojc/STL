package com.example.STL.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.STL.Model.Funcionario;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	Funcionario findByNome(String nome);

	@Query("select m from Funcionario m where m.telefone =?1")
	Funcionario findByNumero(String telefone);

	@Query("select u from Funcionario u where u.login =?1")
	Funcionario findByLogin(String login);

	@Query("select u from Funcionario u order by u.nome")
	List<Funcionario> findAll1();

	@Query("select u from Funcionario u where u.nome =?1")
	Funcionario findByFuncionario(String nome);
	
	@Query("select u from Funcionario u where u.login != 'Admin' ")
	List<Funcionario> findByOcultarAdmin();

	// BUSCA VIA EMAIL NO NO BANCO DE DADOS
	Funcionario findByEmail(String email);
	
	public Funcionario findByEmailAndCodigoRecuperarSenha(String email, String codigoRecupercacaoSenha);

}
