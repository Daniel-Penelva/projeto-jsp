package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DaoUsuarioRepository {

	private Connection connection;

	public DaoUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin modelLogin) throws SQLException {

		/* Inserir novo usuario */
		if(modelLogin.isNovo()) {
		String sql = "INSERT INTO model_login (login, senha, nome, email) VALUES (?, ?, ?, ?)";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		prepararSql.setString(1, modelLogin.getLogin());
		prepararSql.setString(2, modelLogin.getSenha());
		prepararSql.setString(3, modelLogin.getNome());
		prepararSql.setString(4, modelLogin.getEmail());

		prepararSql.execute();

		connection.commit();
		
		}else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id= " + modelLogin.getId();
			PreparedStatement prepararSql = connection.prepareStatement(sql);
			
			prepararSql.setString(1, modelLogin.getLogin());
			prepararSql.setString(2, modelLogin.getSenha());
			prepararSql.setString(3, modelLogin.getNome());
			prepararSql.setString(4, modelLogin.getEmail());
			
			prepararSql.executeUpdate();
			connection.commit();
		}

		// Terminando de gravar o usuário no banco de dados
		// Vai ser chamando o método consultar usuario para mostrar os dados na tela
		return this.consultarUsuario(modelLogin.getLogin());
	}

	public ModelLogin consultarUsuario(String login) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * from model_login WHERE upper(login) = upper('"+login+"')";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		// Vai ser usado um resultSet pq vai retornar uma lista de objetos usuario
		ResultSet resultado = prepararSql.executeQuery();

		// Se tem resultado
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}

		return modelLogin;
	}

	
	public boolean validarLogin(String login) throws Exception {

		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"')";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		ResultSet resultado = prepararSql.executeQuery();
		
		/* Para entrar no resultado sql */
        resultado.next();
	    return resultado.getBoolean("existe");
		
		
		/* Ou pode fazer assim:
		 
		 ... a partir da linha 70 
		 
		 if(resultado.next()) {
			return resultado.getBoolean("existe");
		}
	
		return false;
		  
		 */
	}
	
	public void deletarUsuario(String idUser) throws Exception{
		String sql = "DELETE FROM model_login WHERE id = ?";
		
		PreparedStatement prepararSql = connection.prepareStatement(sql);
		prepararSql.setLong(1, Long.parseLong(idUser));
		
		prepararSql.executeUpdate();
		connection.commit();	
	}

}
