package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;

/**
 * Intercepta todas as requisições que vierem do projeto ou mapeamento. O
 * mapeamento ocorre a partir da página principal.
 */

@WebFilter(urlPatterns = "/principal/*")
public class FilterAutenticacao extends HttpFilter implements Filter {

	/* Tem que declarar o connection para ter conexão com o bd */
	private static Connection connection;

	public FilterAutenticacao() {
		super();
	}

	/*
	 * Encerra o processo quando o servidor é parado. Destroi os processos de
	 * conexão com o banco de dados.
	 */
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Intercepta as requisições e dá as respostas no sistema. 
	 * Tudo feito no sistema vai passar por ele, por exemplo, Validação de autenticação, commit, rollback
	 * de transações no banco de dados, redirecionamento de páginas, etc...
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			/* Tem que capturar o http servlet request para iniciar a sessão */
			HttpServletRequest req = (HttpServletRequest) request;

			/* Abre uma sessao e pega o request */
			HttpSession session = req.getSession();

			/* Captura o usuario logado e coloca na sessão */
			String usuarioLogado = (String) session.getAttribute("usuario");

			/*
			 * Lembrando que a sessao pode ser usada para todas as urls que podem ser acessadas. 
			 * No caso, só poderá ser acessado urls relacionado com /principal/ServletLogin
			 */
			String urlParaAutenticar = req.getServletPath();

			/*
			 * Por fim é feito a validação de logado do usuário na sessão. 
			 * Ou seja, valida se está logado senão redireciona para a tela de login. 
			 * Só podem ser acessados urls relacionado com /principal/ServletLogin
			 */
			if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {

				/* Redirecionamento para a tela de login */
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realiza o login!");
				redireciona.forward(request, response);

				/* Para a excução e redireciona para o login */
				return;

			} else {
				chain.doFilter(request, response);
			}
			
			/* Por fim faz um commit no bd*/
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			
			/* Redireciona para a tela de erro */
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/*
	 * Inicia os processos ou recursos quando o servidor sobe o projeto. 
	 * Inicia a conexão com o banco de dados.
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
	}

}
