<!DOCTYPE html>
<html lang="en">

<link>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ARG</title>

<link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom fonts for this template -->
<link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet" type="text/css">
<link href='https://fonts.googleapis.com/css?family=Cabin:700' rel='stylesheet' type='text/css'>

<!-- Custom styles for this template -->
<link href="../css/grayscale.min.css" rel="stylesheet">

</head>

<body id="page-top">

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="#page-top">Home</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false"
                aria-label="Toggle navigation">
            Menu
            <i class="fa fa-bars"></i>
        </button>

        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
<g:if test="${u.role == 'admin'}">
                    <li class="nav-item">
                        <g:link controller="teacher" action="addTeacher"
                                class="nav-link js-scroll-trigger">Teacher</g:link>
                    </li>

                    <li class="nav-item">
                        <g:link controller="subject" action="addSubject"
                                class="nav-link js-scroll-trigger">Subject</g:link>
                    </li>

                    <li class="nav-item">
                        <g:link controller="student" action="addInfo"
                                class="nav-link js-scroll-trigger">Class Information</g:link>
                    </li>

                    <li class="nav-item">
                         <g:link controller="college" action="enterData"
                                 class="nav-link js-scroll-trigger">Academic Year</g:link>

                    <li class="nav-item">
                        <g:link controller="routine" action="selectYear"
                                class="nav-link js-scroll-trigger">Generate Routine</g:link>
                    </li>

                    <li class="nav-item">
                        <g:link controller="routine" action="viewRoutine"
                                class="nav-link js-scroll-trigger">View Routine</g:link>
                    </li>
                    <li class="nav-item">
                        <g:link controller="user" action="logOut" class="nav-link js-scroll-trigger">Log Out</g:link>
                    </li>
</g:if>
    <g:if test="${u.role=='user'}">
        <li class="nav-item">
            <g:link controller="teacher" action="viewTeacher"
                    class="nav-link js-scroll-trigger">View Teacher</g:link>
        </li>
        <li class="nav-item">
            <g:link controller="teacher" action="searchByName"
                    class="nav-link js-scroll-trigger">Search Teacher</g:link>
        </li>
        <li class="nav-item">
            <g:link controller="subject" action="viewSubject"
                    class="nav-link js-scroll-trigger">View Subject</g:link>
        </li> <li class="nav-item">
            <g:link controller="subject" action="searchSubject"
                    class="nav-link js-scroll-trigger">Search Subject</g:link>
        </li>
        <li class="nav-item">
            <g:link controller="student" action="viewInfo"
                    class="nav-link js-scroll-trigger">View Class Information</g:link>
        </li>
        <li class="nav-item">
            <g:link controller="college" action="viewData"
                    class="nav-link js-scroll-trigger">Academic Information</g:link>
        <li class="nav-item">
        <g:link controller="routine" action="viewRoutine"
                class="nav-link js-scroll-trigger">View Routine</g:link>
        </li>
            <g:link controller="user" action="logOut" class="nav-link js-scroll-trigger">Log Out</g:link>
        </li>
    </g:if>




            </ul>
        </div>
    </div>
</nav>

<!-- Intro Header -->
<header class="masthead" style="background-image:url('${resource(dir: "images", file: "clock1.jpg")}')">
    <div class="intro-body">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 mx-auto">
                    <h1 class="brand-heading">Automatic Routine Generator</h1>

                    <p class="intro-text">Generate Routine Easily and Efficiently
                    </p>
                    <a href="#about" class="btn btn-circle js-scroll-trigger">
                        <i class="fa fa-angle-double-down animated"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- About Section -->
<section id="about" class="content-section text-center"
         style="background-image:url('${resource(dir: "images", file: "airplane1.jpg")}')">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 mx-auto">
                <h2>About Automatic Routine Generator</h2>

                <p>Automatic Routine Generator is a web application that allows you to generate routine easily and efficiently.</p>

                <p>Automatic Routine Generator is developed using Grails Framework.</p>

                <p>This project is completed under the Supervision of our respected Teacher Er. Ashok GM.</p>
            </div>
        </div>
    </div>
</section>



<!-- Contact Section -->
<section id="contact" class="content-section text-center"
         style="background-image:url('${resource(dir: "images", file: "maxresdefault.jpg")}')">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 mx-auto">
                <h2>Contact Us</h2>

                <p>Feel free to contact us at

                </p>
                <ul class="list-inline banner-social-buttons">
                    <li class="list-inline-item">
                        <a href="https://twitter.com" target="_blank" class="btn btn-default btn-lg">
                            <i class="fa fa-twitter fa-fw"></i>
                            <span class="network-name">Twitter</span>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a href="https://github.com" target="_blank" class="btn btn-default btn-lg">
                            <i class="fa fa-github fa-fw"></i>
                            <span class="network-name">Github</span>
                        </a>
                    </li>
                    <li class="list-inline-item">
                        <a href="https://plus.google.com" target="_blank" class="btn btn-default btn-lg">
                            <i class="fa fa-google-plus fa-fw"></i>
                            <span class="network-name">Google+</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</section>


%{--
<!-- Bootstrap core JavaScript -->
<g:javascript src="jquery.min.js"></g:javascript>
<g:javascript src="bootstrap.bundle.min.js"></g:javascript>

<!-- Plugin JavaScript -->
<g:javascript src="jquery.easing.min.js"></g:javascript>

<!-- Google Maps API Key - Use your own API key to enable the map feature. More information on the Google Maps API can be found at https://developers.google.com/maps/ -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCRngKslUGJTlibkQ3FkfTxj3Xss1UlZDA&sensor=false"></script>

<!-- Custom scripts for this template -->
<g:javascript src="grayscale.min.js"></g:javascript>
<r:layoutResources/>--}%

<!-- Bootstrap core JavaScript -->
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Plugin JavaScript -->
<script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Google Maps API Key - Use your own API key to enable the map feature. More information on the Google Maps API can be found at https://developers.google.com/maps/ -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCRngKslUGJTlibkQ3FkfTxj3Xss1UlZDA&sensor=false"></script>

<!-- Custom scripts for this template -->
<script src="../js/grayscale.min.js"></script>
</body>

</html>