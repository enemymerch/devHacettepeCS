<%--
  Created by IntelliJ IDEA.
  User: Can
  Date: 7.04.2018
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <a class="nav-link" href="/index">Home</a>
        </li>
    </ul>
</nav>

<div class="container">
    <div class="col">
        <h2 class="text-center">Welcome</h2>
    </div>
    <div class="row" style="margin-top: 10%">
        <div class=" col-sm-6">
            <h4 class="text-center text-info">Login as Owner</h4>
            <form action="/owner/login" method="post">
                <div class="form-group">
                    <label for="ownerUsername">Username:</label>
                    <input type="username" class="form-control" id="ownerUsername" placeholder="Enter username" name="ownerUsername">
                </div>
                <div class="form-group">
                    <label for="ownerPassword">Password:</label>
                    <input type="password" class="form-control" id="ownerPassword" placeholder="Enter password" name="ownerPassword">
                </div>
                <div class="form-check">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox" name="remember"> Remember me
                    </label>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
                <br>
                <h6 class="text-danger">${ownerLoginMessage}</h6>
            </form>
        </div>
        <div class=" col-sm-6">
            <h4 class="text-center text-info">Login as Customer</h4>
            <form action="/customer/login" method="post">
                <div class="form-group">
                    <label for="customerUsername">Username:</label>
                    <input type="username" class="form-control" id="customerUsername" placeholder="Enter username" name="customerUsername">
                </div>
                <div class="form-group">
                    <label for="customerPassword">Password:</label>
                    <input type="password" class="form-control" id="customerPassword" placeholder="Enter password" name="customerPassword">
                </div>
                <div class="form-check">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox" name="remember"> Remember me
                    </label>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
                <h6 class="text-danger">${customerLoginMessage}</h6>
            </form>
        </div>
    </div>
</div>
</body>
</html>
