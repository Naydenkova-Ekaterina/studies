<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>

<%--
  Created by IntelliJ IDEA.
  User: ekaterina
  Date: 05.10.2019
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit cargo</title>
</head>
<body>

<h1>Edit cargo</h1>

<c:url var="addAction" value="/cargo/update" ></c:url>
<%--
<form:form action="${addAction}" modelAttribute="cargo" id="form">
    <table >
        <tr>
            <td></td>
            <td><form:hidden  path="id" /></td>
        </tr>
        <tr>
            <td>Name : </td>
            <td><form:input path="name"  /></td>
        </tr>
        <tr>
            <td>Weight :</td>
            <td><form:input path="weight" /></td>
        </tr>
        <tr>
            <td>Status :</td>
            <td>
                <form:select path="status" name="status" form="form">
                    <form:option value="prepared">Prepared</form:option>
                    <form:option value="shipped">Shipped</form:option>
                    <form:option value="delivered">Delivered</form:option>
                </form:select>
            </td>
        </tr>

        <tr>
            <td> </td>
            <td><input type="submit" value="Edit Save" /></td>
        </tr>
    </table>
</form:form>
--%>

<form:form action="${addAction}" modelAttribute="cargo" id="form">
    <table>
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
            <td> </td>
            <td><input type="submit" value="Edit Save" /></td>
        </tr>

    </table>
</form:form>


</body>
</html>
