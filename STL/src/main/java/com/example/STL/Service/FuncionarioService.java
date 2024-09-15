package com.example.STL.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.STL.Model.Funcionario;
import com.example.STL.Model.RegistroAtividade;
import com.example.STL.Repository.FuncionarioRepository;
import com.example.STL.Repository.RegistroAtividadeRepository;
import com.example.STL.Util.CarregarImagem;
import com.example.STL.Util.FuncionarioUtil;

@Service
public class FuncionarioService {

	// CRIANDO O DIRETORIO PARA ARMAZENAR AS IMAGENM
	private static String CAMINHO = "/Imagem/funcionario/";

	@Autowired
	private FuncionarioUtil funcionarioUtil;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private RegistroAtividadeRepository registroRepository;

	@Autowired
	private EmailServiceRecupercaoSenha emailServiceRecupercaoSenha;

	public ModelAndView add(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("funcionario/cadastro");
		mv.addObject("funcionarioList", funcionarioRepository.findAll());
		mv.addObject("logado", funcionarioUtil.funcionarioLogado());
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	// SALVAR
	public ModelAndView salvar(Funcionario funcionario, BindingResult br, RedirectAttributes at, MultipartFile imagem) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());

		if (br.hasErrors()) {
			at.addFlashAttribute("mensagem1", "Erro ao Cadastrar Funcionario!");
			return add(funcionario);
		}

		Funcionario user = this.funcionarioRepository.findByNumero(funcionario.getTelefone());
		if (user != null) {
			mv.addObject("mensagem1", "Já existe número um igual salvo");
			mv.setViewName("funcionario/Cadastro");
			return mv;

		}

		Funcionario userr = funcionarioRepository.findByEmail(funcionario.getEmail());
		if (userr != null) {
			mv.addObject("mensagem1", "Email Já Cadastrado");
			mv.setViewName("redirect:/funcionario/Cadastro");
			return mv;
		}

		Funcionario uu = this.funcionarioRepository.findByLogin(funcionario.getLogin());
		if (uu != null) {
			mv.addObject("mensagem1", "Já existe login um igual salvo");
			mv.addObject("funcionario", funcionario);
			return mv;

		}

		if (imagem != null && !imagem.isEmpty()) {
			funcionario.setCaminhoImg(String.valueOf(CarregarImagem.salvarIMG(CAMINHO, imagem)));
		}
		// PARA REGISTRAR QUEM CADASTROU OU ATUALIZOU UMA CONTA
		RegistroAtividade act = new RegistroAtividade();
		if (funcionario.getId() != null) {
			act.setAcao("Atualizou o funcionario " + funcionario.getNome());
		} else {
			act.setAcao("Cadastrou o funcionario  " + funcionario.getNome());
		}
		act.setUsuario(this.funcionarioUtil.funcionarioLogado().getNome());
		act.setDataHora(new Date());
		registroRepository.save(act);

		funcionario.setSenha(new BCryptPasswordEncoder().encode(funcionario.getSenha()));

		if (funcionario.getId() != null) {
			at.addFlashAttribute("mensagem", "Dados Actualizado com sucesso");
		} else {
			at.addFlashAttribute("mensagem", "Dados salvo com sucesso");
		}

		funcionarioRepository.save(funcionario);
		emailServiceRecupercaoSenha.enviarEmail(funcionario.getEmail(), "IEIA",
				"Foste cadastrado na IGREJA IEIA COM SUCESSO");
		at.addFlashAttribute("mensagem", "funcionario Cadastrado Com Sucesso!");
		mv.setViewName("redirect:/stl/funcionario/cadastrar");
		
