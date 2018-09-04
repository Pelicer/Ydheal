package controller;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Reenviarsenha {

	public String reenviar(String Login, String Email) {

		try {
			// Reenviar senha de Login

			// Strin com o comando mysql
			String comando = "select login_usuario, login_senha, login_email from tbl_login where login_usuario = '"
					+ Login + "' and login_email = '" + Email + "'; ";

			// cria a conecxão
			Connection con = ConexaoMySQL.getConexaoMySQL();

			// cria um estamento
			java.sql.Statement st = con.createStatement();

			// pega o resultado obtido da execão do comando mysql
			ResultSet resultSet = st.executeQuery(comando);

			while (resultSet.next()) {

				// cria-se uma variavel que recebe o campo retornado da
				// consulta, aquis, enquanto tiver resultado, faz algo.
				String login = resultSet.getString("login_usuario");
				String senha = resultSet.getString("login_senha");
				String emaill = resultSet.getString("login_email");

				// caso a busca retorne as informações igual a dos txt box, ele
				// executa o login
				if ((Login.equals(login)) && (Email.equals(emaill))) {

					EnviarSenha emailcomsenha = new EnviarSenha();

					String msg = "Olá " + login + ", \n" + "Sua senha é: "
							+ senha + ". \n";

					emailcomsenha.enviarEmail(msg, emaill);

					return "Sua senha foi enviada para seu email com Sucesso!";
				}

			}

			st.close();
			con.close();

		}

		catch (Exception erro) {

			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}

		return "Nome de usuario ou email estão incorretos";
	}

}
