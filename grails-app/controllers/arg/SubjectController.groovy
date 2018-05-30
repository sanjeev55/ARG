package arg

import grails.converters.JSON

class SubjectController {

   def addSubject(){
       render(view: "addSubject")

   }
    def add(){
        def name=params.name
        def semester=params.semester
        def faculty=params.faculty

        Subject subject=new Subject()

        subject.name=name
        subject.semester=Integer.parseInt(semester)
        subject.faculty=faculty
        subject.save()

        redirect(action: "viewSubject")


    }
    def viewSubject(){
        def subject=Subject.findAll()
        def user=session.user
        render(view:"viewSubject",model: [s:subject,u:user])

    }
    def searchSubject(){
        render(view:"searchSubject")

    }
    def searchByName(){
        render(view: "searchByName")
    }
    def searchByName1(){
        def name=params.name

        def subject=Subject.findByName(name)
        def user=session.user
        render(view: "viewSubject", model: [s:subject,u:user])
    }
    def searchByFaculty(){
        render(view: "searchByFaculty")
    }
    def searchByFaculty1(){
        def faculty=params.faculty

        def subject=Subject.findAllByFaculty(faculty)
        def user=session.user
        render(view: "viewSubject",model: [s:subject,u:user])

    }
    def searchBySemester(){
        render(view: "searchBySemester")
    }
    def searchBySemester1(){
        def semester=params.semester
        def subject=Subject.findAllBySemester(Integer.parseInt(semester))
        def user=session.user
        render(view: "viewSubject",model: [s: subject,u: user])

    }
    def searchByFacultyAndSemester(){
        render(view: "searchByFacultyAndSemester")
    }
    def searchByFacultyAndSemester1(){
        def faculty=params.faculty
        def semester=params.semester

        def subject=Subject.findAllByFacultyAndSemester(faculty,Integer.parseInt(semester))
        def user=session.user
        render(view: "viewSubject",model:[s: subject,u:user])
    }
    def editSubject(){
        def id=params.id

        def subject=Subject.findById(Integer.parseInt(id))

        render(view: "editSubject",model:[s:subject])
    }
    def getSubjects(){
        def faculty=params.f
        def semester=Integer.parseInt(params.s)

        def subject=Subject.findAllByFacultyAndSemester(faculty,semester)

        render subject as JSON
    }
    def deleteSubject(){

            def id=params.id;


            def s=Subject.findById(Integer.parseInt(id))
            s.delete()

            redirect(action: "viewSubject")

    }
    def updateSubject(){
        def id=params.id

        def subject=Subject.findById(id)

        def name=params.name
        def faculty=params.faculty
        def semester=Integer.parseInt(params.semester)


        subject.name=name;
        subject.faculty=faculty;
        subject.semester=semester

        subject.save(failOnError: true)

        redirect(action: "viewSubject")
    }
}
