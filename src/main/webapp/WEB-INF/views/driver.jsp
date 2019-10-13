<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Driver Page</title>
    <style>
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

<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2><b>Drivers</b></h2>
                </div>
                <div class="col-sm-6">
                    <a href="#addDriverModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Driver</span></a>
                    <a href="#deleteDriverModal" class="btn btn-danger" data-toggle="modal"><i class="material-icons">&#xE15C;</i> <span>Delete</span></a>
                </div>
            </div>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>
							<span class="custom-checkbox">
								<input type="checkbox" id="selectAll">
								<label for="selectAll"></label>
							</span>
                </th>
                <th>Id</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Status</th>
                <th>City</th>
                <th>Wagon</th>
                <th>Order</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listDrivers}" var="driver">
                <tr>
                    <td>
							<span class="custom-checkbox">
								<input type="checkbox" id="checkbox1" name="options[]" value="1">
								<label for="checkbox1"></label>
							</span>
                    </td>
                    <td>${driver.id}</td>
                    <td>${driver.name}</td>
                    <td>${driver.surname}</td>
                    <td>${driver.status}</td>
                    <td>${driver.city.name}</td>
                    <td>${driver.wagon.id}</td>
                    <td>${driver.order.id}</td>
                    <td>
                        <a href="#editDriverModal" class="edit" data-toggle="modal" onclick="setIdForUpdate('${driver.id}')"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                        <a href="#deleteDriverModal" class="delete" data-toggle="modal" onclick="setIdForRemove('${driver.id}')"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<!-- Add Modal HTML -->
<div id="addDriverModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/driver/add" ></c:url>
            <form:form action="${addAction}" modelAttribute="driver"  >
                <div class="modal-header">
                    <h4 class="modal-title">Add Driver</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label>Name</label>
                        <form:input path="name" type="text" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label>Surname</label>
                        <form:input path="surname" type="text" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <form:select path="status" name="status" class="form-control">
                            <option value="rest">Rest</option>
                            <option value="shift">Shift</option>
                            <option value="behindTheWheel">Behind the wheel</option>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label>City</label>
                        <form:select path="city_id" id="options" name="city" class="form-control">
                            <c:forEach items="${cities}" var="city">
                                <form:option value="${city.id}">
                                    <c:out value="${city.name}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="form-group">
                        <label>Wagon</label>
                        <form:select path="wagon_id" name="wagon" class="form-control">
                            <c:forEach items="${wagons}" var="wagon">
                                <form:option value="${wagon.id}">
                                    <c:out value="${wagon.id}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="form-group">
                        <label>Order</label>
                        <form:select path="order_id" name="order" class="form-control">
                            <c:forEach items="${orders}" var="order">
                                <form:option value="${order.id}">
                                    <c:out value="${order.id}" />
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
<div id="editDriverModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/driver/update" ></c:url>
            <form:form action="${addAction}" modelAttribute="driver" id="formUpdate"  >
                <div class="modal-header">
                    <h4 class="modal-title">Edit Driver</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Id</label>
                        <form:input path="id" type="text" class="form-control" readonly="true" id="UpdReg"/>
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                        <form:input path="name" type="text" class="form-control" id="UpdShift"/>
                    </div>
                    <div class="form-group">
                        <label>Surname</label>
                        <form:input path="surname" type="text" class="form-control" id="UpdCap"/>
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <form:select path="status" name="status" class="form-control" id="UpdStat">
                            <option value="rest">Rest</option>
                            <option value="shift">Shift</option>
                            <option value="behindTheWheel">Behind the wheel</option>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label>City</label>
                        <form:select path="city_id" id="UpdCity" class="form-control" >
                            <c:forEach items="${cities}" var="city">
                                <form:option value="${city.id}">
                                    <c:out value="${city.name}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label>Wagon</label>
                        <form:select path="wagon_id" id="UpdWagon" name="wagon" class="form-control">
                            <c:forEach items="${wagons}" var="wagon">
                                <form:option value="${wagon.id}">
                                    <c:out value="${wagon.id}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="form-group">
                        <label>Order</label>
                        <form:select path="order_id" id="UpdOrder" name="order" class="form-control">
                            <c:forEach items="${orders}" var="order">
                                <form:option value="${order.id}">
                                    <c:out value="${order.id}" />
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
<div id="deleteDriverModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/driver/remove/" ></c:url>

            <form:form action="${addAction}"  id="formRemove" >
                <div class="modal-header">
                    <h4 class="modal-title">Delete Driver</h4>
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
<script src="<c:url value="/resources/driver.js" />"></script>

</body>

</html>