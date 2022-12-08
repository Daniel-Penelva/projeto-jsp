<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Projeto JSP</title>
</head>
<body>
	<h1>Bem vindo ao Projeto JSP!</h1>

	<form action="ServletLogin" method="post">

		Nome: <input name="nome"> 
		Idade: <input name="idade">

		<input type="submit" value="enviar">


	</form>

</body>
</html>