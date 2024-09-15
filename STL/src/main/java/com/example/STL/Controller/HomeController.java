package com.example.STL.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.STL.Model.Funcionario;
import com.example.STL.Repository.FuncionarioRepository;
import com.example.STL.Service.FuncionarioService;
import com.example.STL.Util.FuncionarioUtil;;

@Controller
public class HomeController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	FuncionarioUtil funcionarioUtil;

	@GetMapping("/")
	public ModelAndView login(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		this.CriarUsuarioLogado();
		return mv;
	}

	@GetMapping("/stl")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.setViewName("/index");

		return mv;
	}

	public void CriarUsuarioLogado() {
		List<Funcionario> existingUser = funcionarioRepository.findAll();
		if (existingUser.isEmpty()) {
			Funcionario user = new Funcionario();
			user.setLogin("Admin");
			user.setSenha(new BCryptPasswordEncoder().encode("1234"));
			user.setNome("Administrador");
			user.setTelefone("999-999-999");
			user.setRole("ADMIN");
			user.setEmail("ezelino@gmail.com");
			funcionarioRepository.save(user);

		}

	}

//	##############################################################################################//

	@GetMapping("/solicitacao-codigo")
	public String solicitarCodigo() {
		return "recuperarSenha/recuperar";
	}

	@PostMapping("/solicitacao-codigo")
	public ModelAndView slocititarCodigo(Funcionario funcionario, RedirectAttributes rd) {
		return funcionarioService.solicitarCodigo(funcionario.getEmail(), rd);
	}

	@GetMapping("/recuperacao-senha")
	public String recuperarSenha() {
		return "recuperarSenha/NovaSenha";
	}

	@PostMapping("/recuperacao-senha")
	public ModelAndView RecuperarSenha(Funcionario funcionario, RedirectAttributes rd) {
		return funcionarioService.recuperarSenha(funcionario, rd);
	}

//	##############################################################################################//

}
