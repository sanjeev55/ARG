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

    <title>Add Teacher</title>


    <link rel="stylesheet" href="${resource(dir: 'css', file: 'form-basic.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'demo.css')}">

</head>
<body style="background-image:url('${resource(dir: "images", file: "")}')">


<div class="main-content">

<!-- You only need this form and the form-basic.css -->

    <g:form class="form-basic" method="post" controller="teacher" action="add" >
        <div class="form-title-row">
            <h1>Add Teacher</h1>
        </div>


        <div class="form-row">
            <label>
                <span>Teacher's Name</span>
                <input type="text" name="name">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Teacher's Short Name</span>
                <input type="text" name="shortName">
            </label>
        </div>

        <div class="form-row">
            <label>
                <span>Address</span>
                <input type="text" name="address">
            </label>
        </div>

        <div class="form-row">
            <label>
                <span>Phone Number</span>
                <input type="text" name="phoneNumber">
            </label>
        </div> <div class="form-row">
            <label>
                <span>Specialization</span>
                <input type="text" name="specialization">
            </label>
        </div>

        <div class="form-row">
            <label>
                <span>Type</span>
                <select name="type">
                    <option value="Full Time">Full Time</option>
                    <option value="Part Time">Part Time</option>
                    <option value="Contract">Contract</option>
                </select>
            </label>
        </div>

        <div class="form-row">
            <button type="submit">Add</button>
        </div>


    </g:form>
    <div class="form-basic">
        <g:link controller="teacher" action="viewTeacher"
                style="text-decoration: none"><button>View Teachers</button></g:link>

    <g:link controller="teacher" action="searchByName"
            style="text-decoration: none"><button>Search Teacher</button></g:link>
    </div>

    <g:javascript>

        var faculty=$('#faculty').val();
        var semester=$('#semester').val();
        console.log(faculty);
        console.log(semester);

        $('#semester').change(function() {

            console.log("User is pressing key");
            var faculty=$('#faculty').val();
            console.log(faculty);


            var semester=$('#semester').val();
            console.log(semester);

             getSubjects(faculty,semester);




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

    </g:javascript>
    <r:layoutResources/>

</div>

</body>

</html>