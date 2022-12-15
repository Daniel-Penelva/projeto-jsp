package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;

import dao.DaoUsuarioRepository;

public class ServletUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Criar um objeto dao para chamar o método gravar
	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();

	public ServletUsuarioController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			/* Capturando os parametros do formulário */
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			// Inicializar o objeto
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);

			//Gravar no banco de dados o usuario 
			daoUsuarioRepository.gravarUsuario(modelLogin);
			
			//Mensagem para ser inserida na tela 
			request.setAttribute("msg", "Usuário gravado no banco de dados com sucesso!");

			// Para recuperar os dados na tela ou para carregar o objeto (os dados) na tela
			request.setAttribute("modelLogin", modelLogin);

			// Redirecionar para uma página
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			/* Redireciona para a tela de erro */
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

}
