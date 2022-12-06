<%@page import="java.io.PrintWriter"%>
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
	out.println("Nome:" + nome);
	
	String idade = request.getParameter("idade");
	out.println("Idade: " + idade);
	
	/*Outra forma de saída*/
	String sobrenome = request.getParameter("sobrenome");
	
	PrintWriter saida = response.getWriter();
	saida.println("Sobrenome: " + sobrenome);
	%>
	
	

</body>
</html>