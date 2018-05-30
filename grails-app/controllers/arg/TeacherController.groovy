package arg

class TeacherController {

    def addTeacher(){
        render(view: "addTeacher")
    }

    def add(){
        def name=params.name
        def address=params.address
        def phoneNumber=params.phoneNumber
        def specialization=params.specialization
        def type=params.type
        def shortName=params.shortName

        Teacher teacher=new Teacher()

        teacher.name=name
        teacher.address=address
        teacher.phoneNumber=phoneNumber
         teacher.specialization=specialization
        teacher.type=type
        teacher.shortName=shortName

       teacher.save();

        redirect(action: "viewTeacher")





    }
    def viewTeacher(){
        def teacher=Teacher.findAll()
        def user= session.user
        render(view: "viewTeacher",model:[t:teacher,u:user] )


    }
    def editTeacher(){
        def id=params.id;

        def teacher=Teacher.findById(Integer.parseInt(id))

        render(view: "editTeacher",model:[t:teacher])

    }
    def updateTeacher(){
        def id=params.id;

        def teacher=Teacher.findById(Integer.parseInt(id))

        def name=params.name
        def shortName=params.shortName
        def address=params.address
        def phoneNumber=params.phoneNumber
        def specialization=params.specialization
        def type=params.type


        teacher.name=name
        teacher.shortName=shortName
        teacher.address=address
        teacher.phoneNumber=phoneNumber
        teacher.specialization=specialization
        teacher.type=type
        teacher.save(failOnError: true)

        redirect(action: "viewTeacher")

    }

    def searchByName(){
        render(view: "searchByName")
    }
    def searchByName1(){
        def name=params.name

        def teacher=Teacher.findByName(name)
        def user=session.user
        render(view: "viewTeacher", model: [t:teacher,u:user])
    }

    def deleteTeacher(){
        def id=params.id;


        def t=Teacher.findById(Integer.parseInt(id))
        t.delete()

        redirect(action: "viewTeacher")
    }

    def getType(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def type=teacher.type


        render type
    }
    def getShortName(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def shortName=teacher.shortName


        render shortName
    }
}
