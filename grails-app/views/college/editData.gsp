<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2/10/2018
  Time: 7:49 PM
--%>

<!DOCTYPE html>
<html>
<g:javascript library="jquery"/>
<r:layoutResources/>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Edit Data</title>


    <link rel="stylesheet" href="${resource(dir: 'css', file: 'form-basic.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'demo.css')}">

</head>
<body style="background-image:url('${resource(dir: "images", file: "")}')">


<div class="main-content">

<!-- You only need this form and the form-basic.css -->

    <g:form class="form-basic" method="post" controller="college" action="updateData" >
        <div class="form-title-row">
            <h1>Edit</h1>
        </div>

        <input type="hidden" name="id" value="${c.id}">
        <div class="form-row">
            <label>
                <span>Teacher's Name</span>
                <input type="text" name="name" id="name" value="${c.teacher}">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Teacher's Short Name</span>
                <input type="text" name="shortName" id="shortName" value="${c.shortName}">
            </label>
        </div>

        <div class="form-row">
            <label>
                <span>Faculty</span>
                <input type="text" name="faculty" id="faculty" value="${c.faculty}">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Semester</span>
                <input type="text" name="semester" id="semester" value="${c.semester}">
            </label>
        </div>

        <div class="form-row">
            <label>
                <span>Subject</span>
                <select name="subject" id="subject">
                    <g:each in="${subject}" var="i">
                        <option value="${c.subject}">${c.subject}</option>
                        <option value="i.name">i.name</option>
                    </g:each>
                </select>
            </label>
        </div>

        <div class="form-row">
            <label>
                <span>Type</span>
                <input type="text" name="type" id="type" value="${c.type}">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>No. of Classes per Week</span>
                <input type="text" name="classesPerWeek" value="${c.classesPerWeek}">
            </label>
        </div>
        <div class="form-row" style="text-align: center">
            <input type="button" id="btnSubmit" value=Submit name="Book">

        </div>

    %{--<div class="form-row">
        <button type="submit">Add</button>
    </div>--}%

    </g:form>
    <g:javascript>

        var faculty=$('#faculty').val();
        var semester=$('#semester').val();
        var name=$('#name').val();
        console.log(faculty);
        console.log(semester);

        $('#name').keypress(function(e) {
            if(e.which==13){

            console.log("User is pressing key");
            var faculty=$('#faculty').val();
            console.log(faculty);


            var semester=$('#semester').val();
            console.log(semester);

             getSubjects(faculty,semester);
             }





        });
        $('#name').keypress(function(e){
            if(e.which==13){
                var name=$('#name').val();
            console.log(name);

            getType(name);
            getShortName(name);
            }
        });
        function getSubjects(faculty,semester){
        $.ajax({
           type:'POST',
           url:"${createLink(controller: "subject", action: "getSubjects")}",
           data: {f:faculty,s:semester},

           success: function(response){
              console.log(response);
              for (i = 0; i < response.length; i++)
                {
                    $('#subject').append( '<option value="'+response[i].name+'">'+response[i].name+'</option>' );
                }

           },
           error: function(response) {

           }
        });
    }
    function getType(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getType")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#type').val(response);


            },
            erro: function(response){

            }
            });
    }
    function getShortName(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getShortName")}",
            data:{n:name},

            success :function(response){
                console.log(response);

                $('#shortName').val(response);

            },
            error: function(response){

            }
            });
    }
      $("#btnSubmit").on("click", function(){
 	// Submit the form
    document.querySelector("form").submit();
    });

    </g:javascript>
    <r:layoutResources/>

</div>

</body>

</html>