package com.example.STL.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.web.multipart.MultipartFile;

public class CarregarImagem {

	private static String CAMINHO_IMAGEM = "C:/Loja";

	public static void caminhoIMG(String caminho) {
		try {
			File diretorio = new File(caminho);
			if (!diretorio.exists()) {
				diretorio.mkdirs();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	public static String salvarIMG(String caminho, MultipartFile imagem) {
		caminhoIMG(CAMINHO_IMAGEM + caminho);
		
		//var numAleatorio = Math.floor(Math.random() * 1222000);

		String arquivo = imagem.getOriginalFilename();
		String diretorioNomeArquivo = CAMINHO_IMAGEM+caminho+arquivo;
		File file = new File(diretorioNomeArquivo);

		try {
			imagem.transferTo(file);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Imagem não suportada" +e.getMessage());
		}

		return arquivo;
	}

	public static byte[] mostrarImagem(String caminho) {
		File imagemArquivo = new File(CAMINHO_IMAGEM+caminho);
		if (imagemArquivo.exists()) {
			try {
				return Files.readAllBytes(imagemArquivo.toPath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

}
