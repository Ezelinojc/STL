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
import com.example.STL.Model.Venda;
import com.example.STL.Service.VendaService;

@Controller
@RequestMapping("/stl/vendas/")
public class VendaController {
	
	@Autowired private VendaService vendaService ;
	
	@GetMapping("cadastrar")
	public ModelAndView add(Venda venda) {
		return this.vendaService.add(venda);
	}
	
	@PostMapping("salvar")
	public ModelAndView salvar(@Valid Venda venda, BindingResult br, RedirectAttributes at) {
		return this.vendaService.salvar(venda, br, at);
	}
	
	// EDITAR
	@PostMapping("editar")
	public ModelAndView editar(@RequestParam("id") Long id) throws NoSuchFileException {
		return this.vendaService.editar(id);
	}
	
	@GetMapping("listar")
	public ModelAndView lista() {
		return this.vendaService.lista();
	}

	// EXCLUIR
	@GetMapping("excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes rd) {
		return this.vendaService.eliminar(id, rd);
	}

	


}
