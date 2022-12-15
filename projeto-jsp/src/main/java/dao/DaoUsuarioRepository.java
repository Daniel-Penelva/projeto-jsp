package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DaoUsuarioRepository {

	private Connection connection;

	public DaoUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public void gravarUsuario(ModelLogin modelLogin) throws SQLException {

		
			String sql = "INSERT INTO model_login (login, senha, nome, email) VALUES (?, ?, ?, ?)";

			PreparedStatement prepararSql = connection.prepareStatement(sql);

			prepararSql.setString(1, modelLogin.getLogin());
			prepararSql.setString(2, modelLogin.getSenha());
			prepararSql.setString(3, modelLogin.getNome());
			prepararSql.setString(4, modelLogin.getEmail());
			
			prepararSql.execute();
			
			connection.commit();
	}

}
