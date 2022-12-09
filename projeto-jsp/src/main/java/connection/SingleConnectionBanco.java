package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	private static String banco = "jdbc:postgresql://localhost:5433/projetoJSP?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null;

	/* Método para retornar a conexão existente */
	public static Connection getConnection() {
		return connection;
	}

	/*
	 * Duas formas de chamar o método conectar 1º de forma direta e a 2º pela classe
	 */
	static {
		conectar();
	}

	public SingleConnectionBanco() {
		conectar();
	}

	/* Método para conectar ao banco de dados */
	private static void conectar() {
		try {
			if (connection == null) {

				/* Carrega o drive de conexão do banco postgresql */
				Class.forName("org.postgresql.Driver");

				/* Conectar o drive com o banco de dados */
				connection = DriverManager.getConnection(banco, user, senha);

				/* Para não efetuar alterações no banco sem nossa autorização */
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * OBS. 
	 * Essa Classe de Conexão vai ser chamada pela classe @FilterAutenticacao.
	 * Ele é executado quando o projeto estiver subindo.
	 * Para isso vamos utilizar o método init que inicializa essa configuração do banco.
	 * E no método destroy vai ser fechado a conexão com o bd.
	 */

}
