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

														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="get" id="formUser">

															<input type="hidden" id="acaoRelatorioImprimirTipo"
																name="acao" value="imprimirRelatorioUser">

															<div class="form-row align-items-center">
																<div class="col-sm-3 my-1">
																	<label class="sr-only" for="dataInicial">Data
																		Inicial</label> <input value="${dataInicial}" type="text"
																		class="form-control" id="dataInicial"
																		name="dataInicial">
																</div>

																<div class="col-sm-3 my-1">
																	<label class="sr-only" for="dataFinal">Data
																		Final</label> <input value="${dataFinal}" type="text"
																		class="form-control" id="dataFinal" name="dataFinal">
																</div>

																<div class="col-auto">
																	<button type="button" onclick="gerarGrafico();"
																		class="btn btn-primary mb-2">Gerar Gr�fico</button>
																</div>
															</div>

														</form>

														<div style="height: 500px; overflow: scroll">
															<div>
																<canvas id="myChart"></canvas>
															</div>
														</div>

													</div>
												</div>
											</div>
										</div>
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

	<!-- Startizando o chartjs -->
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

	<script type="text/javascript">

		/* Gerar gr�fico de sal�rio */
		function gerarGrafico() {

			var myChart = new Chart(document.getElementById('myChart'), {
				type : 'line',
				data : {
					labels : [ 'January', 'February', 'March', 'April', 'May',
							'June', ],
					datasets : [{
						label : 'Gr�fico de m�dia salarial por tipo',
						backgroundColor : 'rgb(255, 99, 132)',
						borderColor : 'rgb(255, 99, 132)',
						data : [ 0, 10, 5, 2, 20, 30, 45 ],
					} ]
				},
				options : {}
			});

		}

		/* Calend�rio do JQuery - traduzir o calend�rio */
		$(function() {

			$("#dataInicial")
					.datepicker(
							{
								dateFormat : 'dd/mm/yy',
								dayNames : [ 'Domingo', 'Segunda', 'Ter�a',
										'Quarta', 'Quinta', 'Sexta', 'S�bado' ],
								dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
										'S', 'D' ],
								dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
										'Qui', 'Sex', 'S�b', 'Dom' ],
								monthNames : [ 'Janeiro', 'Fevereiro', 'Mar�o',
										'Abril', 'Maio', 'Junho', 'Julho',
										'Agosto', 'Setembro', 'Outubro',
										'Novembro', 'Dezembro' ],
								monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
										'Mai', 'Jun', 'Jul', 'Ago', 'Set',
										'Out', 'Nov', 'Dez' ],
								nextText : 'Pr�ximo',
								prevText : 'Anterior'
							});
		});

		/* Calend�rio do JQuery - traduzir o calend�rio */
		$(function() {

			$("#dataFinal")
					.datepicker(
							{
								dateFormat : 'dd/mm/yy',
								dayNames : [ 'Domingo', 'Segunda', 'Ter�a',
										'Quarta', 'Quinta', 'Sexta', 'S�bado' ],
								dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
										'S', 'D' ],
								dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
										'Qui', 'Sex', 'S�b', 'Dom' ],
								monthNames : [ 'Janeiro', 'Fevereiro', 'Mar�o',
										'Abril', 'Maio', 'Junho', 'Julho',
										'Agosto', 'Setembro', 'Outubro',
										'Novembro', 'Dezembro' ],
								monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
										'Mai', 'Jun', 'Jul', 'Ago', 'Set',
										'Out', 'Nov', 'Dez' ],
								nextText : 'Pr�ximo',
								prevText : 'Anterior'
							});
		});
	</script>
</body>

</html>
