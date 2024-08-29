package com.itsoeh.jbrigido.projectevaluator.ui.helpers;

import android.util.Log;

import com.itsoeh.jbrigido.projectevaluator.config.Mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail {

    public static void sendEmail(String toEmail, String subject, String cuerpo) {

        Properties propiedades = new Properties();

        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");

        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Mail.CORREO_MAIL, Mail.PASSWORD_EMAL);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new MimeMessage(sesion);
                    message.setFrom(new InternetAddress(Mail.CORREO_MAIL));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                    message.setSubject(subject);
                    message.setText(cuerpo);
                    Transport.send(message);
                } catch (Exception e) {
                    Log.e("Error ", "Error al enviar correo"+e.getMessage());
                }
            }
        }).start();
    }

}


