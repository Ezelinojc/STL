package com.example.STL.Service;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;

@Service
public class EmailServiceRecupercaoSenha {
	


    @Autowired
    private JavaMailSender javaMailSender;

    //Classe de configuracao do freemarker para personalizacao da pagina de email
    @Autowired
    private Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String remetente;
    public String enviarEmail(String destinatario, String titulo, String mensagem){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(titulo);
            simpleMailMessage.setText(mensagem);
            javaMailSender.send(simpleMailMessage);
            return "Email enviado com sucesso!";
        }catch (Exception e){
            return "Erro ao Enviar o email";
        }

    }

    public void enviarEmailTemplate(String destinatario, String titulo, Map<String, Object> propriedades){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(titulo);
            mimeMessageHelper.setFrom(remetente);
            mimeMessageHelper.setTo(destinatario);

            mimeMessageHelper.setText(getConteudoTemplate(propriedades), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public String getConteudoTemplate(Map<String, Object> model){
        StringBuffer content = new StringBuffer();

        try{
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("Arquivo.flth"), model));

        }catch (Exception e){
            e.printStackTrace();
        }

        return content.toString();

    }

}
