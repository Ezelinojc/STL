package com.example.STL.Util;

import com.example.STL.Model.Funcionario;
import com.example.STL.Repository.FuncionarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioUtil {
	
	@Autowired FuncionarioRepository funcionarioRepository;
	
	
	public Funcionario funcionarioLogado() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
	        String login = authentication.getName();
	        Funcionario funcionario  =  funcionarioRepository.findByLogin(login);

	        if ( funcionario != null) {
	            System.out.println("logado: " + funcionario.getNome());
	        }
	        return  funcionario;
	    }
	    return null;
	}

}