		return mv;
	}

	public ModelAndView actualizar(Long id) {
		Funcionario funcionario = this.funcionarioRepository.getById(id);
		return this.add(funcionario);
	}

	// MOSTRAR IMAGEM
	public byte[] mostrarImagem(String imagem) throws IOException {
		return CarregarImagem.mostrarImagem(CAMINHO + imagem);
	}

	// EDITAR

	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("funcionario/lista");
		mv.addObject("funcionarios", this.funcionarioRepository.findByOcultarAdmin());
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		return mv;
	}

	// REGISTRO DE ACTIVIDADE
	public ModelAndView registroAtividade() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("funcionario/registroAtividade");
		mv.addObject("auditorias", this.funcionarioRepository.findAll());
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		return mv;
	}

	public ModelAndView eliminar(Long id, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView();
		Funcionario  funcionario = this.funcionarioRepository.getById(id);

		if (funcionario != null) {
			// REGISTRAR A AÇÃO DE EXCLUSÃO
			RegistroAtividade act = new RegistroAtividade();
			act.setAcao("Exclui a Usuário " + funcionario.getNome());
			// PODE SER ÚTIL REGISTRAR OS DADOS DO USÚARIO QUE ESTÁ SENDO EXCLUIDO
			act.setUsuario(this.funcionarioUtil.funcionarioLogado().getNome());
			act.setDataHora(new Date());
			registroRepository.save(act);
		}
		funcionarioRepository.delete(funcionario);
		rd.addFlashAttribute("mensagem2", "Dados eliminados com sucesso");
		mv.setViewName("redirect:/stl/funcionario/listar");
		return mv;
	}

	private String getCodigoRecuperarSenha() {
		Random random = new Random();
		return String.valueOf(random.nextInt(900000) + 1000000);
	}

	public ModelAndView solicitarCodigo(String email, RedirectAttributes rd) {

		try {
			Funcionario funcionario = funcionarioRepository.findByEmail(email);

			if (funcionario != null) {
				funcionario.setCodigoRecuperarSenha(getCodigoRecuperarSenha());
				funcionario.setDataEnvioCdg(new Date());

				funcionarioRepository.saveAndFlush(funcionario);

				// Envie o código de recuperação por e-mail
				Map<String, Object> propMap = new HashMap<>();
				propMap.put("nome", funcionario.getNome());
				propMap.put("codigo", funcionario.getCodigoRecuperarSenha());
				emailServiceRecupercaoSenha.enviarEmailTemplate(funcionario.getEmail(),
						"Recuperação de senha IEIA-BAIRRO NZAJI", propMap);

				rd.addFlashAttribute("sucesso",
						"Foi enviado um código de recuperação para o e-mail: " + funcionario.getEmail());
				return new ModelAndView("redirect:/recuperacao-senha");
			} else {
				rd.addFlashAttribute("erro", "Não foi possível enviar o código de recuperação para o e-mail " + email
						+ ". E-mail não encontrado.");
				return new ModelAndView("redirect:/solicitacao-codigo");
			}
		} catch (MailException e) {
			rd.addFlashAttribute("erro",
					"Erro ao enviar código se solicitação por  e-mail: você deve estar conectado a uma internet, ou verifica se a um firewall bloquenado as porta ");
			return new ModelAndView("redirect:/solicitacao-codigo");
		}
	}

	public ModelAndView recuperarSenha(Funcionario funcionario, RedirectAttributes rd) {
		ModelAndView mv = new ModelAndView();
		try {
			Funcionario funcionarioBanco = funcionarioRepository
					.findByEmailAndCodigoRecuperarSenha(funcionario.getEmail(), funcionario.getCodigoRecuperarSenha());

			if (funcionarioBanco != null) {
				Date diferenca = new Date(new Date().getTime() - funcionarioBanco.getDataEnvioCdg().getTime());

				if (diferenca.getTime() / 1000 < 200) {
					String novaSenhaCodificada = new BCryptPasswordEncoder().encode(funcionario.getSenha());

					funcionarioBanco.setSenha(novaSenhaCodificada);
					funcionarioBanco.setCodigoRecuperarSenha(funcionario.getCodigoRecuperarSenha());

					funcionarioRepository.saveAndFlush(funcionarioBanco);
					rd.addFlashAttribute("sucesso", "Senha recuperada com sucesso!");
					mv.setViewName("redirect:/");
					System.out.println("Senha recuperada com sucesso");
					return mv;

				} else {

					rd.addFlashAttribute("erro", "Código expirado, solicite um novo código de recuperação");
					mv.setViewName("redirect:/recuperacao-senha");
					return mv;
				}
			} else {
				rd.addFlashAttribute("erro", "Email ou código não encontrado!");
				mv.setViewName("redirect:/recuperacao-senha");
				return mv;
			}
		} catch (Exception e) {
			rd.addFlashAttribute("erro", "Erro ao recuperar a senha. Por favor, tente novamente mais tarde.");
			mv.setViewName("redirect:/recuperacao-senha");
			return mv;
		}
	}

	public ModelAndView perfil(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("funcionario/perfil");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	public ModelAndView edperfil() {
		Funcionario user = this.funcionarioRepository.getById(this.funcionarioUtil.funcionarioLogado().getId());
		return this.perfil(user);
	}

	public ModelAndView trocarSenhaPerfil(String senha, String SenhaAntiga, Long id, RedirectAttributes rd) {

		ModelAndView mv = new ModelAndView();

		Funcionario funcionario = this.funcionarioRepository.getById(id);
		mv.setViewName("funcionario/perfil");
		mv.addObject("funcionario", funcionario);
		mv.addObject("logado", funcionarioUtil.funcionarioLogado());


		// Verifica se a senha antiga fornecida corresponde à senha atual do usuário
	    if (!new BCryptPasswordEncoder().matches(SenhaAntiga, funcionario.getSenha())) {
	        mv.addObject("mensagem1", "Senha antiga incorreta");
	        return mv;
	    }

		if (senha.length() < 4) {
			mv.addObject("mensagem1", "A senha tem que ter no minímo 4 digítos");
			return mv;
		}

		funcionario.setSenha(new BCryptPasswordEncoder().encode(senha));
		this.funcionarioRepository.save(funcionario);
		rd.addFlashAttribute("mensagem", "Senha alterada com sucesso");
		mv.setViewName("redirect:/stl/funcionario/perfil");
		return mv;

	}

	public ModelAndView perfil(@Valid Funcionario funcionario, BindingResult br, RedirectAttributes rd,
			MultipartFile arquivo) {
		ModelAndView mv = new ModelAndView("funcionario/perfil");
		mv.addObject("logado", this.funcionarioUtil.funcionarioLogado());

		// Verifica se há erros de validação
		if (br.hasErrors()) {
			System.out.println(br);
			mv.addObject("mensagem1", "Por favor, corrija os erros no formulário.");
			return mv;
		}

		if (!arquivo.isEmpty()) {
			if (arquivo.getSize() > 2 * Math.pow(1024, 2)) {
				mv.addObject("mensagem1", "O tamanho da imagem do usuário não pode exceder a 2MB");
				return mv;
			}
			if (arquivo != null && !arquivo.isEmpty()) {
				funcionario.setCaminhoImg(String.valueOf(CarregarImagem.salvarIMG(CAMINHO, arquivo)));
			}
		}

		Funcionario user = this.funcionarioRepository.findByNumero(funcionario.getTelefone());
		if (user != null) {
			if (user.getId() != funcionario.getId()) {
				mv.addObject("mensagem1", "Já existe número um igual salvo");
				mv.setViewName("funcionario/perfil");
				return mv;

			}

		}

		Funcionario email = funcionarioRepository.findByEmail(funcionario.getEmail());
		if (email != null) {
			if (email.getId() != funcionario.getId()) {
				mv.addObject("mensagem1", "Email Já Cadastrado");
				mv.setViewName("funcionario/perfil");
				return mv;
			}

		}

		Funcionario login = this.funcionarioRepository.findByLogin(funcionario.getLogin());
		if (login != null) {
			if (login.getId() != funcionario.getId()) {
				mv.addObject("mensagem1", "Já existe login um igual salvo");
				mv.addObject("funcionario", funcionario);
				return mv;
			}

		}

		if (arquivo != null && !arquivo.isEmpty()) {

		}
		this.funcionarioRepository.save(funcionario);
		rd.addFlashAttribute("mensagem", "Perfil do Usuário alterado com sucesso!");
		return new ModelAndView("redirect:/stl/funcionario/perfil");

	}

	public ModelAndView alterarEstadofuncionario(Long id, String acao) {

		ModelAndView mv = new ModelAndView();
		mv.addObject("logado", funcionarioUtil.funcionarioLogado());
		Funcionario funcionario = funcionarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("ID do funcionario inválido: " + id));

		if ("Ativo".equals(acao)) {
			funcionario.setEstado(true);
		} else if ("Desativo".equals(acao)) {
			funcionario.setEstado(false);
		} else {
			throw new IllegalArgumentException("Ação inválida: " + acao);
		}
		funcionarioRepository.save(funcionario);
		mv.setViewName("redirect:/stl/funcionario/listar");
		return mv;
	}

}
