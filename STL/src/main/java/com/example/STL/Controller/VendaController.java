package com.example.STL.Controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@Autowired
	private VendaService vendaService;

	@GetMapping("cadastrar")
	public ModelAndView add(Venda venda) {
		return this.vendaService.add(venda);
	}



	@PostMapping("salvar")
	 
	public ModelAndView registrarVenda(@ModelAttribute Venda venda, @RequestParam("desconto") Optional<Double> desconto, BindingResult br) throws Exception {
		return vendaService.realizarVenda(venda, desconto, br);
	}

	// EDITAR
	/*
	 * @PostMapping("editar") public ModelAndView editar(@RequestParam("id") Long
	 * id) throws NoSuchFileException { return this.vendaService.editar(id); }
	 */

	@GetMapping("listar")
	public ModelAndView lista() {
		return this.vendaService.lista();
	}
	
	@GetMapping("ver-produto")
	public ModelAndView listar() {
		return this.vendaService.lista();
	}


	@PostMapping("pesquisar")
	public ModelAndView pesquisarBI(@RequestParam("codigo") String codigo, RedirectAttributes rd) {
		return this.vendaService.pesquisarCodigo(codigo, rd);
	}

}
