<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Wagon Page</title>
    <style>
        <%@include file='/resources/style.css' %>
    </style>
</head>
<body>
<h1>
    Add a Wagon
</h1>

<c:url var="addAction" value="/wagon/add" ></c:url>

<form:form action="${addAction}" modelAttribute="wagon" id="form">
    <table>
        <c:if test="${!empty wagon.shiftSize}">
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
                <form:label path="shiftSize">
                    <spring:message text="shiftSize"/>
                </form:label>
            </td>
            <td>
                <form:input path="shiftSize" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="capacity">
                    <spring:message text="capacity"/>
                </form:label>
            </td>
            <td>
                <form:input path="capacity" />
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
                    <form:option value="serviceable">serviceable</form:option>
                    <form:option value="faulty">faulty</form:option>
                </form:select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${!empty wagon.shiftSize}">
                    <input type="submit"
                           value="<spring:message text="Edit Wagon"/>" />
                </c:if>
                <c:if test="${empty wagon.shiftSize}">
                    <input type="submit"
                           value="<spring:message text="Add Wagon"/>" />
                </c:if>
            </td>
        </tr>
    </table>
</form:form>

<br>
<h3>Wagons List</h3>
<c:if test="${!empty listWagons}">
    <table class="tg">
        <tr>
            <th width="80">Wagon ID</th>
            <th width="120">Wagon Name</th>
            <th width="120">Wagon Weight</th>
            <th width="120">Wagon Status</th>

            <th width="60">Edit</th>
            <th width="60">Delete</th>
        </tr>
        <c:forEach items="${listWagons}" var="wagon">
            <tr>
                <td>${wagon.id}</td>
                <td>${wagon.shiftSize}</td>
                <td>${wagon.capacity}</td>
                <td>${wagon.status}</td>
                <td><a href="<c:url value='/wagon/edit/${wagon.id}' />">Edit</a></td>
                <td><a href="<c:url value='/wagon/remove/${wagon.id}' />" >Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>