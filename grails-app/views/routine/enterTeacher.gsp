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
<g:form class="form-basic" method="post" controller="routine" action="viewTeacher" >
    <div class="form-title-row">
        <h1>Routine Successfully Generated!!</h1>
    </div>
    <div class="form-row">
        <label>
            <span>Teacher's Name</span>
            <select name="teacher" id="teacher">
                %{--<g:each in="${t}" var="i">
                    <option value="i.name">i.name</option>
                </g:each>--}%
                <g:each in="${t}" var="j">
                    <option value="${j.name}">${j.name}</option>
                </g:each>
            </select>
        </label>
    </div>
    <div class="form-row">
        <button type="submit">Submit</button>
    </div>
</g:form>
</div>

</body>

</html>