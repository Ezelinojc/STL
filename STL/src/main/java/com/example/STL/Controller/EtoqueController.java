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
import com.example.STL.Model.Cliente;
import com.example.STL.Model.Estoque;
import com.example.STL.Service.Estoqueservice;

@Controller
@RequestMapping("/stl/estoques/")
public class EtoqueController {
	
	@Autowired private Estoqueservice estoqueService ;
	
	@GetMapping("cadastrar")
	public ModelAndView add(Estoque estoque) {
		return this.estoqueService.add(estoque);
	}
	
	@PostMapping("salvar")
	public ModelAndView salvar(@Valid Estoque estoque, BindingResult br, RedirectAttributes at) {
		return this.estoqueService.salvar(estoque, br, at);
	}
	
	// EDITAR
	@PostMapping("editar")
	public ModelAndView editar(@RequestParam("id") Long id) throws NoSuchFileException {
		return this.estoqueService.editar(id);
	}
	
	@GetMapping("listar")
	public ModelAndView lista() {
		return this.estoqueService.lista();
	}

	// EXCLUIR
	@GetMapping("excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes rd) {
		return this.estoqueService.eliminar(id, rd);
	}

	


}
