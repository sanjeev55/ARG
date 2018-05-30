<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2/10/2018
  Time: 10:28 PM
--%>

<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Subjects</title>

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
    <h1>Subject's List</h1>
    <div class="tbl-header">
        <table cellpadding="0" cellspacing="0" border="0">
            <thead>

            <tr>
                <th>Name</th>
                <th>Faculty</th>
                <th>Semester</th>
                <g:if test="${u.role=='admin'}">
                    <th>Edit</th>
                    <th>Delete</th>
                </g:if>
            </tr>
            </thead>
        </table>
    </div>
    <div class="tbl-content">
        <table cellpadding="0" cellspacing="0" border="0">
            <tbody>
            <g:each in="${s}" var="i">
                <tr>
                    <th>${i.name}</th>
                    <th>${i.faculty}</th>
                    <th>${i.semester}</th>
                    <g:if test="${u.role=='admin'}">
                    <th><g:link controller="subject" action="editSubject" id="${i.id}"> Edit</g:link></th>
                    <th><g:link controller="subject" action="deleteSubject" id="${i.id}"> Delete</g:link></th>
                    </g:if>
                </tr>

            </g:each>
            </tbody>
        </table>

    </div>

    <g:link controller="user" action="backDashboard">
    <section>
        <button id="js-trigger-overlay1" type="button">Home</button>
    </section>
</g:link>
    <g:if test="${u.role=='admin'}">
    <g:link controller="subject" action="addSubject">
        <section>
            <button id="js-trigger-overlay1" type="button">Add More</button>
        </section>
    </g:link>
    </g:if>
</section>
</body>
</html>