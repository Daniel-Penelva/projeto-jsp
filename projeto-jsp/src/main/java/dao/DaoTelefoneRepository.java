package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DaoTelefoneRepository {

	private Connection connection;
	
	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();

	/* Conectar com o banco de dados */
	public DaoTelefoneRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public List<ModelTelefone> listFone(Long idUserPai) throws SQLException {

		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();

		String sql = "select * from telefone where usuario_id_pai =?";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		ResultSet rs = prepararSql.executeQuery();

		while (rs.next()) {
			ModelTelefone modelTelefone = new ModelTelefone();

			/* Carregando os campos*/
			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			
			/* Vale ressaltar que nao estamos carregando um campo e sim um objeto do tipo ModelUsuario*/
			modelTelefone.setUsuario_cad_id(daoUsuarioRepository.consultarUsuarioId(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultarUsuarioId(rs.getLong("usuario_pai_id")));

			retorno.add(modelTelefone);
		}
		return retorno;
	}

	public void gravaTelefone(ModelTelefone modelTelefone) throws SQLException {

		String sql = "insert into telefone (numero, usuario_pai_id, usuario_cad_id) values (?,?,?)";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		prepararSql.setString(1, modelTelefone.getNumero());
		prepararSql.setLong(2, modelTelefone.getUsuario_pai_id().getId());
		prepararSql.setLong(3, modelTelefone.getUsuario_cad_id().getId());

		prepararSql.execute();

		connection.commit();
	}

	public void deleteTelefone(Long id) throws SQLException {
		String sql = "delete from telefone where id = ?";

		PreparedStatement prepararSql = connection.prepareStatement(sql);

		prepararSql.setLong(1, id);

		prepararSql.executeUpdate();

		connection.commit();
	}

}
