package com.example.STL.Service;

import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;

@Service
public class htmlPdf {
	

	private String caminho = "C:/Loja/relatorios/";
	

	/*
	 * public String htmlTopdf(String processadoHtml, String caminho1){
	 * ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	 * 
	 * try { PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
	 * DefaultFontProvider defaultFont = new DefaultFontProvider(false, true,
	 * false); ConverterProperties converterProperties = new ConverterProperties();
	 * converterProperties.setFontProvider(defaultFont);
	 * HtmlConverter.convertToPdf(processadoHtml, pdfWriter, converterProperties);
	 * 
	 * FileOutputStream fout = new FileOutputStream(caminho + caminho1);
	 * 
	 * byteArrayOutputStream.writeTo(fout); byteArrayOutputStream.close();
	 * 
	 * byteArrayOutputStream.flush(); fout.close();
	 * 
	 * return null;
	 * 
	 * }catch (Exception ex){ ex.printStackTrace();
	 * System.out.println("erro no htmlPdf" +ex.getMessage());
	 * 
	 * return null; }
	 * 
	 * }
	 */

}
