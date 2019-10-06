<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Cargo Page</title>
    <style>
        <%@include file='/resources/style.css' %>
    </style>
</head>
<body>
<h1>
    Add a Cargo
</h1>

<c:url var="addAction" value="/cargo/add" ></c:url>

<form:form action="${addAction}" modelAttribute="cargo" id="form">
    <table>
        <c:if test="${!empty cargo.name}">
            <tr>
                <td>
                    <form:label path="id">
                        <spring:message text="ID"/>
                    </form:label>
                </td>
                <td>
                    <form:input path="id" readonly="true" size="8"  disabled="true" />
                    <form:hidden path="id" />
                </td>
            </tr>
        </c:if>
        <tr>
            <td>
                <form:label path="name">
                    <spring:message text="Name"/>
                </form:label>
            </td>
            <td>
                <form:input path="name" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="weight">
                    <spring:message text="Weight"/>
                </form:label>
            </td>
            <td>
                <form:input path="weight" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="status">
                    <spring:message text="Status"/>
                </form:label>
            </td>
            <td>
                <form:select path="status" name="status" form="form">
                    <form:option value="prepared">Prepared</form:option>
                    <form:option value="shipped">Shipped</form:option>
                    <form:option value="delivered">Delivered</form:option>
                </form:select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${!empty cargo.name}">
                    <input type="submit"
                           value="<spring:message text="Edit Cargo"/>" />
                </c:if>
                <c:if test="${empty cargo.name}">
                    <input type="submit"
                           value="<spring:message text="Add Cargo"/>" />
                </c:if>
            </td>
        </tr>
    </table>
</form:form>

<br>
<h3>Cargoes List</h3>
<c:if test="${!empty listCargoes}">
    <table class="tg">
        <tr>
            <th width="80">Cargo ID</th>
            <th width="120">Cargo Name</th>
            <th width="120">Cargo Weight</th>
            <th width="120">Cargo Status</th>

            <th width="60">Edit</th>
            <th width="60">Delete</th>
        </tr>
        <c:forEach items="${listCargoes}" var="cargo">
            <tr>
                <td>${cargo.id}</td>
                <td>${cargo.name}</td>
                <td>${cargo.weight}</td>
                <td>${cargo.status}</td>
                <td><a href="<c:url value='/edit/${cargo.id}' />">Edit</a></td>
                <td><a href="<c:url value='/remove/${cargo.id}' />" >Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>