package com.example.STL.Service;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.STL.Model.Config;
import com.example.STL.Repository.ConfigRepository;
import com.example.STL.Util.FuncionarioUtil;

@Service
public class ConfigServie {

	@Autowired
	private ConfigRepository configRepository;

	private static String CAMINHO = "/Imagem/configuracao/";

	// CRIANDO O DIRETORIO PARA ARMAZENAR AS IMAGENM
	@Autowired
	private FuncionarioUtil funcionarioUtil;

	public ModelAndView config(Config config) {
		ModelAndView mv = new ModelAndView("config/config");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("configuracao", this.configRepository.findAll());
		mv.addObject("config", config);
		return mv;
	}

	public ModelAndView salvarConfig(@Valid Config config, BindingResult br, RedirectAttributes rd, MultipartFile arquivo) {
		ModelAndView mv = new ModelAndView("config/config");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		if (br.hasErrors()) {
			System.out.println(br);
			mv.addObject("mensagem1", "Por favor, corrija os erros no formulário.");
			return mv;
		}

		
		this.configRepository.save(config);
		rd.addFlashAttribute("mensagem", "Alterado alterado com sucesso!");
		return new ModelAndView("redirect:/stl/config");

	}

	public ModelAndView edconfig() {
		Config user = this.configRepository.getById(this.funcionarioUtil.funcionarioLogado().getId());
		return this.config(user);
	}

}
