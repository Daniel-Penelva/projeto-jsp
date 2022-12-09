package servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

/* Mapeamento  de URL que vem da tela */

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletLogin() {
		super();

	}

	/* Recebe os dados pela url em parametros */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/* Recebe os dados enviados por um formulário */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* Captura os valores da tela */
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		/* A url vai ser chamada para acessar e logar o usuario 
		 * Lembrando que é uma url dinamica e vai ser passada no RequestDispatcher*/
		String url = request.getParameter("url");

		/* Verificar se os valores login e senha foram preenchidos e validados */
		if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

			/* Esses valores sao atribuidos ao objeto modelLogin */
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);

			/* Simulando acesso do Login */
			if (modelLogin.getLogin().equalsIgnoreCase("admin") 
					&& modelLogin.getSenha().equalsIgnoreCase("admin")) {
				
				/* Atributo de sessão para manter o usuário logado na sessão */
				request.getSession().setAttribute("usuario", modelLogin.getLogin());
				
				/* Validação da url para verificar se ela é nula e se ela for nula é 
				 * adicionado um valor padrão da página principal que é a página de logado.*/
				if(url == null || url.equals("null")) {
					url = "principal/principal.jsp";
				
				/* Acessando e redirecionando para a pagina principal */
				RequestDispatcher redirecionar = request.getRequestDispatcher(url);
				redirecionar.forward(request, response);
				}
				
			} else {
				/* simulando preenchimento errado de login e senha */
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Informe o login e a senha corretamente!");
				redirecionar.forward(request, response);
			}

		} else {

			/* Redireciona para outra tela caso o usuário não preencha o login e a senha */
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			request.setAttribute("msg", "Informe o login e a senha!");
			redirecionar.forward(request, response);
		}
	}

}
