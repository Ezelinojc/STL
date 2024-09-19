package com.example.STL.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.example.STL.Model.RegistroAtividade;
import com.example.STL.Repository.RegistroAtividadeRepository;
import com.example.STL.Util.Formatar;
import com.example.STL.Util.FuncionarioUtil;
import com.example.STL.Util.RelatproPdfHmtl;

@Service
public class RegistroAtivadeService {

	@Autowired
	private RegistroAtividadeRepository atividadeRepository;

	@Autowired
	private DataMaper dataMapper;
	@Autowired
	private SpringTemplateEngine springTemplateEngine;

	@Autowired
	private htmlPdf htmLpdf;
	@Autowired
	FuncionarioUtil funcionarioUtil;

	public ModelAndView add(RegistroAtividade atividade) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("funcionario/registroAtividada");
		mv.addObject("listaAtidade", this.atividadeRepository.findAll());
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		return mv;

	}

	public ModelAndView salvarReg(RegistroAtividade atividade) {
		ModelAndView mv = new ModelAndView("funcionario/registroAtividada");
		this.atividadeRepository.save(atividade);
		return mv;
	}

	public ModelAndView eliminar(Long id, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView();
		RegistroAtividade act = atividadeRepository.getById(id);
		atividadeRepository.delete(act);
		rd.addFlashAttribute("mensagem2", "Dados eliminados com sucesso");
		mv.setViewName("redirect:/stl/funcionario/atividade");
		return mv;
	}

	public ResponseEntity<?> relatorio(String dataInicial, String dataFinal) {
		LocalDate hoje = LocalDate.now();

		// Verifica se a dataInicial é maior que a data atual
		if (LocalDate.parse(dataInicial).isAfter(hoje)) {
			return ResponseEntity.badRequest().body("A data inicial não pode ser maior que a data atual.");
		}

		// Verifica se a dataFinal é maior que a data atual
		if (LocalDate.parse(dataFinal).isAfter(hoje)) {
			return ResponseEntity.badRequest().body("A data final não pode ser maior que a data atual.");
		}

		// Verifica se a dataFinal é menor que a dataInicial
		if (LocalDate.parse(dataFinal).isBefore(LocalDate.parse(dataInicial))) {
			return ResponseEntity.badRequest().body("A data final não pode ser menor que a data inicial.");
		}

		// Processa o relatório caso as validações sejam atendidas
		String finalHtml = null;
		
		Context dataContext = dataMapper.setRegistroA(
				this.atividadeRepository.findByDataBetween(Formatar.data(dataInicial), Formatar.data(dataFinal)));

		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
		String Datainiciale = dateFormat.format(new Date());

		finalHtml = springTemplateEngine.process("/relatorios/registroAtividade", dataContext);
		String fileName = "RelatóriosAtividade" + Datainiciale + ".pdf";

		htmLpdf.htmlTopdf(finalHtml, fileName);
		return RelatproPdfHmtl.verPdf(fileName);
	}

}
