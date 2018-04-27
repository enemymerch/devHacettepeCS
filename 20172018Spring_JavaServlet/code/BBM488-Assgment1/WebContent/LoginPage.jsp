<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<% 	// Let's check if user is already logged in !
	if(session.getAttribute("isLoggedIn") != null && session.getAttribute("isLoggedIn").equals("true")){
		response.sendRedirect("RecordPages/RecordEdit.jsp");
	}
%>
<title>LogIn</title>
</head>
<body>
	<div style="margin-left: 35%; margin-right: 40%">
		<h2>To Login to the system, enter your username and password, then submit!</h2>
		<form action="UserController/LogIn" method="post">
			<input name="userName" value="Username">
			<input name="password" type="password" value="Password">
			<input type="submit" value="Login">
		</form>
		<h2>
			<%
				if(session.getAttribute("loginInfo")!= null && (boolean)session.getAttribute("loginInfo").equals("true")){
					out.print(session.getAttribute("LoginPageInfo"));
				}
			%>
		</h2>
	</div>
</body>
</html>