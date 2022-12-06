<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Receber Nome</title>
</head>
<body>

	<%
	String nome = request.getParameter("nome");
	out.print(nome);

	String idade = request.getParameter("idade");
	out.print(idade);
	%>

</body>
</html>