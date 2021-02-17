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

    <title>Select year</title>


    <link rel="stylesheet" href="${resource(dir: 'css', file: 'form-basic.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'demo.css')}">

</head>
<body style="background-image:url('${resource(dir: "images", file: "")}')">


<div class="main-content">

<!-- You only need this form and the form-basic.css -->
    <div class="form-basic">
        <div class="form-title-row">
            <h1>Select Year</h1>
        </div>
        <g:link controller="routine" action="createChromosome"
                style="text-decoration: none"><button>1st Year</button></g:link>
        <g:link controller="routine" action="createChromosome"
                style="text-decoration: none"><button>2nd Year</button></g:link>
        <g:link controller="routine" action="createChromosome"
                style="text-decoration: none"><button>3rd Year</button></g:link>
        <g:link controller="routine" action="createChromosome"
                style="text-decoration: none"><button>4th Year</button></g:link>




</div>
</div>

</body>

</html>