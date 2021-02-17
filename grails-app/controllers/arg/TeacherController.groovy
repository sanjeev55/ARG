package arg

import java.sql.Time

class TeacherController {

    def addTeacher(){
        render(view: "addTeacher")
    }

    def add(){
        def name=params?.name
        def address=params?.address
        def phoneNumber=params?.phoneNumber
        def specialization=params?.specialization
        def type=params?.type
        def shortName=params?.shortName
        if(name == "" || address == "" || phoneNumber=="" || specialization==""||type==""||shortName==""){
            redirect(action: "addTeacher")
        }
        else {
        def tueStartTime= Time?.valueOf(params.tueStartTime)
        def tueEndTime= Time?.valueOf(params.tueEndTime)
        def wedStartTime= Time?.valueOf(params.wedStartTime)
        def wedEndTime= Time?.valueOf(params.wedEndTime)
        def thurStartTime= Time?.valueOf(params.thurStartTime)
        def thurEndTime= Time?.valueOf(params.thurEndTime)
        def friStartTime= Time?.valueOf(params.friStartTime)
        def friEndTime= Time?.valueOf(params.friEndTime)
        def satStartTime= Time?.valueOf(params.satStartTime)
        def satEndTime= Time?.valueOf(params.satEndTime)
        def sunStartTime= Time?.valueOf(params.satStartTime)
        def sunEndTime= Time?.valueOf(params.satEndTime)


            Teacher teacher = new Teacher()

            teacher.name = name
            teacher.address = address
            teacher.phoneNumber = phoneNumber
            teacher.specialization = specialization
            teacher.type = type
            teacher.shortName = shortName
            teacher.tueStartTime = tueStartTime
            teacher.tueEndTime = tueEndTime
            teacher.wedStartTime = wedStartTime
            teacher.wedEndTime = wedEndTime
            teacher.thurStartTime = thurStartTime
            teacher.thurEndTime = thurEndTime
            teacher.friStartTime = friStartTime
            teacher.friEndTime = friEndTime
            teacher.satStartTime = satStartTime
            teacher.satEndTime = satEndTime
            teacher.sunStartTime = sunStartTime
            teacher.sunEndTime = sunEndTime



            def data = Teacher.findAll()
            if (data.size() != 0) {
                def teacher1 = Teacher.findAll([sort: "id", order: "desc"])
                def teach1 = []
                teach1 = teacher1.code

                for (def i = 0; i < 1; i++) {
                    def code = teach1[i]
                    print("code:" + code)

                    def code0 = Integer.parseInt(code, 2)
                    def sum = code0 + Integer.parseInt("1", 2)
                    def finalCode = Integer.toBinaryString(sum)

                    teacher.code = finalCode
                }
            } else {
                teacher.code = "0"
            }

            teacher.save(failOnError: true)

            redirect(action: "viewTeacher")


        }

    }
    def viewTeacher(){
        def teacher=Teacher?.findAll()
        def user= session.user
        render(view: "viewTeacher",model:[t:teacher,u:user] )


    }
    def editTeacher(){
        def id=params.id

        def teacher=Teacher.findById(Integer.parseInt(id))

        render(view: "editTeacher",model:[t:teacher])

    }
    def updateTeacher(){
        def id=params.id;

        def teacher=Teacher.findById(Integer.parseInt(id))

        def name=params?.name
        def shortName=params?.shortName
        def address=params?.address
        def phoneNumber=params?.phoneNumber
        def specialization=params?.specialization
        def type=params?.type


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
    def getTueStartTime(){

        def name=params.n
        print("name:"+name)
        def teacher=Teacher.findByName(name)

        def tueStartTime=teacher.tueStartTime

        render tueStartTime
    }
    def getWedStartTime(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def wedStartTime=teacher.wedStartTime

        render wedStartTime
    }
    def getThurStartTime(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def thurStartTime=teacher.thurStartTime

        render thurStartTime
    }
    def getFriStartTime(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def friStartTime=teacher.friStartTime

        render friStartTime
    }
    def getSatStartTime(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def satStartTime=teacher.satStartTime

        render satStartTime
    }
    def getSunStartTime(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def sunStartTime=teacher.sunStartTime

        render sunStartTime
    }
    def getTueEndTime(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def tueEndTime=teacher.tueEndTime

        render tueEndTime
    }
    def getWedEndTime(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def wedEndTime=teacher.wedEndTime

        render wedEndTime
    }
    def getThurEndTime(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def thurEndTime=teacher.thurEndTime

        render thurEndTime
    }
    def getFriEndTime(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def friEndTime=teacher.friEndTime

        render friEndTime
    }
    def getSatEndTime(){
        def name=params.n

        def teacher=Teacher.findByName(name)

        def satEndTime=teacher.satEndTime

        render satEndTime
    }
    def getSunEndTime(){

        def name=params.n

        def teacher=Teacher.findByName(name)

        def sunEndTime=teacher.sunEndTime

        render sunEndTime
    }
}
