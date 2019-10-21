<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Order Page</title>
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
                    <h2><b>Orders</b></h2>
                </div>
                <div class="col-sm-6">
                    <a href="#addOrderModal" onclick="addOrder()" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Order</span></a>
                    <a href="#deleteOrderModal" class="btn btn-danger" data-toggle="modal"><i class="material-icons">&#xE15C;</i> <span>Delete</span></a>
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
                <th>Is completed</th>
                <th>Wagon</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listOrders}" var="order">
                <tr>
                    <td>
							<span class="custom-checkbox">
								<input type="checkbox" id="checkbox1" name="options[]" value="1">
								<label for="checkbox1"></label>
							</span>
                    </td>
                    <td>${order.id}</td>
                    <td>${order.completed}</td>
                    <td>${order.wagon.id}</td>
                    <td>
                        <a href="#orderDetailsModal" data-toggle="modal"  onclick="seeDetails('${order.id}')">More details</a>
                        <a href="#addCargoModal" class="edit" data-toggle="modal" onclick="addCargo('${order.id}')"><i class="material-icons" data-toggle="tooltip" title="More details">&#xE254;</i></a>
                        <a href="#deleteOrderModal" class="delete" data-toggle="modal" onclick="setIdForRemove('${order.id}')"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<!-- Add Modal HTML -->
<div id="addOrderModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/order/add" ></c:url>
            <form:form action="${addAction}" modelAttribute="order"  >
                <div class="modal-header">
                    <h4 class="modal-title">Add Order</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label>Status completed</label>
                        <form:select path="completed" name="status" class="form-control">
                            <option value="true">True</option>
                            <option value="false">False</option>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label>Cargo</label>
                        <form:select path="cargoDTO_id" id="selectAddCargoes"></form:select>

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
<div id="orderDetailsModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form:form  id="formUpdate"  >
                <div class="modal-header">
                    <h4 class="modal-title">Edit Wagon</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>

                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-info" value="Save">
                </div>
            </form:form>
        </div>
    </div>
</div>
<!-- Add cargo to the order Modal HTML -->
<div id="addCargoModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form:form  id="formUpdate"  >
                <div class="modal-header">
                    <h4 class="modal-title">Edit Wagon</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="form-group">
                    <label>Cargo</label>
                    <select id="addCargoModal_selectAddCargoes"></select>

                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="button" class="btn btn-info" value="Add" onclick="addCargoToExistingOrder()">
                </div>
            </form:form>
        </div>
    </div>
</div>
<!-- Delete Modal HTML -->
<div id="deleteOrderModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <c:url var="addAction" value="/order/remove/" ></c:url>

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
<script src="<c:url value="/resources/order.js" />"></script>

</body>

</html>