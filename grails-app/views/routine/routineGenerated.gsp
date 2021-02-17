<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2/10/2018
  Time: 10:28 PM
--%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Routine Generated</title>


    <link rel="stylesheet" href="${resource(dir: 'css', file: 'form-basic.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'demo.css')}">

</head>
<body style="background-image:url('${resource(dir: "images", file: "")}')">


<div class="main-content">
    <div class="form-basic">
    <div class="form-title-row">
        <h1>Routine Successfully Generated!!</h1>
        <h1>Choose from the following options:</h1>
    </div>
        <g:link controller="routine" action="selectTeacher"
                style="text-decoration: none"><button>Teacher</button></g:link>

        <g:link controller="routine" action="selectClass"
                style="text-decoration: none"><button>Class</button></g:link>
    </div>

</div>

</body>

</html>