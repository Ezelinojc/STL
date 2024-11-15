package com.example.STL.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.STL.Repository.FuncionarioRepository;
import com.example.STL.Repository.ProdutoRepository;

@Service
public class EntrendaProdutoService {
	
	
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

}
