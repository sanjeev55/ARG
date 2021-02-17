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

    <title>Enter Data</title>


    <link rel="stylesheet" href="${resource(dir: 'css', file: 'form-basic.css')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'demo.css')}">

</head>
<body style="background-image:url('${resource(dir: "images", file: "")}')">


<div class="main-content">

<!-- You only need this form and the form-basic.css -->

    <g:form class="form-basic" method="post" controller="college" action="addData" >
        <div class="form-title-row">
            <h1>Enter Data</h1>
        </div>


        <div class="form-row">
            <label>
            <span>Teacher's Name</span>
            <select name="name" id="name">
                <g:each in="${t}" var="j">
                    <option value="${j.name}">${j.name}</option>
                </g:each>
            </select>
            </label>
        </div>
        <div class="form-row">
            <label>
                <span>Teacher's Short Name</span>
                <input type="text" name="shortName" id="shortName">
            </label>
        </div>

     <div class="form-row">
         <label>
             <span>Faculty</span>
            <input type="text" name="faculty" value="${f}" id="faculty" readonly>
         </label>
     </div>
     <div class="form-row">
         <label>
             <span>Semester</span>
             <input type="text" name="semester" id="semester"  value="${s}" readonly>
         </label>
     </div>

      <div class="form-row">
          <label>
              <span>Subject</span>
              <select name="subject" id="subject">
                  <g:each in="${subject}" var="i">
                      <option value="i.name">i.name</option>
                  </g:each>
              </select>
          </label>
      </div>

        <div class="form-row">
            <label>
                <span>Type</span>
               <input type="text" name="type" id="type">
            </label>
        </div>
     <div class="form-row">
         <label>
             <span>No. of Classes per Week</span>
             <input type="text" name="classesPerWeek">
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

            if($('#faculty').val()==="BEX"){
                 faculty="BEX";
            }
            else{
                 faculty="BCT";
            }

            console.log("User is pressing key");
          //  var faculty=$('#faculty').val();
            console.log(faculty);


            //var semester=$('#semester').val();
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
            getTueStartTime(name);
            getTueEndTime(name);
            getWedStartTime(name);
            getWedEndTime(name);
            getThurStartTime(name);
            getThurEndTime(name);
            getFriStartTime(name);
            getFriEndTime(name);
            getSatStartTime(name);
            getSatEndTime(name);
            getSunStartTime(name);
            getSunEndTime(name);
            }
        });
        function getSubjects(faculty,semester){
        $.ajax({
           type:'POST',
           url:"${createLink(controller: "subject", action: "getSubjects")}",
           data: {f:faculty,s:semester},

           success: function(response){
              console.log(response);
              for (var i = 0; i < response.length; i++)
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
     function getTueStartTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getTueStartTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#tueStartTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getTueEndTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getTueEndTime")}",
            data:{n:name},
            success :function(response){
                console.log(response);
                $('#tueEndTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getWedStartTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getWedStartTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#wedStartTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getWedEndTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getWedEndTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#wedEndTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getThurStartTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getThurStartTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#thurStartTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getThurEndTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getThurEndTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#thurEndTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getFriStartTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getFriStartTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#friStartTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getFriEndTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getFriEndTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#friEndTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getSatStartTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getSatStartTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#satStartTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getSatEndTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getSatEndTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#satEndTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getSunStartTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getSunStartTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#sunStartTime').val(response);


            },
            error: function(response){

            }
            });
    }
     function getSunEndTime(name){
            $.ajax({
            type:'POST',
            url:"${createLink(controller: "teacher", action:"getSunEndTime")}",
            data:{n:name},

            success :function(response){
                console.log(response);
                $('#sunEndTime').val(response);


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