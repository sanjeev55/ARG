package arg

import java.sql.Time

class CollegeController {

   def enterData(){
       render(view: "enterData")
   }
    def enterData1(){
        def faculty=params.faculty
        print("facultBEfore:"+faculty)

        def semester=Integer.parseInt(params.semester)
        def teacher=Teacher.findAll()

        render(view: "enterData1",model: [f:faculty,s:semester,t:teacher])
    }

    def addData(){

        def name=params.name
        def shortName=params.shortName
        def faculty=params.faculty
        def semester=Integer.parseInt(params.semester)
        def subject=params.subject
        def classesPerWeek=Integer.parseInt(params.classesPerWeek)
        def type=params.type
        if(name==""||classesPerWeek==""){
            redirect(action:"enterData")
        }
        else{
        def tueStartTime= Time.valueOf(params.tueStartTime)
        def tueEndTime= Time.valueOf(params.tueEndTime)
        def wedStartTime= Time.valueOf(params.wedStartTime)
        def wedEndTime= Time.valueOf(params.wedEndTime)
        def thurStartTime= Time.valueOf(params.thurStartTime)
        def thurEndTime= Time.valueOf(params.thurEndTime)
        def friStartTime= Time.valueOf(params.friStartTime)
        def friEndTime= Time.valueOf(params.friEndTime)
        def satStartTime= Time.valueOf(params.satStartTime)
        def satEndTime= Time.valueOf(params.satEndTime)
        def sunStartTime= Time.valueOf(params.satStartTime)
        def sunEndTime= Time.valueOf(params.satEndTime)
        def teacher=Teacher.findByName(name)
        def teacherCode=teacher.code

        def subject1=Subject.findByName(subject)
            print("subject:"+subject)
        def subjectCode=subject1.code



    College college = new College()

    college.teacher = name
    college.shortName = shortName
    college.faculty = faculty
    college.semester = semester
    college.subject = subject
    college.classesPerWeek = classesPerWeek
    college.type = type
    college.tueStartTime = tueStartTime
    college.tueEndTime = tueEndTime
    college.wedStartTime = wedStartTime
    college.wedEndTime = wedEndTime
    college.thurStartTime = thurStartTime
    college.thurEndTime = thurEndTime
    college.friStartTime = friStartTime
    college.friEndTime = friEndTime
    college.satStartTime = satStartTime
    college.satEndTime = satEndTime
    college.sunStartTime = sunStartTime
    college.sunEndTime = sunEndTime
    college.teacherCode = teacherCode
    college.subjectCode = subjectCode

    def data = College.findAll()
    if (data.size() != 0) {
        def college1 = College.findAll([sort: "id", order: "desc"])
        def coll1 = []

        coll1 = college1.code


        for (def i = 0; i < 1; i++) {
            def code = coll1[i]

            print("code:" + code)

            def code0 = Integer.parseInt(code, 2)
            def sum = code0 + Integer.parseInt("1", 2)
            def finalCode = Integer.toBinaryString(sum)
            college.code = finalCode
        }


    } else {
        college.code = "0"


    }

    college.save()
    firstTime()

    redirect(action: "viewData")

}
    }
    def viewData(){
        def college=College.findAll()
        def user=session.user
        render(view: "viewData",model: [c:college,u:user])
    }

    def editData(){
        def id=Integer.parseInt(params.id)

        def college=College.findById(id)

        render(view: "editData",model: [c:college])


    }
    def updateData(){
        def id=Integer.parseInt(params.id)

        def college=College.findById(id)

        def name=params.name
        def subject=params.subject
        def faculty=params.faculty
        def semester=Integer.parseInt(params.semester)
        def shortName=params.shortName
        def classesPerWeek=Integer.parseInt(params.classesPerWeek)
        def type=params.type

        college.teacher=name
        college.subject=subject
        college.faculty=faculty
        college.semester=semester
        college.shortName=shortName
        college.classesPerWeek=classesPerWeek
        college.type=type

        college.save()

        redirect(action: "viewData")


    }
    def deleteData(){

        def id=Integer.parseInt(params.id)

        def college=College.findById(id)

        college.delete()

        redirect(action: "viewData")

    }

