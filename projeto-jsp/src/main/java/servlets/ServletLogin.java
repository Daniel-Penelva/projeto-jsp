package servlets;

import java.io.IOException;

import dao.DaoLoginRepository;
import dao.DaoUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

/* Mapeamento  de URL que vem da tela */

@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" })
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/* Declarando e criando um objeto dao login */
	private DaoLoginRepository daoLoginRepository = new DaoLoginRepository();
	
	/* Declarando e criando um objeto dao usuário */
	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();
	
	public ServletLogin() {
	}

	/* Recebe os dados pela url em parametros */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {

			/* Para invalidar a sessão */
			request.getSession().invalidate();

			/* Dispachar redirecionando para outra página */
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);

		} else {
			// Para continuar o fluxo normal da página ServletLogin
			doPost(request, response);
		}

	}

	/* Recebe os dados enviados por um formulário */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* Captura os valores da tela */
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");

		/*
		 * A url vai ser chamada para acessar e logar o usuario Lembrando que é uma url
		 * dinamica e vai ser passada no RequestDispatcher
		 */
		String url = request.getParameter("url");

		try {

			/* Verificar se os valores login e senha foram preenchidos e validados */
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

				/* Esses valores sao atribuidos ao objeto modelLogin */
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);

				/* Simulando acesso do Login */
				if (daoLoginRepository.validarAutenticacao(modelLogin)) {
					
					// Para consultar no banco de dados
					modelLogin = daoUsuarioRepository.consultarUsuarioLogado(login);
					
					/* Atributo de sessão para manter o usuário logado na sessão */
					request.getSession().setAttribute("usuario", modelLogin.getLogin());
					
					/* Atributo de sessão para manter o admin logado na sessão */
					request.getSession().setAttribute("isAdmin", modelLogin.getUserAdmin());

					/*
					 * Validação da url para verificar se ela é nula e se ela for nula é adicionado
					 * um valor padrão da página principal que é a página de logado.
					 */
					if (url == null || url.equals("null")) {
						url = "principal/principal.jsp";
					}

					/* Acessando e redirecionando para a pagina principal */
					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);

				} else {
					/* simulando preenchimento errado de login e senha */
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login e a senha corretamente!");
					redirecionar.forward(request, response);
				}

			} else {

				/* Redireciona para outra tela caso o usuário não preencha o login e a senha */
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Informe o login e a senha!");
				redirecionar.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			/* Redireciona para a tela de erro */
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
