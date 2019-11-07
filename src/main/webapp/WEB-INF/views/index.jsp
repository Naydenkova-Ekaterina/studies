<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->  
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->  
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->  
<head>

    <title>Logiweb</title>
    <!-- Meta -->
    <meta charset="utf-8">
    <link rel="shortcut icon" href="/resources/favicon.ico">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <!-- Global CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


    <!-- Theme CSS -->
    <link id="theme-style" rel="indexStyle" href="assets/css/styles.css">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <style>
        <%@include file='/resources/indexStyle.css' %>
    </style>

</head> 

<body class="landing-page">
    
    <div class="page-wrapper">
        
        <!-- ******Header****** -->
        <header class="header text-center">
            <div class="container">
                <div class="branding">
                    <h1 class="logo">
                        <span aria-hidden="true" class="icon_documents_alt icon"></span>
                        <span class="text-highlight">Shipping</span><span class="text-bold">Project</span>
                    </h1>
                </div><!--//branding-->
                <div class="tagline">

                </div><!--//tagline-->
            </div><!--//container-->
        </header><!--//header-->
        
        <section class="cards-section text-center">
            <div class="container">
                <h2 class="title">Getting started is easy!</h2>
                <div class="intro">
                    <p></p>
                    <div class="cta-container">
                        <a class="btn btn-primary btn-cta" href="${pageContext.request.contextPath}/login" target="_blank"><i class="fa fa-cloud-download"></i>Login</a>
                        <a class="btn btn-primary btn-cta" href="<c:url value="/logout" />" target="_blank"><i class="fa fa-cloud-download"></i>Logout</a>

                    </div><!--//cta-container-->
                </div><!--//intro-->
                <div id="cards-wrapper" class="cards-wrapper row">
                    <div class="item item-green col-md-4 col-sm-6 col-xs-6">
                        <div class="item-inner">
                            <div class="icon-holder">
                                <i class="icon fa fa-paper-plane"></i>
                            </div><!--//icon-holder-->
                            <h3 class="title">Drivers</h3>
                            <img src="${pageContext.request.contextPath}/resources/img/icons8-driver-64.png"/>
                            <p class="intro">Demo example, consectetuer adipiscing elit</p>
                            <a class="link" href="${pageContext.request.contextPath}/drivers"><span></span></a>
                        </div><!--//item-inner-->
                    </div><!--//item-->
                    <div class="item item-pink item-2 col-md-4 col-sm-6 col-xs-6">
                        <div class="item-inner">
                            <div class="icon-holder">
                                <span aria-hidden="true" class="icon icon_puzzle_alt"></span>
                            </div><!--//icon-holder-->
                            <h3 class="title">Wagons</h3>
                            <img src="${pageContext.request.contextPath}/resources/img/icons8-truck-100.png"/>
                            <p class="intro">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</p>
                            <a class="link" href="${pageContext.request.contextPath}/wagons"><span></span></a>
                        </div><!--//item-inner-->
                    </div><!--//item-->
                    <div class="item item-blue col-md-4 col-sm-6 col-xs-6">
                        <div class="item-inner">
                            <div class="icon-holder">
                                <span aria-hidden="true" class="icon icon_datareport_alt"></span>
                            </div><!--//icon-holder-->
                            <h3 class="title">Cargoes</h3>
                            <img src="${pageContext.request.contextPath}/resources/img/cargo.png" height="80px" width="80px"/>
                            <p class="intro">Lorem ipsum dolor sit amet, consectetuer adipiscing elit</p>
                            <a class="link" href="${pageContext.request.contextPath}/cargoes"><span></span></a>
                        </div><!--//item-inner-->
                    </div><!--//item-->
                    <div class="item item-purple col-md-4 col-sm-6 col-xs-6">
                        <div class="item-inner">
                            <div class="icon-holder">
                                <span aria-hidden="true" class="icon icon_lifesaver"></span>
                            </div><!--//icon-holder-->
                            <h3 class="title">Orders</h3>
                            <img src="${pageContext.request.contextPath}/resources/img/order.png"/>
                            <p class="intro">Layout for FAQ page. Lorem ipsum dolor sit amet, consectetuer adipiscing elit</p>
                            <a class="link" href="${pageContext.request.contextPath}/orders"><span></span></a>
                        </div><!--//item-inner-->
                    </div><!--//item-->
                    <div class="item item-primary col-md-4 col-sm-6 col-xs-6">
                        <div class="item-inner">
                            <div class="icon-holder">
                                <span aria-hidden="true" class="icon icon_genius"></span>
                            </div><!--//icon-holder-->
                            <h3 class="title">TODO</h3>
                            <p class="intro">TODO </p>
                            <a class="link" href=""><span></span></a>
                        </div><!--//item-inner-->
                    </div><!--//item-->
                    <div class="item item-orange col-md-4 col-sm-6 col-xs-6">
                        <div class="item-inner">
                            <div class="icon-holder">
                                <span aria-hidden="true" class="icon icon_gift"></span>
                            </div><!--//icon-holder-->
                            <h3 class="title">Driver Info</h3>
                            <img src="${pageContext.request.contextPath}/resources/img/user.png"/>
                            <p class="intro">Layout for license &amp; credits page. Consectetuer adipiscing elit.</p>
                            <a class="link" href="${pageContext.request.contextPath}/driver/info/"><span></span></a>
                        </div><!--//item-inner-->
                    </div><!--//item-->
                </div><!--//cards-->
                
            </div><!--//container-->
        </section><!--//cards-section-->
    </div><!--//page-wrapper-->
    
    <footer class="footer text-center">
        <div class="container">
            <!--/* This template is released under the Creative Commons Attribution 3.0 License. Please keep the attribution link below when using for your own project. Thank you for your support. :) If you'd like to use the template without the attribution, you can check out other license options via our website: themes.3rdwavemedia.com */-->

        </div><!--//container-->
    </footer><!--//footer-->
    
     
    <!-- Main Javascript -->

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


    <script type="text/javascript" src="../webapp/resources/main.js"></script>
    
</body>
</html> 

