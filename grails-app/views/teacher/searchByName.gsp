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

    <title>Search By Name</title>


    <link rel="stylesheet" href="${resource(dir: 'css', file: 'form-basic.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'demo.css')}">

</head>
<body style="background-image:url('${resource(dir: "images", file: "")}')">


<div class="main-content">

<!-- You only need this form and the form-basic.css -->

    <g:form class="form-basic" method="post" controller="teacher" action="searchByName1" >
        <div class="form-title-row">
            <h1>Search</h1>
        </div>


        <div class="form-row">
            <label>
                <span>Teacher's Name</span>
                <input type="text" name="name">
            </label>
        </div>


        <div class="form-row">
            <button type="submit">Search</button>
        </div>

    </g:form>

</div>

</body>

</html>