    def firstTime(){
        def faculty=params.faculty
        def semester=params.semester
        def count=24
        def collegeFull=College.findAll()
        def collegeSize=collegeFull.size()
        def college1=College.findAllByFacultyAndSemesterAndSubjectCode(faculty,semester,"1111")
        def size=college1.size()
       College college=new College()
        print("size: "+size)
        //College college1=new College()
        if(size==0){
            print("no leisure")

            college.subject="Project Hour"
            college.teacher="Project"
            college.teacherCode="11111"
            college.subjectCode="1111"
            college.faculty=faculty
            college.semester=Integer.parseInt(semester)
            college.code="11111"
            college.shortName="P"
            college.type="FT"

            for(def i=1;i<=collegeSize;i++){
                print("inside for")
                def college2=College.findById(i)
                def faculty1=college2.faculty
                def classesPerWeek=college2.classesPerWeek
                def teacherCode=college2.teacherCode
                if(faculty==faculty1 && teacherCode!="11111"){
                    count=count-classesPerWeek
                }

            }
            college.classesPerWeek=count
            college.tueStartTime=Time.valueOf("07:00:00")
            college.wedStartTime=Time.valueOf("07:00:00")
            college.thurStartTime=Time.valueOf("07:00:00")
            college.friStartTime=Time.valueOf("07:00:00")
            college.satStartTime=Time.valueOf("07:00:00")
            college.sunStartTime=Time.valueOf("07:00:00")

            college.tueEndTime=Time.valueOf("14:00:00")
            college.wedEndTime=Time.valueOf("14:00:00")
            college.thurEndTime=Time.valueOf("14:00:00")
            college.friEndTime=Time.valueOf("14:00:00")
            college.satEndTime=Time.valueOf("14:00:00")
            college.sunEndTime=Time.valueOf("14:00:00")
           /* def data=College.findAll()
            if(data.size()!=0){
                def college2=College.findAll([sort: "id", order: "desc"])
                def coll1=[]

                coll1=college2.code


                for(def i=0;i<1;i++) {
                    def code = coll1[i]

                    print("code:"+code)

                    def code0 = Integer.parseInt(code, 2)
                    def sum = code0 + Integer.parseInt("1", 2)
                    def finalCode = Integer.toBinaryString(sum)


                    college1.code = finalCode
                }
            }
            else{
                college1.code="0"

            }*/
            college.validate()
            college.errors
            college.save(failOnError: true)

        }
        else{
            def college2=College.findByFacultyAndSubjectCode(faculty,"1111")
            for(def i=1;i<=collegeSize;i++){
                print("inside if ")
                def college3=College.findById(i)
                print("college:"+college3)
                def faculty1=college3.faculty
                def classesPerWeek=college3.classesPerWeek
                def teacherCode=college3.teacherCode
                if(faculty==faculty1 && teacherCode!="11111"){
                    count=count-classesPerWeek
                }

            }
            college2.classesPerWeek=count
            college2.tueStartTime=Time.valueOf("07:00:00")
            college2.wedStartTime=Time.valueOf("07:00:00")
            college2.thurStartTime=Time.valueOf("07:00:00")
            college2.friStartTime=Time.valueOf("07:00:00")
            college2.satStartTime=Time.valueOf("07:00:00")
            college2.sunStartTime=Time.valueOf("07:00:00")

            college2.tueEndTime=Time.valueOf("14:00:00")
            college2.wedEndTime=Time.valueOf("14:00:00")
            college2.thurEndTime=Time.valueOf("14:00:00")
            college2.friEndTime=Time.valueOf("14:00:00")
            college2.satEndTime=Time.valueOf("14:00:00")
            college2.sunEndTime=Time.valueOf("14:00:00")

           /* def data=College.findAll()
            if(data.size()!=0){
                def college2=College.findAll([sort: "id", order: "desc"])
                def coll1=[]

                coll1=college2.code


                for(def i=0;i<1;i++) {
                    def code = coll1[i]


                    print("code:"+code)

                    def code0 = Integer.parseInt(code, 2)
                    def sum = code0 + Integer.parseInt("1", 2)
                    def finalCode = Integer.toBinaryString(sum)

                    college1.code = finalCode
                }
            }
            else{
                college1.code="0"

            }*/
            college2.validate()
            college2.errors
            college2.save(failOnError: true)
        }

    }
}
