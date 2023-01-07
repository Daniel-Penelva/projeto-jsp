<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>

	<!-- Pre-loader start -->
	<jsp:include page="theme-loader.jsp"></jsp:include>

	</div>
	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="navbarmain-menu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->
						<jsp:include page="page-header.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">

										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cadastro de Usu�rio</h4>
														<form class="form-material" enctype="multipart/form-data"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">ID:</label>
															</div>
															
															<div class="form-group form-default input-group mb-4">
																<div class="input-group-prepend">
																<c:if test="${modelLogin.fotouser != '' && modelLogin.fotouser != null}">
																	<a href="<%=request.getContextPath()%>/ServletUsuarioController?acao=downloadFoto&id=${modelLogin.id}">
																		<img alt="Imagem User" id="fotoembase64" src="${modelLogin.fotouser}" width="45px">
																	</a>
																</c:if>
																<c:if test="${modelLogin.fotouser == '' || modelLogin.fotouser == null}">
																	<img alt="Imagem User" id="fotoembase64" src="assets/images/user.png" width="45px">
																</c:if>
																</div>
																<input type="file" id="fileFoto" name="fileFoto" accept="image/*" onchange="visualizarImg('fotoembase64', 'fileFoto');" class="form-control-file" style="margin-top: 15px; margin-left: 5px">
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.email}">
																<span class="form-bar"></span> <label
																	class="float-label">E-mail:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input onblur="pesquisaCep();" type="text" name="cep" id="cep"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.cep}">
																<span class="form-bar"></span> <label
																	class="float-label">CEP:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="logradouro" id="logradouro"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.logradouro}">
																<span class="form-bar"></span> <label
																	class="float-label">Logradouro:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="bairro" id="bairro"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.bairro}">
																<span class="form-bar"></span> <label
																	class="float-label">Bairro:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="localidade" id="localidade"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.localidade}">
																<span class="form-bar"></span> <label
																	class="float-label">Localidade:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="uf" id="uf"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.uf}">
																<span class="form-bar"></span> <label
																	class="float-label">UF:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="numero" id="numero"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.numero}">
																<span class="form-bar"></span> <label
																	class="float-label">N�mero:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="login" id="login"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.login}">
																<span class="form-bar"></span> <label
																	class="float-label">Login:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.senha}">
																<span class="form-bar"></span> <label
																	class="float-label">Senha:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<select class="form-control"
																	aria-label="Default select example" name="perfil">
																	<option disabled="disabled">[Selecione o perfil]</option>
																	<option value="ADMIN" <% 
																	ModelLogin modelLogin = (ModelLogin)request.getAttribute("modelLogin");
																	if(modelLogin != null && modelLogin.getPerfil().equals("ADMIN")){
																		out.print(" ");
																		out.print("selected=\"selected\"");
																		out.print(" ");
																	} %>>Admin</option>
																	<option value="SECRETARIA" <% 
																	 modelLogin = (ModelLogin)request.getAttribute("modelLogin");
																	if(modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")){
																		out.print(" ");
																		out.print("selected=\"selected\"");
																		out.print(" ");
																	} %>>Secret�ria</option>
																	<option value="AUXILIAR" <% 
																	  modelLogin = (ModelLogin)request.getAttribute("modelLogin");
																	if(modelLogin != null && modelLogin.getPerfil().equals("AUXILIAR")){
																		out.print(" ");
																		out.print("selected=\"selected\"");
																		out.print(" ");
																	} %>>Auxiliar</option>
																</select> <span class="form-bar"></span> <label
																	class="float-label">Perfil:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
															<input type="radio" name="sexo" checked="checked" value="MASCULINO" <% 
																	modelLogin = (ModelLogin)request.getAttribute("modelLogin");
																	if(modelLogin != null && modelLogin.getSexo().equals("MASCULINO")){
																	out.print(" ");
																	out.print("checked=\"checked\"");
																	out.print(" ");
																    } %>>Masculino</>
															<input type="radio" name="sexo" value="FEMININO" <% 
																	modelLogin = (ModelLogin)request.getAttribute("modelLogin");
																	if(modelLogin != null && modelLogin.getSexo().equals("FEMININO")){
																	out.print(" ");
																	out.print("checked=\"checked\"");
																	out.print(" ");
																    } %> >Feminino</>
															</div>

															<button type="button"
																class="btn waves-effect waves-light hor-grd btn-grd-primary"
																onclick="limparForm();">Novo</button>

															<button
																class="btn waves-effect waves-light hor-grd btn-grd-success">Salvar</button>

															<button type="button"
																class="btn waves-effect waves-light hor-grd btn-grd-danger"
																onclick="criarDeleteComAjax();">Excluir</button>

															<button type="button" class="btn btn-warning"
																data-toggle="modal" data-target="#exampleModalUsuario">
																Pesquisar</button>

														</form>

													</div>
												</div>
											</div>
										</div>

										<!-- Informando usu�rio cadastrado -->
										<span id="msg">${msg}</span>

										<div style="height: 300px; overflow: scroll">
											<table class="table table-hover" id="tabelaresultadosview">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Visualizar</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${modelLogins}" var="ml">
														<tr>
															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td><a class="btn btn-dark"
																href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										
										<nav aria-label="Page navigation example">
										  <ul class="pagination">
										  	<%
										  		int totalPagina = (int) request.getAttribute("totalPagina");
										  	
											  	for(int p=0; p < totalPagina; p++){
											  		
											  		String url = request.getContextPath() + "/ServletUsuarioController?acao=paginar&pagina=" + (p * 5);
											  		out.print("<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"\">"+(p+1)+"</a></li>");
											  	}
										  	%>
										    
										  </ul>
										</nav>

									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Required Jquery -->
	<jsp:include page="javascriptfile.jsp"></jsp:include>

	<!-- Modal -->
	<div class="modal fade" id="exampleModalUsuario" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de
						Usu�rio</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">

					<div class="input-group mb-3">
						<input type="text" class="form-control"
							placeholder="Digite o nome do usu�rio" aria-label="nome"
							id="nomeBusca" aria-describedby="basic-addon2">
						<div class="input-group-append">
							<button class="btn btn-dark" type="button"
								onclick="buscarUsuario();">Buscar</button>
						</div>
					</div>

					<div style="height: 300px; overflow: scroll">
						<table class="table table-hover" id="tabelaresultados">
							<thead>
								<tr>
									<th scope="col">ID</th>
									<th scope="col">Nome</th>
									<th scope="col">Visualizar</th>
								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
					</div>
					
					<nav aria-label="Page navigation example">
						<ul class="pagination" id="ulPaginacaoUserAjax"> 
						
						</ul> 
					</nav>
						
					<span id="totalResultados"></span>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	
	    function pesquisaCep() {
			var cep = $("#cep").val();
			
			 $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
				
				 if (!("erro" in dados)) {
					 
					 $("#cep").val(dados.cep);
					 $("#logradouro").val(dados.logradouro);
                     $("#bairro").val(dados.bairro);
                     $("#localidade").val(dados.localidade);
                     $("#uf").val(dados.uf);
				 }
				
			});
		}
	    
	    function visualizarImg(fotoembase64, filefoto){
	    	var preview = document.getElementById(fotoembase64); /* Campo img HTML */
	    	var fileUser = document.getElementById(filefoto).files[0];
	    	
	    	/* Para trabalhar com a imagem */
	    	var reader = new FileReader();
	    	
	    	/* Para quando haja algum evento de carregamento nele */
	    	reader.onloadend = function (){
	    		preview.src = reader.result;  /* Carrega a foto na tela */
	    	};
	    	
	    	if(fileUser){
	    		reader.readAsDataURL(fileUser); /* Preview da imagem */
	    	}else{
	    		preview.src='';
	    	}
	    }
	
		function verEditar(id) {

			/* Capturar a action do ServletUsuarioController do formul�rio para poder passar os parametros  */
			var urlAction = document.getElementById('formUser').action;

			/* Redirecionamento com javascript passando o parametro - executa um doGet*/
			window.location.href = urlAction + '?acao=buscarEditar&id=' + id;
		}
		
		function buscaUserPagAjax(url){
			
			var urlAction = document.getElementById('formUser').action;
			var nomeBusca = document.getElementById('nomeBusca').value;
			
			$.ajax(
					{
						method : "get",
						url : urlAction,
						data : url,
						success : function(response, textStatus, xhr) {

							var json = JSON.parse(response);

							/* limpar a tabela depois de ter visto o resultado */
							$('#tabelaresultados > tbody > tr').remove();
							
							/* limpar a li depois de ter visto o resultado */
							$("#ulPaginacaoUserAjax > li").remove();

							for (var p = 0; p < json.length; p++) {
								$('#tabelaresultados > tbody')
										.append(
												'<tr> <td>'
														+ json[p].id
														+ '</td> <td>'
														+ json[p].nome
														+ '</td> <td> <button onclick="verEditar('
														+ json[p].id
														+ ')" type="button" class="btn btn-info">Visualizar</button> </td></tr>');
							}

							document
									.getElementById('totalResultados').textContent = 'Resultados: '
									+ json.length;
							
							
							/* Para gerar uma mensagem de status, por exemplo, sucesso */
							/* xhr pega os dados do cabe�alho */
							/* totalPagina � o parametro passado na ServletUsuario */
							
							var totalPagina = xhr.getResponseHeader("totalPagina");
							
							/* Mesma l�gica da p�gina��o */
							for(var p = 0; p < totalPagina; p++){
								
								var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina='+ (p * 5);
								
								 $("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">'+ (p + 1) +'</a></li>'); 				      
							}
						}

					}).fail(
					function(xhr, status, errorThrown) {
						alert('Erro ao buscar usu�rio por nome: '
								+ xhr.responseText);
					});
			
		}

		function buscarUsuario() {
			var nomeBusca = document.getElementById('nomeBusca').value;
			
            /* Validando que tem o valor para buscar no banco de dados */
			if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') {
				
				var urlAction = document.getElementById('formUser').action;

				$.ajax({

									method : "get",
									url : urlAction,
									data : "nomeBusca=" + nomeBusca + "&acao=buscarUserAjax",
									success : function(response, textStatus, xhr) {

										var json = JSON.parse(response);

										/* limpar a tabela depois de ter visto o resultado */
										$('#tabelaresultados > tbody > tr').remove();
										
										/* limpar a li depois de ter visto o resultado */
										$("#ulPaginacaoUserAjax > li").remove();

										for (var p = 0; p < json.length; p++) {
											$('#tabelaresultados > tbody')
													.append(
															'<tr> <td>'
																	+ json[p].id
																	+ '</td> <td>'
																	+ json[p].nome
																	+ '</td> <td> <button onclick="verEditar('
																	+ json[p].id
																	+ ')" type="button" class="btn btn-info">Visualizar</button> </td></tr>');
										}

										document
												.getElementById('totalResultados').textContent = 'Resultados: '
												+ json.length;
										
										
										/* Para gerar uma mensagem de status, por exemplo, sucesso */
										/* xhr pega os dados do cabe�alho */
										/* totalPagina � o parametro passado na ServletUsuario */
										
										var totalPagina = xhr.getResponseHeader("totalPagina");
										
										/* Mesma l�gica da p�gina��o */
										for(var p = 0; p < totalPagina; p++){
											
											 var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina='+ (p * 5);
											
											 $("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">'+ (p + 1) +'</a></li>');
										      
										}
									}

								}).fail(
								function(xhr, status, errorThrown) {
									alert('Erro ao buscar usu�rio por nome: ' + xhr.responseText);
								});

			}
		}

		function criarDeleteComAjax() {
			if (confirm('Deseja realmente excluir o usu�rio')) {
				/* Capturar do action do formul�rio */
				var urlAction = document.getElementById('formUser').action;

				/* Captura o campo id do usuario */
				var idUser = document.getElementById('id').value;

				$.ajax({

					method : "get",
					url : urlAction,
					data : "id=" + idUser + "&acao=deletarajax",
					success : function(response) {
						limparForm();
						documentElementById('msg').textContent = response;
					}

				}).fail(
						function(xhr, status, errorThrown) {
							alert('Erro ao deletar usu�rio por id: '
									+ xhr.responseText);
						});
			}
		}

		function criarDelete() {

			if (confirm('Deseja realmente excluir o usu�rio?')) {
				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
		}

		function limparForm() {
			var elementos = document.getElementById("formUser").elements; /* Retorn os elementos html dentro do form*/

			for (p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}

		}
	</script>
</body>

</html>
