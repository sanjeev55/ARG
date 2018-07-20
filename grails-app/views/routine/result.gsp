<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2/10/2018
  Time: 8:01 PM
--%>

<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Teacher's List</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'styleTable.css')}" type="text/css">
    %{-- <link rel="stylesheet" href="css/style.css">--}%
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    <g:javascript  src="indexTable.js"></g:javascript>
    <script src="https://s.codepen.io/assets/libs/modernizr.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'styleButton.css')}" type="text/css">
</head>

<body style="background-image:url('${resource(dir: "images", file: "viewTeacher.png")}');background-size: cover">
<section>
    <!--for demo wrap-->
    <h1>Routine For Sunday</h1>
    <div class="tbl-header">
        <table cellpadding="0" cellspacing="0" border="0">
            <thead>

            <tr>
                <th></th>


            </tr>

            </thead>
        </table>
    </div>
    <div class="tbl-content">
        <table cellpadding="0" cellspacing="0" border="0">
            <tbody>
            <g:each in="${a}" var="i">
                <tr>
                    <th>Section: ${i.section}</th>
                    <th>Start Time: ${i.startTime}</th>
                    <th>End Time: ${i.endTime}</th>
                    <th>Teacher's Name: ${i.name}</th>
                    <th>Subject's name: ${i.subject}</th>
                </tr>
            </g:each>
            <g:each in="${b}" var="i">
                <tr>
                    <th>Section: ${i.section}</th>
                    <th>Start Time: ${i.startTime}</th>
                    <th>End Time: ${i.endTime}</th>
                    <th>Teacher's Name: ${i.name}</th>
                    <th>Subject's name: ${i.subject}</th>
                </tr>
            </g:each><g:each in="${c}" var="i">
                <tr>
                    <th>Section: ${i.section}</th>
                    <th>Start Time: ${i.startTime}</th>
                    <th>End Time: ${i.endTime}</th>
                    <th>Teacher's Name: ${i.name}</th>
                    <th>Subject's name: ${i.subject}</th>
                </tr>
            </g:each><g:each in="${d}" var="i">
                <tr>
                    <th>Section: ${i.section}</th>
                    <th>Start Time: ${i.startTime}</th>
                    <th>End Time: ${i.endTime}</th>
                    <th>Teacher's Name: ${i.name}</th>
                    <th>Subject's name: ${i.subject}</th>
                </tr>
            </g:each>
            </tbody>
        </table>

    </div>


</section>
</body>
</html>
