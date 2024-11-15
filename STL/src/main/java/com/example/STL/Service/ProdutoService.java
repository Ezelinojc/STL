package com.example.STL.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.STL.Model.Produto;
import com.example.STL.Model.RegistroAtividade;
import com.example.STL.Repository.ProdutoRepository;
import com.example.STL.Repository.RegistroAtividadeRepository;
import com.example.STL.Util.FuncionarioUtil;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private FuncionarioUtil funcionarioUtil;

	@Autowired
	private RegistroAtividadeRepository registroRepository;
	RegistroAtividade act = new RegistroAtividade();

	public ModelAndView add(Produto produtos) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("produto/cadastro");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("produtos", produtos);
		return mv;
	}

	// SALVAR
	public ModelAndView salvar(Produto produtos, BindingResult br, RedirectAttributes at) {
		ModelAndView mv = new ModelAndView("produto/cadastro");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		if (br.hasErrors()) {
			System.out.println("Erro " + br);
			return add(produtos);
		}

		if (produtos.getId() != null) {
			at.addFlashAttribute("mensagem", "Dados Actualizado com sucesso");
			act.setAcao("Atualizou o Produto " + produtos.getNome());
		} else {
			at.addFlashAttribute("mensagem", "Dados salvo com sucesso");
			act.setAcao("Cadastrou o Produto  " + produtos.getNome());
		}
		act.setDataHora(new Date());
		act.setUsuario(funcionarioUtil.funcionarioLogado());
		registroRepository.save(act);
		produtoRepository.save(produtos);
		mv.setViewName("redirect:/stl/produtos/cadastrar");
		return mv;
	}

	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("produto/lista");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("produtoList", this.produtoRepository.findAll());
		return mv;
	}

	// EDITAR
	public ModelAndView editar(Long id) {
		Produto produtos = this.produtoRepository.getById(id);
		return this.add(produtos);
	}

	public ModelAndView eliminar(Long id, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView();
		Produto Produto = this.produtoRepository.getById(id);
		if (Produto != null) {
			act.setAcao("Exclui a Produto " + Produto.getNome());
			// PODE SER ÚTIL REGISTRAR OS DADOS DO USÚARIO QUE ESTÁ SENDO EXCLUIDO
		}
		act.setDataHora(new Date());
		act.setUsuario(funcionarioUtil.funcionarioLogado());
		registroRepository.save(act);
		produtoRepository.delete(Produto);
		rd.addFlashAttribute("mensagem2", "Dados eliminados com sucesso");
		mv.setViewName("redirect:/stl/produtos/listar");
		return mv;
	}

	public void atualizarEstoque(Long id, int novaQuantidade) {

		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		produto.setQuantidadeStok(novaQuantidade);
		produtoRepository.save(produto);
	}

	

	
	
	public ModelAndView produtoExpirados3() {
	    ModelAndView mv = new ModelAndView("produtoExpirados/lista");
	    mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());

	    List<Produto> produtos = produtoRepository.findAll();
	    List<Produto> produtosExpirados = new ArrayList<>();
	    List<Produto> produtosPrestesAExpirar = new ArrayList<>();

	    LocalDate hoje = LocalDate.now();

	    for (Produto produto : produtos) {
	        LocalDate dataExpiracao = produto.getDataExpiracao();

	        if (dataExpiracao.isBefore(hoje.plusDays(1))) {
	            produtosExpirados.add(produto);
	            
	        } else if (dataExpiracao.isAfter(LocalDate.now())) {
				// Produto prestes a expirar em menos de 30 dias
				long diasParaExpirar = LocalDate.now().until(dataExpiracao).getDays();
				if (diasParaExpirar < 30) {
					produtosPrestesAExpirar.add(produto);
				}
			}
	    }

	    mv.addObject("produtosExpirados", produtosExpirados);
	    mv.addObject("produtosPrestesAExpirar", produtosPrestesAExpirar);
	    return mv;
	}


	

	public boolean validarDatasProduto(Produto produto) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());

		LocalDate dataFabricacao = produto.getDataFabrico();
		LocalDate dataExpiracao = produto.getDataExpiracao();

		// Verifica se as datas são válidas
		if (dataFabricacao == null || dataExpiracao == null) {
			throw new IllegalArgumentException("Datas de fabricação e expiração são obrigatórias.");
		}

		if (dataExpiracao.isBefore(dataFabricacao)) {
			throw new IllegalArgumentException("Data de expiração não pode ser anterior à data de fabricação.");
		}

		return true;
	}
}
