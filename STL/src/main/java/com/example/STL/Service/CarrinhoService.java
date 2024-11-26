package com.example.STL.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.STL.Model.Cliente;
import com.example.STL.Model.ItemVenda;
import com.example.STL.Model.Produto;
import com.example.STL.Model.Venda;
import com.example.STL.Repository.ClienteRepository;
import com.example.STL.Repository.ItemVendaRepository;
import com.example.STL.Repository.ProdutoRepository;
import com.example.STL.Repository.VendaRepository;
import com.example.STL.Util.FuncionarioUtil;

@Service
public class CarrinhoService {

	private List<ItemVenda> itemVenda = new ArrayList<ItemVenda>();
	
	private Cliente cliente;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private ItemVendaRepository itemVendaRepository;
	
	@Autowired
	private FuncionarioUtil util;

	private Venda venda = new Venda();

	public ModelAndView chamarCarrinho() {
		ModelAndView mv = new ModelAndView("venda/carrinho");
		mv.addObject("logado", util.funcionarioLogado());
		this.calcultarTota();
		mv.addObject("ListaItem", itemVenda);
		mv.addObject("venda", venda);
		return mv;
	}

	public ModelAndView finalizar() {
		ModelAndView mv = new ModelAndView("venda/finalizar");
		mv.addObject("logado", util.funcionarioLogado());
		// this.calcultarTota();
		mv.addObject("ListaItem", itemVenda);
		mv.addObject("venda", venda);
		return mv;
	}

	public void calcultarTota() {
		venda.setValorTotal(0.);
		for (ItemVenda it : itemVenda) {
			venda.setValorTotal(venda.getValorTotal() + it.getValortotal());
		}
	}

	public ModelAndView finalizarCompra(String formaPagameto) {
		ModelAndView mv = new ModelAndView("venda/finalizar");
		mv.addObject("logado", util.funcionarioLogado());
		venda.setCliente(cliente);
		venda.setFormaPagamento(formaPagameto);
		vendaRepository.saveAndFlush(venda);
		
		for(ItemVenda v: itemVenda) {
			v.setVenda(venda);
		itemVendaRepository.saveAndFlush(v);
			
		}
		 
		itemVenda = new ArrayList<>();
		venda = new Venda();
		mv.addObject("mensagem","Compra realizada com sucesso!");
		return mv;
	}

	public String adicionarCarrinho1(@RequestParam String codigo) {
		ModelAndView mv = new ModelAndView("venda/carrinho");
		mv.addObject("logado", util.funcionarioLogado());

		Produto produto = produtoRepository.findByCdgProduto(codigo);
		if (produto == null) {
			mv.addObject("mensagem1", "Produto não encontrado!");
			mv.addObject("ListaItem", itemVenda); // Retorna a lista existente mesmo em caso de erro

		}

		// Verificar se o produto já está no carrinho
		Optional<ItemVenda> itemExistente = itemVenda.stream()
				.filter(it -> it.getProduto().getId().equals(produto.getId())).findFirst();

		if (itemExistente.isPresent()) {
			ItemVenda item = itemExistente.get();

			// Validar se o estoque é suficiente
			if (produto.getQuantidadeStok() < item.getQuantidade() + 1) {
				mv.addObject("mensagem1", "Estoque insuficiente para o produto: " + produto.getNome());
				mv.addObject("ListaItem", itemVenda);

			}

			// Atualizar quantidade e valor total
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValortotal(item.getQuantidade() * item.getPrecoUnitario());

		} else {
			if (produto.getQuantidadeStok() < 1) {
				mv.addObject("mensagem1", "Estoque insuficiente para o produto: " + produto.getNome());
				mv.addObject("ListaItem", itemVenda);

			}

			// Adicionar novo item
			ItemVenda novoItem = new ItemVenda();
			novoItem.setProduto(produto);
			novoItem.setQuantidade(1);
			novoItem.setPrecoUnitario(produto.getPreco());
			novoItem.setValortotal(produto.getPreco());
			itemVenda.add(novoItem);
		}

		// Atualizar estoque do produto
		produto.setQuantidadeStok(produto.getQuantidadeStok() - 1);
		produtoRepository.save(produto);

		mv.addObject("ListaItem", itemVenda);
		return "redirect:/stl/carrinho";
	}

	// AUMENTAR E DIMINUIR AS QUANTIDADE DOS PRODUTO APARTIR DOS BOTOES

	public String alterarquantidade(Long id, Integer acao) {

		for (ItemVenda it : itemVenda) {
			if (it.getProduto().getId().equals(id)) {
				if (acao.equals(1)) {

					it.setQuantidade(it.getQuantidade() + 1);
					it.setValortotal(0.);
					it.setValortotal(it.getValortotal() + it.getQuantidade() * it.getPrecoUnitario());

				} else if (acao.equals(0)) {
					if (it.getQuantidade() > 1) {
						it.setQuantidade(it.getQuantidade() - 1);
						it.setValortotal(it.getQuantidade() * it.getPrecoUnitario());
					}
				}
				break;
			}
		}

		return "redirect:/stl/carrinho";
	}

	public ModelAndView pesquisarCodigo(String codigo, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView("venda/carrinho");
		Produto prod = this.produtoRepository.findByCdgProduto(codigo);
		mv.addObject("logado", this.util.funcionarioLogado());
		Produto produto = new Produto();

		for (ItemVenda it : itemVenda) {
			if (it.getProduto().getId().equals(produto.getId())) {
				it.setQuantidade(it.getQuantidade() + 1);
				it.setValortotal(0.);
				it.setValortotal(it.getValortotal() + it.getQuantidade() * it.getPrecoUnitario());

			}
			if (prod == null) {
				mv.addObject("mensagem1", "Produto Não existente!");
				return mv;
			}
			break;

		}

		mv.addObject("ListaItem", itemVenda);
		System.out.println("lista e" + codigo);
		return mv;
	}

	public String remover(Long id) {
		for (ItemVenda it : itemVenda) {
			if (it.getProduto().getId().equals(id)) {
				itemVenda.remove(it);
				break;
			}
		}

		return "redirect:/stl/carrinho";
	}

}
