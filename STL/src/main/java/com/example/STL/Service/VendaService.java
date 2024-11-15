package com.example.STL.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.STL.Model.Venda;
import com.example.STL.Model.Cliente;
import com.example.STL.Model.ItemVenda;
import com.example.STL.Model.Produto;
import com.example.STL.Model.RegistroAtividade;
import com.example.STL.Repository.VendaRepository;
import com.example.STL.Repository.ClienteRepository;
import com.example.STL.Repository.ProdutoRepository;
import com.example.STL.Util.FuncionarioUtil;

@Service
public class VendaService {
	private static final BigDecimal TAXA_IMPOSTO = new BigDecimal("0.15"); // 15% de imposto
	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private FuncionarioUtil funcionarioUtil;

	RegistroAtividade act = new RegistroAtividade();

	public ModelAndView add(Venda vendas) {
		ModelAndView mv = new ModelAndView("venda/lista");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("clientes", this.clienteRepository.findAll());
		mv.addObject("vendas", vendas);
		return mv;
	}

	public ModelAndView realizarVenda(Venda venda,Optional<Double> desconto, BindingResult br) {
		ModelAndView mv = new ModelAndView("venda/lista");
		mv.addObject("logado", funcionarioUtil.funcionarioLogado());
		if (br.hasErrors()) {
			mv.addObject("mensagem1", "Erro ao tentar salvar a venda");
			return add(venda);
		}
		vendaRepository.save(venda);
		mv.setViewName("redirect:/stl/vendas/cadastrar");
		return mv;
	}

	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("Venda/lista");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("VendaList", this.vendaRepository.findAll());
		return mv;
	}

	public ModelAndView pesquisarCodigo(String codigo, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView("venda/lista");
		Produto prod = this.produtoRepository.findByCdgProduto(codigo);
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("venda", new Venda());

		if (prod == null) {
			mv.addObject("mensagem1", "Produto Não existente!");
			return mv;
		}

		mv.addObject("produtos", prod);
		return mv;
	}

	public BigDecimal calcularTotalVenda(List<ItemVenda> itens) {
		BigDecimal total = BigDecimal.ZERO;

		for (ItemVenda item : itens) {
			BigDecimal totalItem = item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()));
			item.setTotal(totalItem);
			total = total.add(totalItem);
		}

		return total.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Calcula o imposto com base no valor total da venda.
	 */
	public BigDecimal calcularImposto(BigDecimal totalVenda) {
		BigDecimal imposto = totalVenda.multiply(TAXA_IMPOSTO);
		return imposto.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Calcula o troco a ser devolvido ao cliente com base no valor pago.
	 */
	public BigDecimal calcularTroco(BigDecimal valorPago, BigDecimal totalVenda) {
		BigDecimal troco = valorPago.subtract(totalVenda);
		return troco.compareTo(BigDecimal.ZERO) >= 0 ? troco.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
	}

	private void atualizarEstoqueProduto(Produto produto, int quantidadeVendida) {
		int novaQuantidadeEstoque = produto.getQuantidadeStok() - quantidadeVendida;
		if (novaQuantidadeEstoque < 0) {
			throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
		}
		produto.setQuantidadeStok(novaQuantidadeEstoque);
		produtoRepository.save(produto);
	}

	@Transactional
	public Venda processarEGuardarVenda(Venda venda, BigDecimal valorPago) {
	    BigDecimal totalVenda = calcularTotalVenda(venda.getItens());
	    BigDecimal imposto = calcularImposto(totalVenda);
	    BigDecimal totalComImposto = totalVenda.add(imposto);

	    if (valorPago.compareTo(totalComImposto) < 0) {
	        throw new RuntimeException("Valor pago insuficiente para cobrir o total da venda.");
	    }

	    venda.setDataVenda(LocalDate.now());
	    venda.setValorTotal(totalComImposto);
	    venda.setImposto(imposto);
	    venda.setTroco(calcularTroco(valorPago, totalComImposto));

	    for (ItemVenda item : venda.getItens()) {
	        atualizarEstoqueProduto(item.getProduto(), item.getQuantidade());
	    }

	    Cliente cliente = venda.getCliente();
	    if (cliente != null && cliente.getId() == null) {
	        cliente = clienteRepository.save(cliente);
	        venda.setCliente(cliente);
	    }

	    return vendaRepository.save(venda);
	}
}
