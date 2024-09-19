package com.example.STL.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RelatproPdfHmtl {
	
	private static String caminho = "C:/Loja/relatorios/";
    public static ResponseEntity<?> verPdf(String pathCaminho){
        File arquivo = new File(caminho + pathCaminho);
        Path path = Paths.get(arquivo.getAbsolutePath());
        ByteArrayResource resource;

        try{
            resource =new ByteArrayResource(Files.readAllBytes(path));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).contentLength(arquivo.length()).body(resource);
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }


}
