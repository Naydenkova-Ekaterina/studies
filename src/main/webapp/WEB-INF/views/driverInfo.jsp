<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Driver Info</title>
</head>
<body>
<form:form modelAttribute="selDriverInfo"  >
    <div class="modal-header">
        <h4 class="modal-title">Add Wagon</h4>
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    </div>
    <div class="modal-body">
        <div class="form-group">

            <label>Register number</label>
            <form:input path="personalNumber" value="${selDriverInfo.personalNumber}" type="text" readonly="true" class="form-control" />
        </div>
        <div class="form-group">
            <label>Wagon register number</label>
            <form:input path="wagon_id" value="${selDriverInfo.wagon_id}" type="text" class="form-control"/>
        </div>
        <div class="form-group">
            <label>Order id</label>
            <form:input path="order_id" value="${selDriverInfo.order_id}" type="text" class="form-control"/>
        </div>
        <div class="form-group">
            <label>Co-drivers</label>
            <select multiple>
            <c:forEach items="${selDriverInfo.codrivers}" var="codriver">
                <option value="${codriver}">
                    <c:out value="${codriver}" />
                </option>
            </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Waypoints</label>
            <select multiple>
                <c:forEach items="${selDriverInfo.waypoints}" var="waypoint">
                    <option value="${waypoint.id}">
                        <c:out value="${waypoint.id}" />
                    </option>
                </c:forEach>
            </select>
        </div>

    </div>

</form:form>
</body>
</html>
