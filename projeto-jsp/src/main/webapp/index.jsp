<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<title>Projeto JSP</title>

<style type="text/css">

form{
position: absolute;
top:35%;
left: 33%;
right: 33%;
}

h5{
position: absolute;
top:20%;
left: 33%;
}

h5.msg{
position: absolute;
top:70%;
left: 33%;
color:red;
}

</style>

</head>
<body>
	<h5>Bem vindo ao Projeto JSP!</h5>
	
	<!-- São as classes do bootstrap que gera os layouts para o formulário. -->

	<form action="ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
	<input type="hidden" value="<%=request.getParameter("url")%>" name="url"> 
	
	<div class="col-md-6">
	<label class="form-label">Login:</label>
	<input class="form-control" name="login" type="text" required="required">
	<div class="invalid-feedback">
      Informe Login
    </div>
	</div>
	
	<div class="col-md-6">
	<label class="form-label">Senha:</label>
	<input class="form-control" name="senha" type="password" required="required">
	<div class="invalid-feedback">
      Informe Senha
    </div> 
	</div>
	
	<div class="col-12">
	<input class="btn btn-outline-secondary" type="submit" value="Acessar">
	</div>

	</form>

	<!-- Redirecionamento do RequestDispatcher -->
	<h5 class="msg">${msg}</h5>

	<!-- Opção 1: Pacote Bootstrap com Popper para javascript -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"> </script>
		
<script type="text/javascript">

//Exemplo de JavaScript inicial para desabilitar envios de formulário se houver campos inválidos
(function () {
  'use strict'

  // Busca todos os formulários aos quais queremos aplicar estilos personalizados de validação de Bootstrap
  var forms = document.querySelectorAll('.needs-validation')

  // Faz um loop sobre eles e evita submissão
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()

</script>

</body>
</html>