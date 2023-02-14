package servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DaoUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;
import util.ReportUtil;

@MultipartConfig
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	// Criar um objeto dao para chamar o método gravar
	private DaoUsuarioRepository daoUsuarioRepository = new DaoUsuarioRepository();

	public ServletUsuarioController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			/*
			 * Captura o parametro ação para enviar a requisição no ServletUsuarioController
			 */
			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				/* Captura o parâmetro id do formulário */
				String idUser = request.getParameter("id");

				/* Chama o método deletar usuário para deletar no banco */
				daoUsuarioRepository.deletarUsuario(idUser);

				// Criando lista de usuários buscando dados do BD
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));

				// Para recuperar a lista de usuários
				request.setAttribute("modelLogins", modelLogins);

				// Mensagem para ser inserida na tela
				request.setAttribute("msg", "Lista de usuários carregados");

				/* Atribui uma mensagem na tela */
				request.setAttribute("msg", "Excluido com sucesso!");

				/* Atributo para retornar o total de pagina */
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

				/* Redireciona para página usuario.jsp */
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				/* Captura o parâmetro id do formulário */
				String idUser = request.getParameter("id");

				/* Chama o método deletar usuário para deletar no banco */
				daoUsuarioRepository.deletarUsuario(idUser);

				/*
				 * Resposta de excluido - vai ser enviado para o @criarDeleteAjax() no response
				 */
				response.getWriter().write("Excluido com sucesso!");

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {

				/* Captura o parâmetro nomeBusca do modal */
				String nomeBusca = request.getParameter("nomeBusca");

				/* Chama o método deletar usuário para deletar no banco */
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca,
						super.getUserLogado(request));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				/*
				 * adicionar por cabeçalho, ou seja, para não misturar esses dados com a lista
				 * de retorno do ModelLogin. Portanto, a resposta não vai ser enviado pela lista
				 * e sim pelo cabeçalho.
				 */
				response.addHeader("totalPagina", "" + daoUsuarioRepository
						.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));

				/* retorna a lista json de usuario */
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

				/* Captura o parâmetro nomeBusca do modal */
				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");

				/* Chama o método deletar usuário para deletar no banco */
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioListOffSet(nomeBusca,
						super.getUserLogado(request), Integer.parseInt(pagina));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				/*
				 * adicionar por cabeçalho, ou seja, para não misturar esses dados com a lista
				 * de retorno do ModelLogin. Portanto, a resposta não vai ser enviado pela lista
				 * e sim pelo cabeçalho.
				 */
				response.addHeader("totalPagina", "" + daoUsuarioRepository
						.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));

				/* retorna a lista json de usuario */
				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {

				/* Captura o parâmetro nomeBusca do modal */
				String id = request.getParameter("id");

				// chama o método dao
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(id, super.getUserLogado(request));

				// Criando lista de usuários buscando dados do BD
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));

				// Para recuperar a lista de usuários
				request.setAttribute("modelLogins", modelLogins);

				// Mensagem para ser inserida na tela
				request.setAttribute("msg", "Lista de usuários carregados");

				// Mensagem para ser inserida na tela
				request.setAttribute("msg", "Usuário em edição");

				// Para recuperar os dados na tela ou para carregar o objeto (os dados) na tela
				request.setAttribute("modelLogin", modelLogin);

				/* Atributo para retornar o total de pagina */
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

				// Redirecionar para uma página
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {

				// Criando lista de usuários buscando dados do BD
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));

				// Mensagem para ser inserida na tela
				request.setAttribute("msg", "Lista de usuários carregados");

				// Para recuperar os dados na tela ou para carregar o objeto (os dados) na tela
				request.setAttribute("modelLogins", modelLogins);

				/* Atributo para retornar o total de pagina */
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

				// Redirecionar para uma página
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {

				/* Captura o parâmetro id do formulário */
				String idUser = request.getParameter("id");

				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(idUser, super.getUserLogado(request));

				if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
					response.getOutputStream()
							.write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));

				}
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {

				Integer offset = Integer.parseInt(request.getParameter("pagina"));

				List<ModelLogin> modelLogins = daoUsuarioRepository
						.consultaUsuarioListPaginada(this.getUserLogado(request), offset);

				// Para recuperar a lista de usuários
				request.setAttribute("modelLogins", modelLogins);

				/* Atributo para retornar o total de pagina */
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

				/* Redireciona para página usuario.jsp */
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {

				String dataIncial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				// Se não for preenchido as datas irá gerar todos os usuários
				if (dataIncial == null || dataIncial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
					request.setAttribute("listaUser",
							daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
				} else {
					request.setAttribute("listaUser", daoUsuarioRepository
							.consultaUsuarioListRel(super.getUserLogado(request), dataIncial, dataFinal));
				}

				// Para mostrar os dados na tela
				request.setAttribute("dataInicial", dataIncial);
				request.setAttribute("dataFinal", dataFinal);

				// Para rediresionar para a página reluser
				request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPDF")) {

				String dataIncial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				List<ModelLogin> modelLogins = null;

				// Se não for preenchido as datas irá gerar todos os usuários
				if (dataIncial == null || dataIncial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {

					modelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request));

				} else {

					modelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataIncial,
							dataFinal);
				}

				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio") + File.separator);
				
				byte[] relatorio = new ReportUtil().geraRelatorioPDF(modelLogins, "rel-user-jsp", params, request.getServletContext());

				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
				response.getOutputStream().write(relatorio);

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
			/* Redireciona para a tela de erro */
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String mensagem = "Usuário gravado no banco de dados com sucesso!";

			/* Capturando os parametros do formulário */
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			String dataNascimento = request.getParameter("dataNascimento");
			String rendaMensal = request.getParameter("rendamensal");

			// Tem que retirar o cifrao e a virgula e ponto que especifica o valor do
			// dinheiro
			// o código abaixo vai fazer uma troca para vazio
			// tem que ser enviado número limpo para o BD
			rendaMensal = rendaMensal.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");

			// Inicializar o objeto
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);

			// Pega o valor da tela que está formatada em dd/mm/yyyy e converte em texto em
			// data na forma yyyy-mm-dd
			modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd")
					.format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));

			modelLogin.setRendamensal(Double.valueOf(rendaMensal));

			/* Condição para capturar a foto */
			if (ServletFileUpload.isMultipartContent(request)) {
				/* Pega a foto da tela */
				Part part = request.getPart("fileFoto");

				if (part.getSize() > 0) {
					/* converter a imagem para byte */
					byte[] foto = IOUtils.toByteArray(part.getInputStream());

					/*
					 * Converte agora para String - OBS. gera uma String bem grande O resultado
					 * abaixo é o padrão a ser usado.
					 */
					String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64,"
							+ new Base64().encodeBase64String(foto);

					/* captura a imagem e a extensão */
					modelLogin.setFotouser(imagemBase64);
					modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
				}

			}

			// Validações para conferir se já existe login (true) e se é um novo id (que é
			// um usuario novo)
			// Se sim (true) então não pode cadastrar.
			if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				mensagem = "Já existe usuário com o mesmo login, informe outro login.";
			} else {

				if (modelLogin.isNovo()) {
					mensagem = "Gravado com sucesso!";
				} else {
					mensagem = "Atualizado com sucesso!";
				}

				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
			}

			// Criando lista de usuários buscando dados do BD
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));

			// Para recuperar a lista de usuários
			request.setAttribute("modelLogins", modelLogins);

			// Mensagem para ser inserida na tela
			request.setAttribute("msg", "Lista de usuários carregados");

			// Mensagem para ser inserida na tela
			request.setAttribute("msg", mensagem);

			// Para recuperar os dados na tela ou para carregar o objeto (os dados) na tela
			request.setAttribute("modelLogin", modelLogin);

			/* Atributo para retornar o total de pagina */
			request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

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
