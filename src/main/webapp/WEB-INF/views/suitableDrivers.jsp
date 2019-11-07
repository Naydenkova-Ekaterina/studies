<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Suitable drivers</title>

    <style>
        <%@include file='/resources/css/headerStyle.css' %>
        <%@include file='/resources/css/buttonStyle.css' %>

        <%@include file='/resources/wagonStyle.css' %>
    </style>

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>



<header class="myheader">
    <h1 class="logo"><a class="headerNav" href="${pageContext.request.contextPath}/">Home</a></h1>
    <input type="checkbox" id="nav-toggle" class="nav-toggle">
    <nav class="headerNav">
        <ul class="headerUl">
            <li><a class="headerLink" href="${pageContext.request.contextPath}/wagons">Wagons</a></li>
            <li><a class="headerLink" href="${pageContext.request.contextPath}/drivers">Drivers</a></li>
            <li><a class="headerLink" href="${pageContext.request.contextPath}/cargoes">Cargoes</a></li>
            <li><a class="headerLink" href="${pageContext.request.contextPath}/orders" class="submenu-link">Orders</a>
                <ul class="submenu">
                    <li><a href="${pageContext.request.contextPath}/suitableWagons">Set wagon</a></li>
                    <li><a href="${pageContext.request.contextPath}/suitableDrivers">Set drivers</a></li>
                </ul>
            </li>
            <li><a class="headerLink" href="${pageContext.request.contextPath}/driver/info/">Driver Info</a></li>
        </ul>
    </nav>
    <label for="nav-toggle" class="nav-toggle-label">
        <span></span>
    </label>
</header>

<div class="content">


    <div class="container">

        <form>

            <select id="orderSelect" class="select">
            </select>
            <div class="button-2">
                <div class="eff-2"></div>
                <a href="#" onclick="getSuitableDrivers()">Find suitable drivers</a>
            </div>
        </form>

        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-6">
                        <h2><b>Drivers</b></h2>
                    </div>
                </div>
            </div>
            <table class="table table-striped table-hover" id="records_table">
                <thead>
                <tr>
                    <th>
							<span class="custom-checkbox">
							</span>
                    </th>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Status</th>
                    <th>City</th>
<%--                    <th>Wagon</th>--%>
<%--                    <th>Order</th>--%>
<%--                    <th>User</th>--%>

                </tr>
                </thead>

            </table>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/suitableDrivers.js" />"></script>


</body>
</html>
