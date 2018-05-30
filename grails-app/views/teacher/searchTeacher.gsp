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
    <li class="nav-item">
<g:link controller="teacher" action="searchByName"
        class="nav-link js-scroll-trigger">Search By Name</g:link>
</li>
<li class="nav-item">
    <g:link controller="teacher" action="searchByFaculty"
            class="nav-link js-scroll-trigger">Search By Faculty</g:link>
</li>
<li class="nav-item">
    <g:link controller="teacher" action="searchBySemester"
            class="nav-link js-scroll-trigger">Search By Semester</g:link>
</li>
<li class="nav-item">
    <g:link controller="teacher" action="searchByFacultyAndSemester"
            class="nav-link js-scroll-trigger">Search By Faculty and Semester</g:link>
</li>
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