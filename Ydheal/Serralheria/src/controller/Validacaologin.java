package controller;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.jFrames.AWTelainicial;

@SuppressWarnings("serial")
public class Validacaologin extends AWTelainicial {

	boolean v = false;

	public boolean Logar(String Login, String Senha) {

		try {
			// Validação de Login

			// Strin com o comando mysql
			String comando = "select login_usuario, login_senha from tbl_login where login_usuario = '"
					+ Login + "' and login_senha = '" + Senha + "';";

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

				// caso a busca retorne as informações igual a dos txt box, ele
				// executa o login
				if ((Login.equals(login)) && (Senha.equals(senha))) {

					v = true;

					try {
						AWTelainicial window = new AWTelainicial();
						window.frmSerralheriaYdheal.setVisible(true);
						window.frmSerralheriaYdheal
								.setExtendedState(window.frmSerralheriaYdheal
										.getExtendedState()
										| JFrame.MAXIMIZED_BOTH);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					return v;

				}

			}

			st.close();
			con.close();

		}

		catch (Exception erro) {

			JOptionPane.showMessageDialog(null, "Falha na consulta");
		}
		return v;

	}
}
