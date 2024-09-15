package com.example.STL.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.STL.Model.Venda;
import com.example.STL.Model.RegistroAtividade;
import com.example.STL.Repository.VendaRepository;
import com.example.STL.Repository.RegistroAtividadeRepository;

import com.example.STL.Util.FuncionarioUtil;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	@Autowired
	private FuncionarioUtil funcionarioUtil;

	@Autowired
	private RegistroAtividadeRepository registroRepository;
	RegistroAtividade act = new RegistroAtividade();

	public ModelAndView add(Venda vendas) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("venda/cadastro");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("vendas", vendas);
		return mv;
	}

	// SALVAR
	public ModelAndView salvar(Venda vendas, BindingResult br, RedirectAttributes at) {
		ModelAndView mv = new ModelAndView("venda/cadastro");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		if (br.hasErrors()) {
			return add(vendas);
		}

		

		if (vendas.getId() != null) {
			at.addFlashAttribute("mensagem", "Dados Actualizado com sucesso");
			act.setAcao("Atualizou o Venda " + vendas.getCliente());
		} else {
			at.addFlashAttribute("mensagem", "Dados salvo com sucesso");
			act.setAcao("Cadastrou o Venda  " + vendas.getCliente());
		}
		act.setUsuario(this.funcionarioUtil.funcionarioLogado().getNome());
		act.setDataHora(new Date());
		registroRepository.save(act);
		vendaRepository.save(vendas);
		mv.setViewName("redirect:/stl/vendas/cadastrar");
		return mv;
	}
	
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("Venda/lista");
		mv.addObject("logado",this.funcionarioUtil.funcionarioLogado());
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
			act.setUsuario(this.funcionarioUtil.funcionarioLogado().getNome());
			act.setDataHora(new Date());
			registroRepository.save(act);
		}

		vendaRepository.delete(venda);
		rd.addFlashAttribute("mensagem2", "Dados eliminados com sucesso");
		mv.setViewName("redirect:/stl/vendas/listar");
		return mv;
	}

}
