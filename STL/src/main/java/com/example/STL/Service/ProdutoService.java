package com.example.STL.Service;

import java.util.Date;

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
			return add(produtos);
		}

		

		if (produtos.getId() != null) {
			at.addFlashAttribute("mensagem", "Dados Actualizado com sucesso");
			act.setAcao("Atualizou o Produto " + produtos.getNome());
		} else {
			at.addFlashAttribute("mensagem", "Dados salvo com sucesso");
			act.setAcao("Cadastrou o Produto  " + produtos.getNome());
		}
		act.setUsuario(this.funcionarioUtil.funcionarioLogado().getNome());
		act.setDataHora(new Date());
		registroRepository.save(act);
		produtoRepository.save(produtos);
		mv.setViewName("redirect:/stl/produtos/cadastrar");
		return mv;
	}
	
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("produto/lista");
		mv.addObject("logado",this.funcionarioUtil.funcionarioLogado());
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
			act.setUsuario(this.funcionarioUtil.funcionarioLogado().getNome());
			act.setDataHora(new Date());
			registroRepository.save(act);
		}

		produtoRepository.delete(Produto);
		rd.addFlashAttribute("mensagem2", "Dados eliminados com sucesso");
		mv.setViewName("redirect:/stl/produtos/listar");
		return mv;
	}

	
	public void atualizarEstoque(Long id, Double novaQuantidade) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setQuantidadeStok(novaQuantidade);
        produtoRepository.save(produto);
    }
}
