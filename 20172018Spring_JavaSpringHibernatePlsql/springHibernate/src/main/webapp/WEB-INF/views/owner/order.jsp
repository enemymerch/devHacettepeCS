<%@ page import="com.can.model.Orders" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.sun.org.apache.xpath.internal.operations.Or" %>
<%@ page import="com.can.model.Customer" %>
<%@ page import="com.can.model.Product" %><%--
  Created by IntelliJ IDEA.
  User: Can
  Date: 7.04.2018
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
    <%
    String isLoggedIn = (String) session.getAttribute("isOwnerLoggedIn");
    if (!(isLoggedIn != null && isLoggedIn.equals("true"))){
        response.sendRedirect("/index");
    }
    String username = (String) session.getAttribute("ownerUsername");
    ArrayList<Orders> orders = (ArrayList<Orders>) request.getAttribute("orders");
    ArrayList<Customer> customers = (ArrayList<Customer>) request.getAttribute("customers");
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
        <h2 class="text-center">Owner - Order Management</h2>
    </div>
    <div class="row" style="margin-top: 10%">
        <div class="col">
            <h4 class="text-center text-info">Orders</h4>
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Product</th>
                    <th>Piece</th>
                    <th>Status</th>
                    <th>Customer</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if(orders != null) {
                        for(Orders order: orders) {
                            out.print("<tr class=\"table-dark text-dark\">" +
                                    "<td>"+order.getId()+"</td>" +
                                    "<td>"+order.getProduct()+"</td>" +
                                    "<td>"+order.getPiece()+"</td>" +
                                    "<td>"+order.getStatus()+"</td>" +
                                    "<td>"+order.getUsername()+"</td>" +
                                    "</tr>");
                        }
                    } else {
                        out.print("No data");
                    }
                %>
                </tbody>
            </table>

        </div>
    </div>
    <div class="row" style="margin-top: 10%">
        <div class="col-sm-6">
            <h4 class="text-center text-info">Add</h4>
            <form action="/owner/order/add" method="post">
                <div class="form-group">
                    <label for="addOrderCustomerUsername">Customer</label>
                    <select class="form-control" id="addOrderCustomerUsername" name="addOrderCustomerUsername">
                        <%
                            if(customers != null) {
                                for(Customer customer: customers) {
                                    out.print("<option>" +
                                            customer.getUsername() +
                                            "</option>");
                                }
                            }
                        %>
                    </select>
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
        <div class="col-sm-6">
            <h4 class="text-center text-info">Update</h4>
            <form action="/owner/order/update" method="post">
                <div class="form-group">
                    <label for="updateOrderID">ID</label>
                    <input type="text" class="form-control" id="updateOrderID" placeholder="Enter ID" name="updateOrderID">
                    <label for="updateOrderStatus">Status</label>
                    <select class="form-control" id="updateOrderStatus" name="updateOrderStatus">
                        <option>Preparing order</option>
                        <option>Order is on the way</option>
                        <option>Order delivered</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Update</button>
                <br>
                <h6 class="text-danger">${orderUpdatedMessage}</h6>
            </form>
        </div>
    </div>
</div>
</body>
</html>
