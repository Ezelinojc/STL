package com.example.STL.Service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.STL.Model.Venda;
import com.example.STL.Model.Produto;
import com.example.STL.Model.RegistroAtividade;
import com.example.STL.Repository.VendaRepository;
import com.example.STL.Repository.ClienteRepository;
import com.example.STL.Repository.ProdutoRepository;
import com.example.STL.Repository.RegistroAtividadeRepository;

import com.example.STL.Util.FuncionarioUtil;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private FuncionarioUtil funcionarioUtil;

	@Autowired
	private RegistroAtividadeRepository registroRepository;
	RegistroAtividade act = new RegistroAtividade();

	public ModelAndView add(Venda vendas) {
		ModelAndView mv = new ModelAndView("venda/lista");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("vendas", vendas);
		return mv;
	}

	public ModelAndView realizarVenda(Venda venda, double percentualDesconto, BindingResult br) {
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
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("venda/verVendas");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("Ver", this.vendaRepository.findAll());
		return mv;
	}


	
	public ModelAndView pesquisarCodigo(String codigo, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView("venda/lista");
		List<Produto> prod = this.produtoRepository.findByCdgProduto(codigo);
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("venda", new Venda());

		if (prod.isEmpty()) {
			mv.addObject("mensagem1", "Produto Não existente!");
			return mv;
		}

		mv.addObject("produtos", prod);
		return mv;
	}

}
