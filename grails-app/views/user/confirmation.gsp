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

    <g:form class="form-basic" method="post" controller="user" action="registerUser" >
        <input type="hidden" name="name" value="${u.name}" >
        <input type="hidden" name="email" value="${u.email}" >
        <input type="hidden" name="password" value="${u.password}" >
        <input type="hidden" name="randomNumber" value="${r}">

        <div class="form-title-row">
            <h1>Confirmation</h1>
        </div>


        <div class="form-row">
            <label>
                <h6>Enter the 4 digit confirmation code sent to the email address provided</h6>
                <span>Confirmation Code</span>
                <input type="text" name="code">
            </label>
        </div>


        <div class="form-row">
            <button type="submit">Confirm</button>
        </div>

    </g:form>

</div>

</body>

</html>