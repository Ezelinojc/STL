package com.example.STL.Controller;

import java.nio.file.NoSuchFileException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.STL.Model.Produto;
import com.example.STL.Service.ProdutoService;

@Controller
@RequestMapping("/stl/produtos/")
public class ProdutosController {
	
	@Autowired private ProdutoService produtoService ;
	
	@GetMapping("cadastrar")
	public ModelAndView add(Produto produto) {
		return this.produtoService.add(produto);
	}
	
	@PostMapping("salvar")
	public ModelAndView salvar(@Valid Produto produto, BindingResult br, RedirectAttributes at) {
		return this.produtoService.salvar(produto, br, at);
	}
	
	// EDITAR
	@PostMapping("editar")
	public ModelAndView editar(@RequestParam("id") Long id) throws NoSuchFileException {
		return this.produtoService.editar(id);
	}
	
	@GetMapping("listar")
	public ModelAndView lista() {
		return this.produtoService.lista();
	}

	// EXCLUIR
	@GetMapping("excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes rd) {
		return this.produtoService.eliminar(id, rd);
	}

	


}
