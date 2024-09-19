package com.example.STL.Service;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.example.STL.Model.RegistroAtividade;


@Service
public class DataMaper {
	
	/// RECITUARIO
		private String CAMINHO = "C:/Loja/Imagem/";
		
		/// RECITUARIO
		private String caminho = "C:/Loja/Imagem";

		// PARA O LIDER
		public Context setRegistroA(List<RegistroAtividade> registro) {
			Context context = new Context();
			Map<String, Object> data = new HashMap<>();
			data.put("registro", registro);
			File file = new File(caminho);
			if (!file.exists()) {
				file.mkdirs();
			}

			String caminhoBase = file.getParentFile().toURI().toString();
			String caminhoImagem = caminhoBase + "Imagem/venda.jpng";
			try {
				URI uriImagem = new URI(caminhoImagem);
				data.put("foto", uriImagem);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			context.setVariables(data);
			return context;
		}

}
