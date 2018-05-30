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

    <title>Add Flight</title>


    <link rel="stylesheet" href="${resource(dir: 'css', file: 'form-basic.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'demo.css')}">

</head>
<body style="background-image:url('${resource(dir: "images", file: "")}')">


<div class="main-content">

<!-- You only need this form and the form-basic.css -->

    <g:form class="form-basic" method="post" controller="student" action="updateInfo" >
        <div class="form-title-row">
            <h1>Edit Information</h1>
        </div>

        <input type="hidden" name="id" value="${s.id}">


        <div class="form-row">
            <label>
                <span>Faculty</span>
                <select name="faculty">
                    <option value="BCT">BCT</option>
                    <option value="BEX">BEX</option>
                </select>
            </label>
        </div>


        <div class="form-row">
            <label>
                <span>Semester</span>
                <select name="semester">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                </select>
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Total Number of Students</span>
                <input type="text" name="noOfStudents" value="${s.noOfStudents}">
            </label>
        </div>
        <div class="form-row">
            <button type="submit">Update</button>
        </div>

    </g:form>

</div>

</body>

</html>