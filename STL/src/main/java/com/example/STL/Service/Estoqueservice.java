package com.example.STL.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.STL.Model.Estoque;
import com.example.STL.Model.RegistroAtividade;
import com.example.STL.Repository.EstoquerRepository;
import com.example.STL.Repository.RegistroAtividadeRepository;
import com.example.STL.Util.FuncionarioUtil;

@Service
public class Estoqueservice {

	@Autowired
	private EstoquerRepository estoqueRepository;
	@Autowired
	private FuncionarioUtil funcionarioUtil;

	@Autowired
	private RegistroAtividadeRepository registroRepository;
	RegistroAtividade act = new RegistroAtividade();

	public ModelAndView add(Estoque estoques) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("estoque/cadastro");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("estoques", estoques);
		return mv;
	}

	// SALVAR
	public ModelAndView salvar(Estoque estoques, BindingResult br, RedirectAttributes at) {
		ModelAndView mv = new ModelAndView("estoque/cadastro");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		if (br.hasErrors()) {
			return add(estoques);
		}

		

		if (estoques.getId() != null) {
			at.addFlashAttribute("mensagem", "Dados Actualizado com sucesso");
			act.setAcao("Atualizou o Estoque " + estoques.getProduto());
		} else {
			at.addFlashAttribute("mensagem", "Dados salvo com sucesso");
			act.setAcao("Cadastrou o Estoque  " + estoques.getProduto());
		}
		act.setUsuario(this.funcionarioUtil.funcionarioLogado().getNome());
		act.setDataHora(new Date());
		registroRepository.save(act);
		estoqueRepository.save(estoques);
		mv.setViewName("redirect:/stl/estoques/cadastrar");
		return mv;
	}
	
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("estoque/lista");
		mv.addObject("logado",this.funcionarioUtil.funcionarioLogado());
		mv.addObject("EstoqueList", this.estoqueRepository.findAll());
		return mv;
	}

	// EDITAR
	public ModelAndView editar(Long id) {
		Estoque Estudos = this.estoqueRepository.getById(id);
		return this.add(Estudos);
	}

	public ModelAndView eliminar(Long id, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView();
		Estoque estoque = this.estoqueRepository.getById(id);
		if (estoque != null) {
			act.setAcao("Exclui a Estoque " + estoque.getProduto());
			// PODE SER ÚTIL REGISTRAR OS DADOS DO USÚARIO QUE ESTÁ SENDO EXCLUIDO
			act.setUsuario(this.funcionarioUtil.funcionarioLogado().getNome());
			act.setDataHora(new Date());
			registroRepository.save(act);
		}

		estoqueRepository.delete(estoque);
		rd.addFlashAttribute("mensagem2", "Dados eliminados com sucesso");
		mv.setViewName("redirect:/stl/estoques/listar");
		return mv;
	}

}
