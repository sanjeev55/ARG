<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 1/19/2018
  Time: 8:32 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Forget Password</title>

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'form-basic.css')}">


</head>


<body style="background-image:url('${resource(dir: "images", file: "user_bg.jpg")}')">

<div class="main-content">

<!-- You only need this form and the form-basic.css -->

    <g:form class="form-basic" method="post" controller="user" action="forgetPassword1">
        <div class="form-title-row">
            <h1>Forget Password</h1>
        </div>

        <div class="form-row">
            <label>
                <span>Enter your email:</span>
                <input type="text" name="email">
            </label>
        </div>




        <div class="form-row">
            <button type="submit">Send</button>
        </div>

    </g:form>

</div>

</body>

</html>
