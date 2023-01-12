<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="br.com.playtime.bean.User" %>


<%@page pageEncoding="ISO-8859-1" %>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Listar Usuários</title>
</head>
<body>
	<center>
		<table width="100%" border="1" cellpadding="2" cellspacing="0">
			<tr>
				<th colspan="6"><h3>Lista de Usuários</h3></th>
			<tr>
			<tr>
				<th>ID</th>
				<th>Nome</th>
				<th>Username</th>
				<th>Senha</th>
			<tr>
			<%
				List<User> lista = new ArrayList<User>();
				lista = (ArrayList) request.getAttribute("userList");
				for(User u: lista){%>
					<tr>
						<td><%u.getIdUser();%></td>
						<td><%u.getNome();%></td>
						<td><%u.getUsername();%></td>
						<td><%u.getSenha();%></td>
					</tr>
					<%
				}			
			%>
			<tr>
				<td colspan="6" align="center"><a href="index.jsp">Página Principal</a></td>
			<tr>
		</table>
	</center>
</body>
</html>
