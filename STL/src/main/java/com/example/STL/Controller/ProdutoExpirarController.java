package com.example.STL.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.STL.Service.ProdutoService;

@Controller
public class ProdutoExpirarController {
	
	@Autowired
    private ProdutoService productService;
	
	
	@GetMapping("/stl/produtoExpirados")
	public ModelAndView produtoExpirados() {
	    return this.productService.produtoExpirados3();
	}
	
	 
}
