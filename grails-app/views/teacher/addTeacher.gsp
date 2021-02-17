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
                <select name="type" id="type">
                    <option value="Full Time">Full Time</option>
                    <option value="Part Time">Part Time</option>
                    <option value="Contract">Contract</option>
                </select>
            </label>
        </div>


        <div class="form-row">
            <label>
                <span> Tue Start Time</span>
                <input type="text" name="tueStartTime" id="tueStartTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span> Tue End Time</span>
                <input type="text" name="tueEndTime" id="tueEndTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span> Wed Start Time</span>
                <input type="text" name="wedStartTime" id="wedStartTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Wed End Time</span>
                <input type="text" name="wedEndTime" id="wedEndTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Thur Start Time</span>
                <input type="text" name="thurStartTime" id="thurStartTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Thur End Time</span>
                <input type="text" name="thurEndTime" id="thurEndTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Friday Start Time</span>
                <input type="text" name="friStartTime" id="friStartTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Friday End Time</span>
                <input type="text" name="friEndTime" id="friEndTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Saturday Start Time</span>
                <input type="text" name="satStartTime" id="satStartTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Saturday End Time</span>
                <input type="text" name="satEndTime" id="satEndTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Sun Start Time</span>
                <input type="text" name="sunStartTime" id="sunStartTime">
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Sunday End Time</span>
                <input type="text" name="sunEndTime" id="sunEndTime">
            </label>
        </div>
        <div class="form-row">
            <button type="submit">Add</button>
        </div>


    </g:form>
    <g:javascript>
        var type=$('#type').val();

        $('#type').keypress(function(e){
            if(e.which==13){
                var type=$('#type').val();
                if(type=="Full Time"){
                getStartTime();
                getEndTime();
                }
                console.log(type);
            }
        });
          function getStartTime(){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "slot", action:"getStartTime")}",


            success :function(response){
                console.log(response);

                $('#tueStartTime').val(response);
                $('#wedStartTime').val(response);
                $('#thurStartTime').val(response);
                $('#friStartTime').val(response);
                $('#satStartTime').val(response);
                $('#sunStartTime').val(response);

            },
            error: function(response){

            }
            });
    }
      function getEndTime(){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "slot", action:"getEndTime")}",


            success :function(response){
                console.log(response);

                $('#tueEndTime').val(response);
                $('#wedEndTime').val(response);
                $('#thurEndTime').val(response);
                $('#friEndTime').val(response);
                $('#satEndTime').val(response);
                $('#sunEndTime').val(response);

            },
            error: function(response){

            }
            });
    }
</g:javascript>
    <r:layoutResources/>
</div>
</div>
    <div class="form-basic">
        <g:link controller="teacher" action="viewTeacher"
                style="text-decoration: none"><button>View Teachers</button></g:link>

    <g:link controller="teacher" action="searchByName"
            style="text-decoration: none"><button>Search Teacher</button></g:link>
    </div>



</div>

</body>

</html>