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
			String sql = "INSERT INTO model_login (login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement prepararSql = connection.prepareStatement(sql);

			prepararSql.setString(1, modelLogin.getLogin());
			prepararSql.setString(2, modelLogin.getSenha());
			prepararSql.setString(3, modelLogin.getNome());
			prepararSql.setString(4, modelLogin.getEmail());
			prepararSql.setLong(5, userLogado);
			prepararSql.setString(6, modelLogin.getPerfil());
			prepararSql.setString(7, modelLogin.getSexo());

			prepararSql.setString(8, modelLogin.getCep());
			prepararSql.setString(9, modelLogin.getLogradouro());
			prepararSql.setString(10, modelLogin.getBairro());
			prepararSql.setString(11, modelLogin.getLocalidade());
			prepararSql.setString(12, modelLogin.getUf());
			prepararSql.setString(13, modelLogin.getNumero());

			prepararSql.execute();

			connection.commit();

			/* Condicional para saber se realmente foi gravado uma foto */
			if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {

				sql = "update model_login set fotouser = ?, extensaofotouser = ? where login = ?";

				prepararSql = connection.prepareStatement(sql);

				prepararSql.setString(1, modelLogin.getFotouser());
				prepararSql.setString(2, modelLogin.getExtensaofotouser());
				prepararSql.setString(3, modelLogin.getLogin());

				prepararSql.execute();

				connection.commit();
			}

		} else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=? WHERE id= "
					+ modelLogin.getId();
			PreparedStatement prepararSql = connection.prepareStatement(sql);

			prepararSql.setString(1, modelLogin.getLogin());
			prepararSql.setString(2, modelLogin.getSenha());
			prepararSql.setString(3, modelLogin.getNome());
			prepararSql.setString(4, modelLogin.getEmail());
			prepararSql.setString(5, modelLogin.getPerfil());
			prepararSql.setString(6, modelLogin.getSexo());

			prepararSql.setString(7, modelLogin.getCep());
			prepararSql.setString(8, modelLogin.getLogradouro());
			prepararSql.setString(9, modelLogin.getBairro());
			prepararSql.setString(10, modelLogin.getLocalidade());
			prepararSql.setString(11, modelLogin.getUf());
			prepararSql.setString(12, modelLogin.getNumero());

			prepararSql.executeUpdate();
			connection.commit();

			/* Condicional para saber se realmente foi gravado uma foto */
			if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {

				sql = "update model_login set fotouser = ?, extensaofotouser = ? where id = ?";

				prepararSql = connection.prepareStatement(sql);

				prepararSql.setString(1, modelLogin.getFotouser());
				prepararSql.setString(2, modelLogin.getExtensaofotouser());
				prepararSql.setLong(3, modelLogin.getId());

				prepararSql.execute();

				connection.commit();
			}
		}

		// Terminando de gravar o usuário no banco de dados
		// Vai ser chamando o método consultar usuario para mostrar os dados na tela
		return this.consultarUsuario(modelLogin.getLogin(), userLogado);
	}

	/* Listar usuários de cinco em cinco */
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws SQLException {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " order by nome offset "+offset+" limit 5";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		ResultSet resultado = prepararSql.executeQuery();

		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	public int totalPagina(Long userLogado) throws Exception {
		String sql = "select count(1) as total from model_login where usuario_id = " + userLogado;

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		ResultSet resultado = prepararSql.executeQuery();
		
		resultado.next();

		Double cadastros = resultado.getDouble("total");

		Double porpagina = 5.0;

		Double pagina = cadastros / porpagina;

		Double resto = pagina % 2;

		/* Acrescenta uma página se for maior que zero */
		if (resto > 0) {
			pagina++;
		}
		
		return pagina.intValue();
	}

	/* Listar todos os usuários */
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws SQLException {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " limit 5";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		ResultSet resultado = prepararSql.executeQuery();

		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);
		}

		return retorno;
	}
	
	
	/* Listar usuário(s) por nome */
	public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offset) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);
		}

		return retorno;
	}
	

	/* Listar usuário(s) por nome */
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws SQLException {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			retorno.add(modelLogin);
		}

		return retorno;
	}
	
	/* Listar usuário(s) por nome */
	public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws SQLException {

		String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		prepararSql.setString(1, "%" + nome + "%");
		prepararSql.setLong(2, userLogado);

		ResultSet resultado = prepararSql.executeQuery();

		resultado.next();

		Double cadastros = resultado.getDouble("total");

		Double porpagina = 5.0;

		Double pagina = cadastros / porpagina;

		Double resto = pagina % 2;

		/* Acrescenta uma página se for maior que zero */
		if (resto > 0) {
			pagina++;
		}
		
		return pagina.intValue();
	}

	/* Consultar um usuário pelo login e pelo usuário logado */
	public ModelLogin consultarUsuario(String login, Long userLogado) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * from model_login WHERE upper(login) = upper('" + login
				+ "') and useradmin is false and usuario_id = " + userLogado;

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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}

		return modelLogin;
	}

	/* Consultar um usuário já logado */
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
			modelLogin.setUserAdmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));

		}

		return modelLogin;
	}

	/* Consultar um usuário pelo login */
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
			modelLogin.setUserAdmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}

		return modelLogin;
	}
	
	public ModelLogin consultarUsuarioId(Long id) throws SQLException {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * from model_login WHERE id = ? and useradmin is false";

		PreparedStatement prepararSql = connection.prepareStatement(sql);
		prepararSql.setLong(1, id);
		

		// Vai ser usado um resultSet pq vai retornar uma lista de objetos usuario
		ResultSet resultado = prepararSql.executeQuery();

		// Se tem resultado
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
		}

		return modelLogin;
	}

	/* Consultar um usuário pelo id */
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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
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
