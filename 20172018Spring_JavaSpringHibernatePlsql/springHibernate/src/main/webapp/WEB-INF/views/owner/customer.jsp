<%@ page import="com.can.model.Customer" %>
<%@ page import="java.util.ArrayList" %>
<%--
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
    ArrayList<Customer> customers = (ArrayList<Customer>) request.getAttribute("customers");
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
        <h2 class="text-center">Owner - Customer Management</h2>
    </div>
    <div class="row" style="margin-top: 10%">
        <div class="col">
            <h4 class="text-center text-info">Customers</h4>
            <table class="table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Floor</th>
                    <th>Building</th>
                    <th>Room</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if(customers != null) {
                        for(Customer customer: customers) {
                            out.print("<tr class=\"table-dark text-dark\">" +
                                    "<td>"+customer.getId()+"</td>" +
                                    "<td>"+customer.getName()+"</td>" +
                                    "<td>"+customer.getSurname()+"</td>" +
                                    "<td>"+customer.getFloor()+"</td>" +
                                    "<td>"+customer.getBuilding()+"</td>" +
                                    "<td>"+customer.getRoom()+"</td>" +
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
            <form action="/owner/customer/add" method="post">
                <div class="form-group">
                    <label for="addNewCustomerName">Name</label>
                    <input type="username" class="form-control" id="addNewCustomerName" placeholder="Enter customer name" name="addNewCustomerName">
                    <label for="addNewCustomerSurname">Surname</label>
                    <input type="username" class="form-control" id="addNewCustomerSurname" placeholder="Enter customer surname" name="addNewCustomerSurname">
                </div>
                <div class="form-group">
                    <label for="addNewCustomerUsername">Username</label>
                    <input type="username" class="form-control" id="addNewCustomerUsername" placeholder="Enter customer username" name="addNewCustomerUsername">
                    <label for="addNewCustomerPassword">Password</label>
                    <input type="password" class="form-control" id="addNewCustomerPassword" placeholder="Enter custoemr password" name="addNewCustomerPassword">
                </div>
                <div class="form-group">
                    <label for="addNewCustomerFloor">Floor</label>
                    <input type="text" class="form-control" id="addNewCustomerFloor" placeholder="Enter customer floor" name="addNewCustomerFloor">
                    <label for="addNewCustomerBuilding">Building</label>
                    <input type="text" class="form-control" id="addNewCustomerBuilding" placeholder="Enter customer building" name="addNewCustomerBuilding">
                    <label for="addNewCustomerRoom">Room</label>
                    <input type="text" class="form-control" id="addNewCustomerRoom" placeholder="Enter customer room" name="addNewCustomerRoom">
                </div>
                <button type="submit" class="btn btn-primary">Add</button>
                <br>
                <h6 class="text-danger">${newCustomerAddedMessage}</h6>
            </form>
        </div>
        <div class="col-sm-6">
            <h4 class="text-center text-info">Update</h4>
            <form action="/owner/customer/update" method="post">
                <div class="form-group">
                    <label for="updateCustomerID">ID</label>
                    <input type="username" class="form-control" id="updateCustomerID" placeholder="Enter new name" name="updateCustomerID">
                    <label for="updateCustomerName">Name</label>
                    <input type="username" class="form-control" id="updateCustomerName" placeholder="Enter new name" name="updateCustomerName">
                    <label for="updateCustomerSurname">Surname</label>
                    <input type="username" class="form-control" id="updateCustomerSurname" placeholder="Enter new surname" name="updateCustomerSurname">
                </div>
                <div class="form-group">
                    <label for="updateCustomerFloor">Floor</label>
                    <input type="text" class="form-control" id="updateCustomerFloor" placeholder="Enter new floor" name="updateCustomerFloor">
                    <label for="updateCustomerFloor">Building</label>
                    <input type="text" class="form-control" id="updateCustomerBuilding" placeholder="Enter new building" name="updateCustomerBuilding">
                    <label for="updateCustomerRoom">Room</label>
                    <input type="text" class="form-control" id="updateCustomerRoom" placeholder="Enter new room" name="updateCustomerRoom">
                </div>
                <button type="submit" class="btn btn-primary">Update</button>
                <br>
                <h6 class="text-danger">${customerUpdatedMessage}</h6>
            </form>
        </div>
    </div>
</div>
</body>
</html>
