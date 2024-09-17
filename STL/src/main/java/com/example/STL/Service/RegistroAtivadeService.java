package com.example.STL.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.STL.Model.RegistroAtividade;
import com.example.STL.Repository.RegistroAtividadeRepository;
import com.example.STL.Util.FuncionarioUtil;



@Service
public class RegistroAtivadeService {
	
	@Autowired private RegistroAtividadeRepository atividadeRepository;
	
	@Autowired FuncionarioUtil funcionarioUtil;
	
	public ModelAndView add(RegistroAtividade atividade) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("funcionario/registroAtividada");
		mv.addObject("listaAtidade",this.atividadeRepository.findAll());
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		return mv;
		
	}
	
	public ModelAndView salvarReg(RegistroAtividade atividade) {
		ModelAndView mv = new ModelAndView("funcionario/registroAtividada");
		this.atividadeRepository.save(atividade);
		return mv;
	}

}
