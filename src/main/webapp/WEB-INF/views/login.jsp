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



    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>


<%--    <style>--%>
<%--        <%@include file='/resources/css/login.css' %>--%>
<%--    </style>--%>

</head>
<body>

<div id="app"></div>


<%--<c:url var="addAction" value="/loginAction" ></c:url>--%>
<%--<form:form action="${addAction}"  >--%>
<%--    <input type="text" name="email">--%>
<%--    <input type="text" name="password">--%>
<%--    <input type="submit">--%>
<%--</form:form>--%>

<script type="text/babel" src="<c:url value="/resources/js/login.js" />"></script>

</body>
</html>
