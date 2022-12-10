package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DaoLoginRepository {
	
	private Connection connection;
	
	/* Construtor para conectar com o bd */
	public DaoLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	/* Método para validar o login retornando um boolean recebendo como parametro o modelLogin.
	 * Se logou (true) se não logou (false) */ 
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception{
		
		String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?)";
		
		/* Prepara a SQL */
		PreparedStatement prepararSql = connection.prepareStatement(sql); 
		prepararSql.setString(1,modelLogin.getLogin());
		prepararSql.setString(2, modelLogin.getSenha());
		
		/* Executa a sql */
		ResultSet resultSet = prepararSql.executeQuery();
		
		/* Autenticado*/
		if(resultSet.next()) {
			return true;
		}

		return false;	// não autenticado
	}
	
	/**
	 * Esse Dao vai ser declarado no @ServletLogin e criado um objeto para condicionar a 
	 * validação da autenticação do login.
	 * */
}
