package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.DaoTelefoneRepository;
import dao.DaoUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import model.ModelTelefone;

public class ServletTelefone extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();
	private DaoTelefoneRepository daoTelefoneRepository = new DaoTelefoneRepository();

	public ServletTelefone() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String iduser = request.getParameter("iduser");

		try {
			if (iduser != null && !iduser.isEmpty()) {

				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(Long.parseLong(iduser));

				request.setAttribute("modelLogin", modelLogin);

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

		try {
			
			String usuario_pai_id = request.getParameter("id");
			String numero = request.getParameter("numero");
			
			ModelTelefone modelTelefone = new ModelTelefone();
			modelTelefone.setNumero(numero);
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultarUsuarioId(Long.parseLong(usuario_pai_id)));
			modelTelefone.setUsuario_cad_id(super.getUserLogadoObject(request));
			
			daoTelefoneRepository.gravaTelefone(modelTelefone);
			
			// Mensagem para ser inserida na tela
			request.setAttribute("msg", "Salvo com sucesso");
			
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
		
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
	}

}
