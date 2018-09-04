package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EnviarSenha {

	Email email;

	public EnviarSenha() {
		email = new SimpleEmail();
		configuracao();
	}

	private void configuracao() {
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthentication("EmailTesteJava123@gmail.com", "emailteste123");
		email.setSSLOnConnect(true);
	}

	public void enviarEmail(String msg, String para) {

		try {
			email.setFrom("EmailTesteJava123@gmail.com");
			email.setSubject("Recuperação de senha");
			email.setMsg(msg
					+ "\n E-mail gerado automaticamnete, favor não responder esse E-Mail!");
			email.addTo(para);
			email.send();
		} catch (EmailException ex) {
			Logger.getLogger(EnviarSenha.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}