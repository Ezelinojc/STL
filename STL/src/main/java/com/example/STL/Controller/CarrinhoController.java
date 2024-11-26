package com.example.STL.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.STL.Service.CarrinhoService;

@Controller
@RequestMapping("/stl")
public class CarrinhoController {

	@Autowired
	private CarrinhoService carrinhoService;



	@GetMapping("/carrinho")
	public ModelAndView carrinho() {
		return carrinhoService.chamarCarrinho();
	}

	@GetMapping("/finalizar")
	public ModelAndView finalizar() {
		return carrinhoService.finalizar();
	}
	
	@PostMapping("/finalizarCompra")
	public ModelAndView finalizarCompra(@RequestParam String formaPagamento) {
		return carrinhoService.finalizarCompra(formaPagamento);
	}

	/*
	 * @PostMapping("/adicionarCarrinho/{id}") public ModelAndView
	 * carrinho1(@PathVariable Long id) { return
	 * carrinhoService.adicionarCarrinho(id); }
	 */

	@PostMapping("/adicionarCarrinho")
	public String carrinho1(@RequestParam("codigo") String codigo) {
		return carrinhoService.adicionarCarrinho1(codigo);
	}

	@GetMapping("/alterarQuantidade/{id}/{acao}")
	public String alterarquantidade(@PathVariable Long id, @PathVariable Integer acao) {
		return this.carrinhoService.alterarquantidade(id, acao);
	}

	@GetMapping("/removerProduto/{id}")
	public String remover(@PathVariable Long id) {
		return this.carrinhoService.remover(id);
	}

}
