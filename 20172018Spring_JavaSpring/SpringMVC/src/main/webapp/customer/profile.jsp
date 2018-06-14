<%@ page import="com.can.model.Order" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.can.model.Product" %>
<%@ page import="com.can.model.Customer" %>
<%@ page import="com.can.model.Address" %><%--
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
    Customer profile = (Customer) session.getAttribute("profile");
    Address address = profile.getAddress();
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
        <h2 class="text-center">Customer - Profile</h2>
    </div>

    <div class="row" style="margin-top: 10%">
        <div class="col-4">
            <h4 class="text-center text-info">Profile</h4>
            <h6 class="text-info">Name: <strong><% out.print(profile.getName());%></strong></h6>
            <h6 class="text-info">Surname: <strong><% out.print(profile.getSurname());%></strong></h6>
            <h6 class="text-info">Floor: <strong><% out.print(address.getFloor());%></strong></h6>
            <h6 class="text-info">Building: <strong><% out.print(address.getBuilding());%></strong></h6>
            <h6 class="text-info">Room: <strong><% out.print(address.getRoom());%></strong></h6>
        </div>
        <div class="col-4">
            <h4 class="text-center text-info">Update Profile</h4>
            <form action="/customer/profile/update" method="post">
                <div class="form-group">
                    <label for="updateCustomerProfileID" style="display: none">ID</label>
                    <input type="username" class="form-control" style="display: none;" id="updateCustomerProfileID" value="<%out.print(profile.getId());%>" name="updateCustomerID">
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
        <div class="col-4">
            <h4 class="text-center text-info">Update Password</h4>
            <form action="/customer/profile/updatePassword" method="post">
                <div class="form-group">
                    <label for="updateCustomerPasswordID" style="display: none">ID</label>
                    <input type="username" class="form-control" style="display: none;" id="updateCustomerPasswordID" value="<%profile.getId();%>"  placeholder="Enter new name" name="updateCustomerID">
                    <label for="updateCustomerOldPassword">Old Password</label>
                    <input type="password" class="form-control" id="updateCustomerOldPassword" placeholder="Enter old password" name="updateCustomerOldPassword">
                    <label for="updateCustomerNewPassword">New password</label>
                    <input type="password" class="form-control" id="updateCustomerNewPassword" placeholder="Enter new password" name="updateCustomerNewPassword">
                    <label for="updateCustomerNewPasswordAgain">New password again</label>
                    <input type="password" class="form-control" id="updateCustomerNewPasswordAgain" placeholder="Enter new password again" name="updateCustomerNewPasswordAgain">

                </div>
                <button type="submit" class="btn btn-primary">Update Password</button>
                <br>
                <h6 class="text-danger">${customerUpdatedPasswordMessage}</h6>
            </form>
        </div>
    </div>
</div>
</body>
</html>
