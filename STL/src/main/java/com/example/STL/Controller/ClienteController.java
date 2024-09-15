package com.example.STL.Controller;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.STL.Model.Cliente;
import com.example.STL.Service.ClienteService;

@Controller
@RequestMapping("/stl/clientes/")
public class ClienteController {
	
	@Autowired private ClienteService clienteService ;
	
	@GetMapping("cadastrar")
	public ModelAndView add(Cliente cliente) {
		return this.clienteService.add(cliente);
	}
	
	@PostMapping("salvar")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult br, RedirectAttributes at) {
		return this.clienteService.salvar(cliente, br, at);
	}
	
	// EDITAR
	@PostMapping("editar")
	public ModelAndView editar(@RequestParam("id") Long id) throws NoSuchFileException {
		return this.clienteService.editar(id);
	}
	
	@GetMapping("listar")
	public ModelAndView lista() {
		return this.clienteService.lista();
	}

	// EXCLUIR
	@GetMapping("excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id, RedirectAttributes rd) {
		return this.clienteService.eliminar(id, rd);
	}

	


}
