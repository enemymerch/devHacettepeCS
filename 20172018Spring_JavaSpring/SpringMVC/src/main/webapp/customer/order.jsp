<%@ page import="com.can.model.Order" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.can.model.Product" %><%--
  Created by IntelliJ IDEA.
  User: Can
  Date: 7.04.2018
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%
    String isLoggedIn = (String) session.getAttribute("isCustomerLoggedIn");
    if (!(isLoggedIn != null && isLoggedIn.equals("true"))){
        response.sendRedirect("/index");
    }
    String username = (String) session.getAttribute("customerUsername");
    ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
    ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");

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
            <a class="nav-link" href="/customer/dashboard">Home</a>
        </li>
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                <%
                    out.print(username);
                %>
            </a>
            <div class="dropdown-menu">
                <a class="dropdown-item" href="/customer/profile">Profile</a>
                <a class="dropdown-item" href="/logout">Logout</a>
            </div>
        </li>
    </ul>
</nav>

<div class="container">
    <div class="col" style="margin-top: 5%">
        <h2 class="text-center">Customer - Orders</h2>
    </div>
    <div class="row" style="margin-top: 10%">
        <div class="col-sm-6">
            <h4 class="text-center text-info">Orders</h4>
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Product</th>
                    <th>Piece</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if(orders != null) {
                        for(Order order: orders) {
                            out.print("<tr class=\"table-dark text-dark\">" +
                                    "<td>"+order.getId()+"</td>" +
                                    "<td>"+order.getProduct()+"</td>" +
                                    "<td>"+order.getPiece()+"</td>" +
                                    "<td>"+order.getStatus()+"</td>" +
                                    "</tr>");
                        }
                    } else {
                        out.print("No data");
                    }
                %>
                </tbody>
            </table>
        </div>
        <div class="col-sm-6">
            <div class="col-sm-6">
                <h4 class="text-center text-info">Add</h4>
                <form action="/customer/order/add" method="post">
                    <div class="form-group">
                        <label for="addOrderProductName">Product</label>
                        <select class="form-control" id="addOrderProductName" name="addOrderProductName">
                            <%
                                if(products!= null) {
                                    for(Product product: products) {
                                        out.print("<option>" +
                                                product.getName() +
                                                "</option>");
                                    }
                                }
                            %>
                        </select></div>
                    <div class="form-group">
                        <label for="addOrderPiece">Piece</label>
                        <input type="text" class="form-control" id="addOrderPiece" placeholder="1" name="addOrderPiece">
                    </div>
                    <button type="submit" class="btn btn-primary">Add</button>
                    <br>
                    <h6 class="text-danger">${newOrderAddedMessage}</h6>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
