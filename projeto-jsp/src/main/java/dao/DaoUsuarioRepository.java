package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DaoUsuarioRepository {

	private Connection connection;

	public DaoUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	/* Gravar usuário */
	public ModelLogin gravarUsuario(ModelLogin modelLogin, Long userLogado) throws SQLException {

		/* Inserir novo usuario */
		if (modelLogin.isNovo()) {
			String sql = "INSERT INTO model_login (login, senha, nome, email, usuario_id) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement prepararSql = connection.prepareStatement(sql);

			prepararSql.setString(1, modelLogin.getLogin());
			prepararSql.setString(2, modelLogin.getSenha());
			prepararSql.setString(3, modelLogin.getNome());
			prepararSql.setString(4, modelLogin.getEmail());
			prepararSql.setLong(5, userLogado);

			prepararSql.execute();

			connection.commit();

		} else {
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
		return this.consultarUsuario(modelLogin.getLogin(), userLogado);
	}

	/* Listar todos os usuários */
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws SQLException {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado;

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		ResultSet resultado = prepararSql.executeQuery();

		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	/* Listar usuário por nome */
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws SQLException {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ?";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		prepararSql.setString(1, "%" + nome + "%");
		prepararSql.setLong(2, userLogado);

		ResultSet resultado = prepararSql.executeQuery();

		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	/* Consultar usuário pelo login e pelo usuário logado */
	public ModelLogin consultarUsuario(String login, Long userLogado) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * from model_login WHERE upper(login) = upper('" + login + "') and useradmin is false and usuario_id = " + userLogado;

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
	
	
	/* Consultar usuário pelo login */
	public ModelLogin consultarUsuarioLogado(String login) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * from model_login WHERE upper(login) = upper('" + login + "')";

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
	
	
	
	/* Consultar usuário pelo login */
	public ModelLogin consultarUsuario(String login) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * from model_login WHERE upper(login) = upper('" + login + "') and useradmin is false";

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
	
	

	/* Consultar id do usuáro */
	public ModelLogin consultarUsuarioId(String id, Long userLogado) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * from model_login WHERE id = ? and useradmin is false and usuario_id = ?";

		PreparedStatement prepararSql = connection.prepareStatement(sql);
		prepararSql.setLong(1, Long.parseLong(id));
		prepararSql.setLong(2, userLogado);

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

	/* Validar login do usuário */
	public boolean validarLogin(String login) throws Exception {

		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('" + login + "')";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		ResultSet resultado = prepararSql.executeQuery();

		/* Para entrar no resultado sql */
		resultado.next();
		return resultado.getBoolean("existe");

		/*
		 * Ou pode fazer assim:
		 * 
		 * ... a partir da linha 70
		 * 
		 * if(resultado.next()) { return resultado.getBoolean("existe"); }
		 * 
		 * return false;
		 * 
		 */
	}

	/* Deletar usuáro */
	public void deletarUsuario(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false";

		PreparedStatement prepararSql = connection.prepareStatement(sql);
		prepararSql.setLong(1, Long.parseLong(idUser));

		prepararSql.executeUpdate();
		connection.commit();
	}

}
