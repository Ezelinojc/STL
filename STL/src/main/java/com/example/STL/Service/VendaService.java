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
		mv.addObject("ProdutoVenda", this.produtoRepository.findAll());
		mv.addObject("ClienteVenda", this.clienteRepository.findAll());
		mv.addObject("vendas", vendas);
		return mv;
	}

	// Método para calcular o valor total sem desconto
	public double calcularValorTotal(Venda venda) {
		Produto produto = venda.getProduto();
		if (produto != null) {
			return produto.getPreco() * venda.getQtd();
		}
		return 0;
	}

	// Método para aplicar o desconto
	public double aplicarDesconto(double valorTotal, double percentualDesconto) {
		if (percentualDesconto >= 0 && percentualDesconto <= 100) {
			return valorTotal - (percentualDesconto / 100) * valorTotal;
		}
		return valorTotal;
	}
	
	// Método para verificar se a quantidade vendida é maior que o estoque
    public void validarQuantidadeEstoque(Venda venda) throws Exception {
        Produto produto = venda.getProduto();
        if (produto != null && venda.getQtd() > produto.getQuantidadeStok()) {
            throw new Exception("Quantidade vendida não pode ser maior que a quantidade em estoque!");
        }
    }
    
    
    public ModelAndView realizarVenda(Venda venda, double percentualDesconto ,BindingResult br) throws Exception {
    	ModelAndView mv = new ModelAndView("venda/lista");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		if (br.hasErrors()) {
			mv.addObject("mensagem1 ", "Erro ao tenta Salvar");
			return add(venda);
		}

        validarQuantidadeEstoque(venda); 

        double valorTotal = calcularValorTotal(venda); 
        double valorComDesconto = aplicarDesconto(valorTotal, percentualDesconto); 

        // Atualiza a quantidade em estoque
        act.setDataHora(new Date());
		act.setUsuario(funcionarioUtil.funcionarioLogado());
		registroRepository.save(act);
		
		
        Produto produto = venda.getProduto();
        produto.setQuantidadeStok(produto.getQuantidadeStok() - venda.getQtd());
        produtoRepository.save(produto);

        mv.addObject("venda", venda);
        mv.addObject("valorTotal", valorTotal);
        mv.addObject("valorComDesconto", valorComDesconto);
        mv.addObject("percentualDesconto", percentualDesconto);
        mv.setViewName("redirect:/stl/vendas/cadastrar");
        return mv;
    }

	// SALVAR

	/*
	 * public ModelAndView salvar(Venda vendas, BindingResult br, RedirectAttributes
	 * at) { ModelAndView mv = new ModelAndView("venda/lista");
	 * mv.addObject("logado", this.funcionarioUtil.funcionarioLogado()); if
	 * (br.hasErrors()) { return add(vendas); }
	 * 
	 * if (vendas.getId() != null) { at.addFlashAttribute("mensagem",
	 * "Dados Actualizado com sucesso"); act.setAcao("Atualizou o Venda " +
	 * vendas.getCliente()); } else { at.addFlashAttribute("mensagem",
	 * "Dados salvo com sucesso"); act.setAcao("Cadastrou o Venda  " +
	 * vendas.getCliente()); }
	 * act.setUsuario(this.funcionarioUtil.funcionarioLogado().getNome());
	 * act.setDataHora(new Date()); registroRepository.save(act);
	 * vendaRepository.save(vendas);
	 * mv.setViewName("redirect:/stl/vendas/cadastrar"); return mv; }
	 */
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("Venda/lista");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("VendaList", this.vendaRepository.findAll());
		return mv;
	}

	// EDITAR
	public ModelAndView editar(Long id) {
		Venda vendas = this.vendaRepository.getById(id);
		return this.add(vendas);
	}

	public ModelAndView eliminar(Long id, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView();
		Venda venda = this.vendaRepository.getById(id);
		if (venda != null) {
			act.setAcao("Exclui a Venda " + venda.getCliente());
			// PODE SER ÚTIL REGISTRAR OS DADOS DO USÚARIO QUE ESTÁ SENDO EXCLUIDO
	
		}
		act.setDataHora(new Date());
		act.setUsuario(funcionarioUtil.funcionarioLogado());
		registroRepository.save(act);

		vendaRepository.delete(venda);
		rd.addFlashAttribute("mensagem2", "Dados eliminados com sucesso");
		mv.setViewName("redirect:/stl/vendas/listar");
		return mv;
	}

	public ModelAndView pesquisarCodigo(String codigo, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView("venda/lista");
		List<Produto> prod = this.produtoRepository.findByCdgProduto(codigo);
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("venda", new Venda());

		if (prod == null) {
			mv.addObject("mensagem1", "Produto Não existente!");
			return mv;
		}

		mv.addObject("produtos", prod);

		return mv;
	}

}
