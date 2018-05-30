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

    <title>Select Faculty and Semester</title>


    <link rel="stylesheet" href="${resource(dir: 'css', file: 'form-basic.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'demo.css')}">

</head>
<body style="background-image:url('${resource(dir: "images", file: "")}')">


<div class="main-content">

<!-- You only need this form and the form-basic.css -->

    <g:form class="form-basic" method="post" controller="college" action="enterData1" >
        <div class="form-title-row">

        </div>



        <h2>Select the Faculty and Semester</h2>
        <div class="form-row">
            <label>
                <span>Select Faculty</span>
                <select name="faculty">
                    <option>------</option>
                    <option value="BCT">BCT</option>
                    <option value="BEX">BEX</option>
                </select>
            </label>
        </div>


        <div class="form-row">
            <label>
                <span>Select Semester</span>
                <select name="semester">
                    <option>------</option>
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
            <button type="submit">Submit</button>
        </div>

    </g:form>
    <div class="form-basic">
        <g:link controller="college" action="viewData"
                style="text-decoration: none"><button>View Details</button></g:link>
    </div>

</div>

</body>

</html>