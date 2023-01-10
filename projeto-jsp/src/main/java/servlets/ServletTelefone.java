package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.DaoUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

public class ServletTelefone extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();

	public ServletTelefone() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String iduser = request.getParameter("iduser");

		try {
			if (iduser != null && !iduser.isEmpty()) {

				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(Long.parseLong(iduser));

				request.setAttribute("usuario", modelLogin);

				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

			} else {
				// Criando lista de usuários buscando dados do BD
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));

				// Para recuperar a lista de usuários
				request.setAttribute("modelLogins", modelLogins);

				// Mensagem para ser inserida na tela
				request.setAttribute("msg", "Lista de usuários carregados");

				/* Atributo para retornar o total de pagina */
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

				/* Redireciona para página usuario.jsp */
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
