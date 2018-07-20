package arg

import java.sql.Time

class CollegeController {

   def enterData(){
       render(view: "enterData")
   }
    def enterData1(){
        def faculty=params.faculty
        def semester=Integer.parseInt(params.semester)

        render(view: "enterData1",model: [f:faculty,s:semester])
    }

    def addData(){
        def name=params.name
        def shortName=params.shortName
        def faculty=params.faculty
        def semester=Integer.parseInt(params.semester)
        def subject=params.subject
        def classesPerWeek=Integer.parseInt(params.classesPerWeek)
        def type=params.type
        def startTime= Time.valueOf(params.startTime)
        def endTime= Time.valueOf(params.endTime)

        def teacher=Teacher.findByShortName(shortName)
        def teacherCode=teacher.code

        def subject1=Subject.findByName(subject)
        def subjectCode=subject1.code


        College college=new College()

        college.teacher=name
        college.shortName=shortName
        college.faculty=faculty
        college.semester=semester
        college.subject=subject
        college.classesPerWeek=classesPerWeek
        college.type=type
        college.startTime=startTime
        college.endTime=endTime
        college.teacherCode=teacherCode
        college.subjectCode=subjectCode

        def data=College.findAll()
        if(data.size()!=0){
            def college1=College.findAll([sort: "id", order: "desc"])
            def coll1=[]
            coll1=college1.code

            for(def i=0;i<1;i++) {
                def code = coll1[i]
                print("code:"+code)

                def code0 = Integer.parseInt(code, 2)
                def sum = code0 + Integer.parseInt("1", 2)
                def finalCode = Integer.toBinaryString(sum)

                college.code = finalCode
            }
        }
        else{
            college.code="0"
        }

        college.save()

        redirect(action: "viewData")


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
}
