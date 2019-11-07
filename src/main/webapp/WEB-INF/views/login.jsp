<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/react/15.3.1/react-with-addons.min.js"></script>
    <script src="https://npmcdn.com/react-dom@15.3.0/dist/react-dom.min.js"></script>
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/qs/6.9.0/qs.js"></script>


    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

    <style>
        <%@include file='/resources/css/headerStyle.css' %>

        <%@include file='/resources/css/main.css' %>

    </style>

</head>
<body>


<header class="myheader">
    <h1 class="logo"><a id="logoId" class="headerNav" href="${pageContext.request.contextPath}/">Home</a></h1>
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

<div id="app" ></div>


<%--<c:url var="addAction" value="/loginAction" ></c:url>--%>
<%--<form:form action="${addAction}"  >--%>
<%--    <input type="text" name="email">--%>
<%--    <input type="text" name="password">--%>
<%--    <input type="submit">--%>
<%--</form:form>--%>


<script type="text/babel" src="<c:url value="/resources/js/login.js" />"></script>

</body>
</html>
