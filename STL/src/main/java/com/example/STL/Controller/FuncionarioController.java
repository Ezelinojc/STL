package com.example.STL.Controller;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.STL.Model.Funcionario;
import com.example.STL.Model.RegistroAtividade;
import com.example.STL.Service.FuncionarioService;
import com.example.STL.Service.RegistroAtivadeService;



@Controller
@RequestMapping("/stl/funcionario/")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioService funcionarioSercvice;

	@Autowired
	private RegistroAtivadeService registroAtividade;

	// SALVAR

	@GetMapping("cadastrar")
	public ModelAndView candidato(Funcionario funcionario) {
		return this.funcionarioSercvice.add(funcionario);
	}

	@PostMapping("salvar")
	public ModelAndView salvar(@Valid Funcionario funcionario, BindingResult br, RedirectAttributes at,
			@RequestParam("file") MultipartFile imagem) {
		return this.funcionarioSercvice.salvar(funcionario, br, at, imagem);
	}

	// LISTAR
	@GetMapping("listar")
	public ModelAndView Listagem() {
		return this.funcionarioSercvice.listar();

	}

	@GetMapping("ver-registro")
	public ModelAndView registroAuditoria() {
		return this.funcionarioSercvice.registroAtividade();

	}

	@GetMapping("foto/{imagem}")
	@ResponseBody
	public byte[] mostrarImagem(@PathVariable("imagem") String nomeArquivo) throws IOException {
		return this.funcionarioSercvice.mostrarImagem(nomeArquivo);
	}

	// EDITAR
	@PostMapping("editar")
	public ModelAndView editar(@RequestParam("id") Long id) throws NoSuchFileException {
		return this.funcionarioSercvice.actualizar(id);
	}

	// EXCLUIR
	@GetMapping("excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes rd) {
		return this.funcionarioSercvice.eliminar(id, rd);
	}

	@GetMapping("atividade")
	public ModelAndView addAtivade(RegistroAtividade atividade) {
		return this.registroAtividade.add(atividade);
	}

	@PostMapping("atividade")
	public ModelAndView salvarAtivade(RegistroAtividade atividade) {
		return this.registroAtividade.salvarReg(atividade);
	}

	@PostMapping("alterarEstado")
	public ModelAndView alterarEstadofuncionario(@RequestParam("id") Long id, @RequestParam("acao") String acao) {
		return funcionarioSercvice.alterarEstadofuncionario(id, acao);
	}
	
	@GetMapping("perfil")
	public ModelAndView perfill() {
		return this.funcionarioSercvice.edperfil();
	}

	@PostMapping("perfil")
	public ModelAndView perfil1(Funcionario funcionario, BindingResult br, RedirectAttributes rd,
			@RequestParam("file") MultipartFile arquivo) {
		return this. funcionarioSercvice.perfil(funcionario, br, rd, arquivo);
	}
	
	@PostMapping("trocarSenha")
	public ModelAndView trocarSenha( String senha, @RequestParam("senhaAntiga")String SenhaAtinga, Long id, RedirectAttributes rd) {
		return this.funcionarioSercvice.trocarSenhaPerfil(senha, SenhaAtinga, id, rd);
	}

}
