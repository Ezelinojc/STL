package com.example.STL.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.STL.Model.Cliente;
import com.example.STL.Model.RegistroAtividade;
import com.example.STL.Repository.ClienteRepository;
import com.example.STL.Repository.RegistroAtividadeRepository;
import com.example.STL.Util.FuncionarioUtil;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private FuncionarioUtil funcionarioUtil;

	@Autowired
	private RegistroAtividadeRepository registroRepository;
	
	RegistroAtividade act = new RegistroAtividade();

	public ModelAndView add(Cliente clientes) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cliente/cadastro");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("clientes", clientes);
		return mv;
	}

	// SALVAR
	public ModelAndView salvar(Cliente clientes, BindingResult br, RedirectAttributes at) {
		ModelAndView mv = new ModelAndView("cliente/cadastro");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		if (br.hasErrors()) {
			return add(clientes);
		}

		if (clientes.getId() != null) {
			at.addFlashAttribute("mensagem", "Dados Actualizado com sucesso");
			act.setAcao("Atualizou o Cliente " + clientes.getNome());
		} else {
			at.addFlashAttribute("mensagem", "Dados salvo com sucesso");
			act.setAcao("Cadastrou o Cliente  " + clientes.getNome());
		}

		act.setUsuario(funcionarioUtil.funcionarioLogado());
		act.setDataHora(new Date());
		registroRepository.save(act);
		clienteRepository.save(clientes);
		mv.setViewName("redirect:/stl/clientes/cadastrar");
		return mv;
	}

	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("cliente/lista");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("EstudosList", this.clienteRepository.findAll());
		return mv;
	}

	// EDITAR
	public ModelAndView editar(Long id) {
		Cliente Estudos = this.clienteRepository.getById(id);
		return this.add(Estudos);
	}

	public ModelAndView eliminar(Long id, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView();
		Cliente cliente = this.clienteRepository.getById(id);
		if (cliente != null) {
			act.setAcao("Exclui a Cliente " + cliente.getNome());
			// PODE SER ÚTIL REGISTRAR OS DADOS DO USÚARIO QUE ESTÁ SENDO EXCLUIDO

		}

		act.setDataHora(new Date());
		act.setUsuario(funcionarioUtil.funcionarioLogado());

		registroRepository.save(act);
		clienteRepository.delete(cliente);
		rd.addFlashAttribute("mensagem2", "Dados eliminados com sucesso");
		mv.setViewName("redirect:/stl/clientes/listar");
		return mv;
	}

}
