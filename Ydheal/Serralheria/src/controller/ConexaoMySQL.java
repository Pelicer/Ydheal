package controller;

import java.sql.DriverManager;
import java.sql.SQLException;

//In�cio da classe de conex�o//
public class ConexaoMySQL {

	public static String status = "N�o conectou...";

	// M�todo Construtor da Classe//

	public ConexaoMySQL() {

	}

	// M�todo de Conex�o//
	public static java.sql.Connection getConexaoMySQL() {

		java.sql.Connection connection = null; 

		try {


			String serverName = "localhost"; // caminho do servidor do BD
			String mydatabase = "db_serralheria"; // nome do seu banco de dados
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = "root"; // nome de um usu�rio de seu BD
			String password = ""; // sua senha de acesso

			connection = DriverManager.getConnection(url, username, password);

			return connection;

		} catch (SQLException e) {


			return null;

		}

	}

	

}
