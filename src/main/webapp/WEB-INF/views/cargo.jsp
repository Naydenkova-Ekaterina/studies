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
                    <h2><b>Cargos</b></h2>
                </div>
                <div class="col-sm-6">
                    <a href="#addCargoModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Cargo</span></a>
                    <a href="#deleteCargoModal" class="btn btn-danger" data-toggle="modal"><i class="material-icons">&#xE15C;</i> <span>Delete</span></a>
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
                <th>Weight</th>
                <th>Status</th>
                <th>Source</th>
                <th>Destination</th>
                <th>Order</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listCargoes}" var="cargo">
                <tr>
                    <td>
							<span class="custom-checkbox">
								<input type="checkbox" id="checkbox1" name="options[]" value="1">
								<label for="checkbox1"></label>
							</span>
                    </td>
                    <td>${cargo.id}</td>
                    <td>${cargo.name}</td>
                    <td>${cargo.weight}</td>
                    <td>${cargo.status}</td>
                    <td>${cargo.src.city.name}</td>
                    <td>${cargo.dst.city.name}</td>
                    <td>${cargo.orderDTO_id}</td>
                    <td>
                        <a href="#editCargoModal" class="edit" data-toggle="modal" onclick="setIdForUpdate('${cargo.id}')"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                        <a href="#deleteCargoModal" class="delete" data-toggle="modal" onclick="setIdForRemove('${cargo.id}')"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<!-- Add Modal HTML -->
<div id="addCargoModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/cargo/add" ></c:url>
            <form:form action="${addAction}" modelAttribute="cargo"  >
                <div class="modal-header">
                    <h4 class="modal-title">Add Cargo</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label>Name</label>
                        <form:input path="name" type="text" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <label>Weight</label>
                        <form:input path="weight" type="text" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <form:select path="status" name="status" class="form-control">
                            <option value="prepared">Prepared</option>
                            <option value="shipped">Shipped</option>
                            <option value="delivered">Delivered</option>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label>Source</label>
                        <form:select path="src_id" id="options" name="city" class="form-control">
                            <c:forEach items="${waypoints}" var="waypoint">
                                <form:option value="${waypoint.id}">
                                    <c:out value="${waypoint.city.name}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="form-group">
                        <label>Destination</label>
                        <form:select path="dst_id" id="options" name="city" class="form-control">
                            <c:forEach items="${waypoints}" var="waypoint">
                                <form:option value="${waypoint.id}">
                                    <c:out value="${waypoint.city.name}" />
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
<div id="editCargoModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/cargo/update" ></c:url>
            <form:form action="${addAction}" modelAttribute="cargo" id="formUpdate"  >
                <div class="modal-header">
                    <h4 class="modal-title">Edit Cargo</h4>
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
                        <label>Weight</label>
                        <form:input path="weight" type="text" class="form-control" id="UpdCap"/>
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <form:select path="status" name="status" class="form-control" id="UpdStat">
                            <option value="prepared">Prepared</option>
                            <option value="shipped">Shipped</option>
                            <option value="delivered">Delivered</option>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label>Source</label>
                        <form:select path="src_id" name="city" class="form-control" id="UpdSrc">
                            <c:forEach items="${waypoints}" var="waypoint">
                                <form:option value="${waypoint.id}">
                                    <c:out value="${waypoint.city.name}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="form-group">
                        <label>Destination</label>
                        <form:select path="dst_id" name="city" class="form-control" id="UpdDst">
                            <c:forEach items="${waypoints}" var="waypoint">
                                <form:option value="${waypoint.id}">
                                    <c:out value="${waypoint.city.name}" />
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
<div id="deleteCargoModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/cargo/remove/" ></c:url>

            <form:form action="${addAction}"  id="formRemove" >
                <div class="modal-header">
                    <h4 class="modal-title">Delete Cargo</h4>
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
<script src="<c:url value="/resources/cargo.js" />"></script>

</body>

</html>