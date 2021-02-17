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
    <title>Routine Generated</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">

    <link rel="stylesheet" href="${resource(dir: 'css', file: 'styleTable.css')}" type="text/css">
 <link rel="stylesheet" href="css/style.css">

    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    <g:javascript  src="indexTable.js"></g:javascript>
    <script src="https://s.codepen.io/assets/libs/modernizr.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'styleButton.css')}" type="text/css">
</head>

<body style="background-image:url('${resource(dir: "images", file: "viewTeacher.png")}');background-size: cover">
<section>
    <!--for demo wrap-->
    <h1>Routine for ${f} ${t}</h1>
    <div class="tbl-header">
        <table cellpadding="0" cellspacing="0" border="0">
            <thead>

            <tr>
                <th></th>
                <g:each in="${s}" var="i" >
                    <th>${i.startTime}-${i.endTime}</th>
                </g:each>

            </tr>

            </thead>
        </table>
    </div>
    <div class="tbl-content">
        <table cellpadding="0" cellspacing="0" border="0">
            <tbody>
            <tr>
                <th>Tuesday</th>
            <g:each in="${a}" var="i">
            <g:if test="${i.day=='Tuesday'}">

                    <th>${i.subjectName} (${i.teacherName})</th>



            </g:if>
            </g:each>
            </tr>
            <tr>
                <th>Wednesday</th>
                <g:each in="${a}" var="i">
                    <g:if test="${i.day=='Wednesday'}">

                        <th>${i.subjectName} (${i.teacherName})</th>


                    </g:if>
                </g:each>
            </tr>
            <tr>
                <th>Thursday</th>
                <g:each in="${a}" var="i">
                    <g:if test="${i.day=='Thursday'}">

                        <th>${i.subjectName} (${i.teacherName})</th>


                    </g:if>
                </g:each>
            </tr>
            <tr>
                <th>Friday</th>
                <g:each in="${a}" var="i">
                    <g:if test="${i.day=='Friday'}">

                        <th>${i.subjectName} (${i.teacherName})</th>


                    </g:if>
                </g:each>
            </tr>
            <tr>
                <th>Saturday</th>
                <g:each in="${a}" var="i">
                    <g:if test="${i.day=='Saturday'}">

                        <th>${i.subjectName} (${i.teacherName})</th>


                    </g:if>
                </g:each>
            </tr>
            <tr>
                <th>Sunday</th>
                <g:each in="${a}" var="i">
                    <g:if test="${i.day=='Sunday'}">

                        <th>${i.subjectName} (${i.teacherName})</th>


                    </g:if>
                </g:each>
            </tr>
            </tbody>
        </table>

    </div>


</section>
<g:link controller="routine" action="downloadExcel" id="${f}"
        style="text-decoration: none"><button>Download Excel</button></g:link>
</body>
</html>


