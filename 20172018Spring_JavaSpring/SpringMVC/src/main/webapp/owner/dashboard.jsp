<%--
  Created by IntelliJ IDEA.
  User: Can
  Date: 7.04.2018
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%
    String isLoggedIn = (String) session.getAttribute("isOwnerLoggedIn");
    if (!(isLoggedIn != null && isLoggedIn.equals("true"))){
        response.sendRedirect("/index");
    }
    String username = (String) session.getAttribute("ownerUsername");

%>

<html>
<head>
    <title>Index</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <script src="/resources/js/jquerry.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-sm bg-success navbar-dark">
    <ul class="navbar-nav">
        <li class="nav-item active">
            <a class="nav-link" href="/owner/dashboard">Home</a>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                <%
                    out.print(username);
                %>
            </a>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="/logout">Logout</a>
            </div>
        </li>
    </ul>
</nav>

<div class="container">
    <div class="col" style="margin-top: 5%">
        <h2 class="text-center">Welcome</h2>
        <h4 class="text-center">Owner Dashboard</h4>
    </div>
    <div class="row" style="margin-top: 10%">
        <div class=" col-sm-4">
            <h4 class="text-center text-info">Product</h4>
            <h6 class="text-center" style="margin-top: 10%">
                <a href="/owner/product">Manage products</a>
            </h6>
        </div>

        <div class=" col-sm-4">
            <h4 class="text-center text-info">Customer</h4>
            <h6 class="text-center" style="margin-top: 10%">
                <a class="text-lg-center" style="text-align: center;" href="/owner/customer">Manage customers</a>
            </h6>
        </div>

        <div class=" col-sm-4">
            <h4 class="text-center text-info">Orders</h4>
            <h6 class="text-center" style="margin-top: 10%">
                <a class="text-lg-center" style="text-align: center;" href="/owner/order">Manage orders</a>
            </h6>
        </div>
    </div>
</div>
</body>
</html>
