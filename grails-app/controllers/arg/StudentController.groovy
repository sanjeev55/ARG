package arg

class StudentController {

    def addInfo(){
        render(view: "addInfo")

    }
    def addInfo1(){
        def faculty=params.faculty
        def semester=params.semester
        def noOfStudents=params.noOfStudents

        Student student=new Student()

        student.faculty=faculty
        student.semester=Integer.parseInt(semester)
        student.noOfStudents=Integer.parseInt(noOfStudents)

        student.save()

        redirect(action: "viewInfo")

    }
    def viewInfo(){
        def students=Student.findAll();
        def user=session.user
        render(view: "viewInfo",model: [s:students,u:user])
    }
    def editInfo(){
        def id=Integer.parseInt(params.id)

        def students=Student.findById(id)

        render(view: "editInfo", model: [s:students])
    }
    def updateInfo(){
        def id=Integer.parseInt(params.id)

        def students=Student.findById(id)

        def faculty=params.faculty
        def semester=params.semester
        def noOfStudents=params.noOfStudents

        students.faculty=faculty
        students.semester=Integer.parseInt(semester)
        students.noOfStudents=Integer.parseInt(noOfStudents)

        students.save()

        redirect(action: "viewInfo")
    }

    def deleteInfo(){
        def id=Integer.parseInt(params.id)

        def students=Student.findById(id)

        students.delete()

        redirect(action: "viewInfo")


    }
}
