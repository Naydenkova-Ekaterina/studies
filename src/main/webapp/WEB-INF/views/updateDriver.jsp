<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<head>
    <meta charset="utf-8" />
    <%--    <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/apple-icon.png">--%>
    <%--    <link rel="icon" type="image/png" href="../assets/img/favicon.ico">--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Update Driver</title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
    <!--     Fonts and icons     -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" />
    <!-- CSS Files -->
    <%--    <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />--%>
    <%--    <link href="../assets/css/light-bootstrap-dashboard.css?v=2.0.0 " rel="stylesheet" />--%>


    <link id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}"/>

    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <style>
        <%@include file='/resources/assets/css/bootstrap.min.css' %>
        <%@include file='/resources/assets/css/light-bootstrap-dashboard.css' %>

    </style>





</head>

<body>
<div class="wrapper">
    <div class="sidebar" data-image="../assets/img/sidebar-5.jpg">
        <!--
    Tip 1: You can change the color of the sidebar using: data-color="purple | blue | green | orange | red"

    Tip 2: you can also add an image using data-image tag
-->
        <div class="sidebar-wrapper">
            <div class="logo">
                <a href="http://www.creative-tim.com" class="simple-text">
                    Menu
                </a>
            </div>
            <ul class="nav">
                <li>
                    <a class="nav-link" href="${pageContext.request.contextPath}/driver/info/">
                        <i class="nc-icon nc-chart-pie-35"></i>
                        <p>Info</p>
                    </a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href=${pageContext.request.contextPath}/updateDriver">
                        <i class="nc-icon nc-circle-09"></i>
                        <p>Update Info</p>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="main-panel">
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg " color-on-scroll="500">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">Home</a>
                <button href="" class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-bar burger-lines"></span>
                    <span class="navbar-toggler-bar burger-lines"></span>
                    <span class="navbar-toggler-bar burger-lines"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end" id="navigation">

                    <ul class="navbar-nav ml-auto">

                        <li class="nav-item">
                            <a class="nav-link" href="#pablo">
                                <span class="no-icon">Log out</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- End Navbar -->
        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Driver Info</h4>
                            </div>
                            <div class="card-body">
<%--                                <form>--%>
                                    <div class="row">
                                        <div class="col-md-5 pr-1">
                                            <div class="form-group">


                                                <label>Shift start/end</label>
                                                <button class="form-control" onclick="startShift()">Start shift</button>
                                                <button class="form-control" onclick="endShift()">End shift</button>

                                            </div>
                                        </div>

                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 pr-1">
                                            <div class="form-group">


                                                <div class="form-group">
                                                    <label>Status</label>
                                                    <select name="status" class="form-control" id="UpdStat">
                                                        <option value="rest">Rest</option>
                                                        <option value="shift">Shift</option>
                                                        <option value="behindTheWheel">Behind the wheel</option>
                                                    </select>
                                                    <button class="form-control" value="Change driver status" onclick="changeDriverStatus()">Change driver status</button>

                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">



                                                <div class="table-title" style="margin: 0">
                                                    <div class="row">
                                                        <div class="col-sm-6">
                                                            <h2><b>Cargoes</b></h2>
                                                        </div>
                                                        <div class="col-sm-6">
                                                        </div>
                                                    </div>
                                                </div>
                                                <table class="table table-striped table-hover">
                                                    <thead>
                                                    <tr>
                                                        <th></th>
                                                        <th>Id</th>
                                                        <th>Name</th>
                                                        <th>Weight</th>
                                                        <th>Source</th>
                                                        <th>Destination</th>
                                                        <th>Status</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="cargoes_table">
                                                    </tbody>
                                                </table>



                                            </div>

                                            </div>
                                        </div>

                                    </div>



                                    <%--                                        <button type="submit" class="btn btn-info btn-fill pull-right">Update Profile</button>--%>
                                    <div class="clearfix"></div>
                                </form>
                            </div>
                        </div>
<%--                    </div>--%>



                    <div class="col-md-4">
                        <div class="card card-user">
                            <div class="card-image">
                            </div>
                            <div class="card-body">
                                <div class="author">
                                    <a href="#">
                                        <img src="${pageContext.request.contextPath}/resources/img/userman.png" width="300px"/>
                                        <h5 class="title">Driver</h5>
                                    </a>
                                    <p class="description">
                                    </p>
                                </div>
                                <p class="description text-center">
<%--                                    "Lamborghini Mercy--%>
<%--                                    <br> Your chick she so thirsty--%>
<%--                                    <br> I'm in that two seat Lambo"--%>
                                </p>
                            </div>
                            <hr>
                            <div class="button-container mr-auto ml-auto">
                                <button href="#" class="btn btn-simple btn-link btn-icon">
                                    <i class="fa fa-facebook-square"></i>
                                </button>
                                <button href="#" class="btn btn-simple btn-link btn-icon">
                                    <i class="fa fa-twitter"></i>
                                </button>
                                <button href="#" class="btn btn-simple btn-link btn-icon">
                                    <i class="fa fa-google-plus-square"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            </div>
        </div>
        <footer class="footer">
            <div class="container-fluid">
                <nav>
                    <ul class="footer-menu">
                        <li>
                            <a href="#">
                                Home
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                Company
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                Portfolio
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                Blog
                            </a>
                        </li>
                    </ul>

                </nav>
            </div>
        </footer>
    </div>
</div>

</body>

<script src="<c:url value="/resources/js/driverUpdate.js" />"></script>

<!--   Core JS Files   -->
<script src="../assets/js/core/bootstrap.min.js" type="text/javascript"></script>
<!--  Plugin for Switches, full documentation here: http://www.jque.re/plugins/version3/bootstrap.switch/ -->
<script src="../assets/js/plugins/bootstrap-switch.js"></script>
<!--  Chartist Plugin  -->
<script src="../assets/js/plugins/chartist.min.js"></script>
<!-- Control Center for Light Bootstrap Dashboard: scripts for the example pages etc -->
<script src="../assets/js/light-bootstrap-dashboard.js?v=2.0.0 " type="text/javascript"></script>

</html>
