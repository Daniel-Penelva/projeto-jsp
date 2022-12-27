package servlets;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import dao.DaoUsuarioRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ServletGenericUtil extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	/* criando um objeto para buscar o método consultarLogin */
	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();

	
	/* Permite retornar o codigo do usuário */
	public Long getUserLogado(HttpServletRequest request) throws SQLException {

		/* Abre uma sessao e pega o request */
		HttpSession session = request.getSession();

		/* Captura o usuario logado e coloca na sessão */
		String usuarioLogado = (String) session.getAttribute("usuario");

		/* Retorna a consulta de logar pelo id */
		return daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado).getId();
	}

}
