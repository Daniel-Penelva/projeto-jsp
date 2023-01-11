package servlets;

import java.io.IOException;
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

		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equals("excluir")) {
				// captura o id do usuario
				String idfone = request.getParameter("id");

				/* deleta o usuário */
				daoTelefoneRepository.deleteFone(Long.parseLong(idfone));

				/* captura o id do usuario */
				String userpai = request.getParameter("userpai");

				/* Retorna o objeto na tela */
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(Long.parseLong(userpai));

				/* Lista todos os objetos e mostra na tela, menos o que foi deletado */
				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(modelLogin.getId());
				request.setAttribute("modelTelefones", modelTelefones);

				request.setAttribute("msg", "Telefone Excluido");
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

				/* return para encerrar a execução e não ir para os blocos de codigo abaixo */
				return;
			}

			String iduser = request.getParameter("iduser");

			if (iduser != null && !iduser.isEmpty()) {

				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(Long.parseLong(iduser));

				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(modelLogin.getId());
				request.setAttribute("modelTelefones", modelTelefones);

				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

			} else {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
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

			List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(Long.parseLong(usuario_pai_id));

			ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(Long.parseLong(usuario_pai_id));

			request.setAttribute("modelLogin", modelLogin);
			request.setAttribute("modelTelefones", modelTelefones);
			request.setAttribute("msg", "Salvo com sucesso");
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
