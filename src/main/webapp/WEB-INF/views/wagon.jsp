<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Wagon Page</title>
    <style>
        <%@include file='/resources/css/headerStyle.css' %>

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



    <div class="container">    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2><b>Wagons</b></h2>
                </div>
                <div class="col-sm-6">
                    <a href="#addWagonModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Wagon</span></a>
                </div>
            </div>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>
							<span class="custom-checkbox">
							</span>
                </th>
                <th>Register number</th>
                <th>Shift size</th>
                <th>Capacity</th>
                <th>Status</th>
                <th>City</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${listWagons}" var="wagon">
            <tr>
                <td>
							<span class="custom-checkbox">
							</span>
                </td>
                <td>${wagon.id}</td>
                <td>${wagon.shiftSize}</td>
                <td>${wagon.capacity}</td>
                <td>${wagon.status}</td>
                <td>${wagon.city.name}</td>
                <td>
                    <a href="#editWagonModal" class="edit" data-toggle="modal" onclick="setIdForUpdate('${wagon.id}')"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                    <a href="#deleteWagonModal" class="delete" data-toggle="modal" onclick="setIdForRemove('${wagon.id}')"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<!-- Add Modal HTML -->
<div id="addWagonModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/wagon/add" ></c:url>
            <form:form action="${addAction}" modelAttribute="wagon"  >
                <div class="modal-header">
                    <h4 class="modal-title">Add Wagon</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">

                        <label>Register number</label>
                        <form:input path="id" type="text" pattern="[a-zA-Z]{2}[0-9]{5}" placeholder="2 letters 5 numbers - ab12345" required="required" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Shift size</label>
                        <form:input path="shiftSize" type="text" pattern="[0-9]{2}:[0-9]{2}" placeholder="09:30"  required="required" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label>Capacity</label>
                        <form:input path="capacity" type="text" pattern="[0-9]+\.?[0-9]+" placeholder="1000" required="required" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <form:select path="status" name="status" class="form-control">
                            <option value="serviceable">Serviceable</option>
                            <option value="faulty">Faulty</option>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label>City</label>
                        <form:select path="city_id" id="options" name="status" class="form-control">
                            <c:forEach items="${cities}" var="order">
                                <form:option value="${order.id}">
                                    <c:out value="${order.name}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-success" value="Add">
                </div>
            </form:form>
        </div>
    </div>
</div>
<!-- Edit Modal HTML -->
<div id="editWagonModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/wagon/update" ></c:url>
            <form:form action="${addAction}" modelAttribute="wagon" id="formUpdate"  >
            <div class="modal-header">
                    <h4 class="modal-title">Edit Wagon</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Register number</label>
                        <form:input path="id" type="text" class="form-control" readonly="true" id="UpdReg"/>
                    </div>
                    <div class="form-group">
                        <label>Shift size</label>
                        <form:input path="shiftSize" type="text" class="form-control" pattern="[0-9]{2}:[0-9]{2}" placeholder="09:30"  required="required" id="UpdShift"/>
                    </div>
                    <div class="form-group">
                        <label>Capacity</label>
                        <form:input path="capacity" type="text" class="form-control" pattern="[0-9]+\.?[0-9]+" placeholder="1000" required="required" id="UpdCap"/>
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <form:select path="status" name="status" class="form-control" id="UpdStat">
                            <option value="serviceable">Serviceable</option>
                            <option value="faulty">Faulty</option>
                        </form:select>                    </div>
                    <div class="form-group">
                        <label>City</label>
                        <form:select path="city_id" id="UpdCity" class="form-control" >
                            <c:forEach items="${cities}" var="order">
                                <form:option value="${order.id}">
                                    <c:out value="${order.name}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-info" value="Save">
                </div>
            </form:form>
        </div>
    </div>
</div>
<!-- Delete Modal HTML -->
<div id="deleteWagonModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/wagon/remove/" ></c:url>

            <form:form action="${addAction}"  id="formRemove" >
            <div class="modal-header">
                    <h4 class="modal-title">Delete Wagon</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete these Records?</p>
                    <p class="text-warning"><small>This action cannot be undone.</small></p>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-danger" value="Delete">
                </div>
            </form:form>
        </div>
    </div>
</div>
</div>
<script src="<c:url value="/resources/wagon.js" />"></script>

</body>

</html>