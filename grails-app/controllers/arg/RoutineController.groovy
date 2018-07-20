package arg

class RoutineController {

    def createChromosome(){
        def count=0
        def faculty
        while(count<4) {
            def myList = []//generation of four chromosome
            for (int k = 0; k <= 5; k++) { //for 6 days routine generation

                int id, j
                for (id = 0; id <= 2; id++) {//For three section
                    def faculty1 = Faculty.findById(id)
                    def fcode = faculty1.code
                    def semester= faculty1.semester
                    if(faculty1.className=="BEX") {
                        faculty = "BEX"
                        print("faculty:"+faculty)

                    }
                    else
                    {
                        faculty = "BCT"
                        print("faculty1:"+faculty)
                    }
                    for (j = 0; j <= 3; j++) { // for 4 time slots
                        Random rand = new Random()

                        int randnum = rand.nextInt(20)
                        while (randnum == 0) {
                            randnum = rand.nextInt(20)
                        }
                        print("randnum:"+randnum)


                        def college = College.findById(randnum)


                        def tcode = college.teacherCode
                        def scode = college.subjectCode

                        def faculty2=college.faculty
                        def semester2=college.semester
                        while(faculty!=faculty2&&semester!=semester2)
                        {
                            print("inside the loop")
                            randnum = rand.nextInt(20)
                            while (randnum == 0) {
                                randnum = rand.nextInt(20)
                            }

                            college = College.findById(randnum)
                            tcode = college.teacherCode
                            scode = college.subjectCode
                            faculty2=college.faculty
                            semester2=college.semester
                        }

                        def slot1 = Slot.findById(j)
                        def slcode = slot1.code
                        print("j:" + j)

                        def wholeCode = fcode + "." + tcode + "." + scode + "." + slcode
                        print(wholeCode)

                        myList = myList + [wholeCode]
                        def size = myList.size()
                        print("size:" + size)

                        print(myList)

                    }
                }
            }

                Chromosome chromosome = new Chromosome()
                chromosome.chromo = myList
                chromosome.save()






           def id1 = chromosome.id

          splitDna(id1)
           print("id" + id1)
            count++

        }
     /*   def chromo1=Chromosome.findById(5485)
        def chromo2=chromo1.chromo
        def size=chromo2.size()
        print("Total size:"+size)*/
     //   redirect(action: "crossOver")



    }
    def splitDna(def id){
        def id1=id
        def chromosome=Chromosome.findById(id)
        def size=chromosome.chromo.size()
        def chromos = chromosome.chromo.substring(1, size-1)

        def gene
        def dna
        gene = chromos.split(",")
        print("genesss;"+gene)
        for(def i=0;i<72;i++) { // 72 total genes

            print("gene" + [i] + ":" + gene[i].replaceAll("\\s",""))
            dna=gene[i].tokenize(".") //spliting gene to dna wrt "."
            for(def j=0;j<3;j++){
                printf("dna:"+dna[j].replaceAll("\\s",""))

            }
            Fitness fitness=new Fitness()
            fitness.token=i
            fitness.dnaC=dna[0]
            fitness.dnaT=dna[1]
            fitness.dnaS=dna[2]

            fitness.save()


        }
        calculateFitness(id1)
    }
    def calculateFitness(def id){
        def fitnessValueH=0

        fitnessValueH=hardConstraintH3()

        print("fitness1:"+fitnessValueH)
       // print("fitness2:"+fitnessValueH2)

       // fitnessValue1=softConstraint()


        def totalFitness=fitnessValueH


        def chromo=Chromosome.findById(id)
        chromo.fitness=totalFitness

        print("Total Fitness:"+totalFitness)

        Fitness.executeUpdate('delete from Fitness ')




    }
    def hardConstraintH1(){
        def fitnessValue=0
        def fitnessValueH2=0
        def dna=[]
        for(def i=0;i<72;i++) {
            def fitness = Fitness.findByToken(i)

            dna[i]=fitness.dnaT
            print("dna"+[i]+":"+dna[i])

        }
        def i,j,k,l;
        def ul;
        def ll=0;
        def initial=0;
        for(l=0;l<6;l++){       //routine of 6 days a week
            for(i=initial;i<initial+4;i++){
                ul=ll+8;
                for(j=ll;j<=ul;j=j+4){
                    for(k=j+4;k<=ul;k=k+4){     //+4 because same slot repeats after duration of 4
                        if(dna[j]==dna[k]){        //slot comparison for same day same period (teacher code)
                            fitnessValue=fitnessValue-5

                        }
                        else{
                            fitnessValue=fitnessValue+5
                        }
                    }
                }
                ll++;
            }
            ll=ll+8;                //+8 because difference in routine between one section and last section is 8 periods here (will increase with increase in section)
            initial=initial+12;     //+12 because routine of a day(eg sunday) consists of 12 dna (will increase will increase in section)
        }
        print("Fitness H1:"+fitnessValue)
        fitnessValueH2=hardConstraintH2(fitnessValue)
       return fitnessValueH2
    }

    def hardConstraintH2(def fitnessValue){
        def fitnessValueH3=0
        for(def i=0;i<72;i++){
            def code=Fitness.findByToken(i)
            def tcode=code.dnaT

            def college=College.findByTeacherCode(tcode)
            def startTime
            def endTime
            if(i>=0&&i<=11) {
                startTime = college.tueStartTime
                endTime = college.tueEndTime
            }
            else if(i>=12&&i<=23) {
                startTime = college.wedStartTime
                endTime = college.wedEndTime
            }
            else if(i>=24&&i<=35) {
                startTime = college.thurStartTime
                endTime = college.thurEndTime
            }
            else if(i>=36&&i<=47) {
                startTime = college.friStartTime
                endTime = college.friEndTime
            }
            else if(i>=48&&i<=59) {
                startTime = college.satStartTime
                endTime = college.satEndTime
            }
            else {
                startTime = college.sunStartTime
                endTime = college.sunEndTime
            }

            def value=i%4

            def slot=Slot.findById(value)

            def startTime1=slot.startTime
            def endTime1=slot.endTime

            if(startTime1>=startTime&&endTime1<=endTime){
                print("time available")
                print("StartTime1:"+startTime1)
                print("StartTime:"+startTime)
                print("EndTime:"+endTime)
                print("EndTime1:"+endTime1)
                fitnessValue=fitnessValue+5
                print("Fitness inside if:"+fitnessValue)

            }
            else{
                print("time not available")
                print("StartTime1:"+startTime1)
                print("StartTime:"+startTime)
                print("EndTime:"+endTime)
                print("EndTime:"+endTime1)
                fitnessValue=fitnessValue-5
                print("Fitness inside else:"+fitnessValue)
            }

        }
        print("fitnessH2:"+fitnessValue)

        fitnessValueH3=hardConstraintH3(fitnessValue)
        return fitnessValueH3
    }

    def hardConstraintH3(){
       /* def fitnessValue=0

        for (def i=0;i<3;i++){
            def className1
            def faculty=Faculty.findById(i)
            def classCode=faculty.code
            def className=faculty.className
            if(className=="BEX"){
                className1="BEX"
            }
            else
            {
                className1="BCT"
            }

            print("classcode:"+classCode)
            print("className:"+className1)
            for(def j=4;j<18;j++) {
                def subject = Subject.findById(j)
                def subjectCode = subject.code
                def subject1 = Subject.findAllByFacultyAndCode(className1, subjectCode)
                def size = subject1.size()
                print("size:"+size)
                if (size == 1){
                    print("classCode="+classCode)
                    print("subjectCode="+subjectCode)
                    def fitness1 = Fitness.findAllByDnaSAndDnaC(subjectCode,classCode)
                def size1 = fitness1.size()
                print("size1:" + size1)
                def college = College.findBySubjectCode(subjectCode)
                def classesPerWeek = college.classesPerWeek
                    print("classesPerWeek:"+classesPerWeek)

                if (size1 == classesPerWeek) {
                    fitnessValue = fitnessValue + 5
                    print("fitnessTrue:"+fitnessValue)
                } else {
                    fitnessValue = fitnessValue - 5
                    print("fitnessFalse:"+fitnessValue)
                }
            }
            }
        }
        print("Fintess123:"+fitnessValue)
        return fitnessValue*/
        def fitnessValue=0
        //def data= new String[100]
        def subject=Subject.findAll()
        def subjectSize=subject.size()      //To compare all subjects in database
        for(def i=0;i<subjectSize;i++){             //Loop to compare workload and allocated period
            def id=i+4                              //Because id begins with 4 in database
            def subject1=Subject.findById(id)       //Retriving subject from subject table (master database)
            def subjectCode=subject1.code           //Retriving associated subject code

            def college1=College.findAllBySubjectCode(subjectCode)  //Retriving subject code from college table (has workload information)
            def teacherCode=college1.teacherCode     //Retriving teacher because 1 subject can have mmultiple teacher
            for(def tc=0;tc<teacherCode.size();tc++){   //Comparing for workload using teacher and subject code
                def teacherCode1=teacherCode[tc]
                print("teacher"+tc+":"+teacherCode[tc])
                def fitness=Fitness.findAllByDnaS(subjectCode)
                def size=fitness.size()               //Retriving for workload from routine
                print("size"+size)

                def college= College.findBySubjectCodeAndTeacherCode(subjectCode,teacherCode1)
                def classesPerWeek=college.classesPerWeek       //Retriving for allocated workload
                print("classesperweek:"+classesPerWeek)

                if(size==classesPerWeek){
                    fitnessValue=fitnessValue+5
                    print("fitnessTrue:"+fitnessValue)
                }
                else{
                    fitnessValue=fitnessValue-5
                    print("fitnessFalse:"+fitnessValue)
                }
            }


            print("subjectCode:"+subjectCode)

        }
        print("fitnessValue123:"+fitnessValue)
        return fitnessValue
    }
    def softConstraint(){
        def fitnessValue1=0
        def dna=[]
        for(def i=0;i<72;i++) {
            def fitness = Fitness.findByToken(i)

            dna[i]=fitness.dna
            print("dna"+[i]+":"+dna[i])
        }
        def i=0
        def ll=i
        def ul=ll+3
        for(i=0;i<4;i++){
            for(def j=ll;j<ul;j++){
                for(def k=j+1;k<=ul;k++){
                    print("j:"+j+" "+"k:"+k)
                    if(dna[j]==dna[k]){
                        fitnessValue1=fitnessValue1-5

                    }
                    else{
                        fitnessValue1=fitnessValue1+5
                    }
                }
            }
            ll=ul+1
            ul=ll+3
        }
        return fitnessValue1
        print("fitness1:"+fitnessValue1)

    }
    def crossOver() {
        def optimumFitness = 0

        while (optimumFitness != 240) {

        def chromosome = Chromosome.findAll([sort: "fitness", order: "desc"])
        def chromo = []
        chromo = chromosome.chromo
        for (def i = 0; i < 2; i++) {
            print("Chromosome" + [i] + ":" + chromo[i])
            print("size:" + chromo.size())
            def chromos = chromo[i].substring(1, 159).replaceAll("\\s", "")
            def gene
            def dna
            gene = chromos.split(",")

            for (def j = 0; j < 16; j++) {

                print("gene" + [j] + ":" + gene[j].replaceAll("\\s", ""))
                dna = gene[j].tokenize(".")
                for (def k = 0; k < 3; k++) {
                    printf("dna:" + dna[k].replaceAll("\\s", ""))

                }
                if (i == 0) {
                    Fitness1 fitness1 = new Fitness1()
                    fitness1.token = j
                    fitness1.dna0 = dna[0]
                    fitness1.dna1 = dna[1]
                    fitness1.dna2 = dna[2]

                    fitness1.save()
                } else {
                    Fitness2 fitness2 = new Fitness2()
                    fitness2.token = j
                    fitness2.dna0 = dna[0]
                    fitness2.dna1 = dna[1]
                    fitness2.dna2 = dna[2]

                    fitness2.save()
                }
            }


        }
       /* Random rand = new Random()
        def randnum = rand.nextInt(16)
        print("randnum:" + randnum)
        print(rand)
        for (def i = 0; i < randnum; i++) {
            Random random = new Random()
            def randnum1 = random.nextInt(16)
            print("randnum1:" + randnum1)
            def dna2
            def dna1
            def temp
            def f1dna = Fitness1.findByToken(randnum1)
            dna1 = f1dna.dna1
            print("dna1:" + dna1)

            def f2dna = Fitness2.findByToken(randnum1)
            dna2 = f2dna.dna1
            print("dna2:" + dna2)

            temp = dna1
            dna1 = dna2
            dna2 = temp
            print("dna1:" + dna1)
            print("dna2:" + dna2)*/
           //Random r=new Random()
            Random rand = new Random()
            def randnum = rand.nextInt(16)
            print("randnum:" + randnum)
            print(rand)
            for (def i = 0; i < 16; i++) {
                //we will take 16 iterations taking each gene at a time but whether crossover happens or not depends on crossover probability
                Random random = new Random()
                def randnum1 = random.nextInt(3)        //crossover probability is 1/3
                //print("randnum1:" + randnum1)
                def dna2
                def dna1
                def temp
                def f1dna = Fitness1.findByToken(i)        // genes are withdrawn serially from db
                dna1 = f1dna.dna1
                print("dna1:" + dna1)

                def f2dna = Fitness2.findByToken(i)
                dna2 = f2dna.dna1
                print("dna2:" + dna2)
                if (randnum1 % 2 == 0) {                //cross over with probability 1/3
                    temp = dna1
                    dna1 = dna2
                    dna2 = temp
                }

                Random r=new Random()
            def r1=r.nextInt(9)
            def r2=r.nextInt(99)
           /* if(r2%2==1) {
                if (r1 % 2 == 0) {
                    f1dna.dna1 = dna1.replaceAll("0", "1")

                    f2dna.dna1 = dna2.replaceAll("1", "0")
                }
                else
                {
                    f1dna.dna1=dna1.replaceAll("1","0")
                    f2dna.dna1=dna2.replaceAll("0","1")
                }
            }
                else {

                f1dna.dna1 = dna1
                f2dna.dna1 = dna2
            }*/
            if(r2%100==0) {		//mutation rate=1% and r2 is a random number between 0 to 99
                if (r1%10==0) {		//10 types of mutation each with equal probability of occurence r1 is a random number between 0 to 9
                    f1dna.dna1=dna1.replaceAll("0","1")
                }
                else if (r1%10==1){
                    f1dna.dna1=dna1.replaceAll("1","0")
                }
                else if (r1%10==2){
                    f2dna.dna1=dna2.replaceAll("0","1")
                }
                else if (r1%10==3){
                    f2dna.dna1=dna2.replaceAll("1","0")
                }
                else if (r1%10==4){
                    f1dna.dna1=dna1.replaceAll("0","1")
                    f1dna.dna1=dna1.replaceAll("1","0")
                }
                else if (r1%10==5){
                    f1dna.dna1=dna1.replaceAll("0","1")
                    f2dna.dna2=dna2.replaceAll("0","1")
                }
                else if (r1%10==6){
                    f1dna.dna1=dna1.replaceAll("0","1")
                    f2dna.dna1=dna2.replaceAll("1","0")
                }
                else if (r1%10==7){
                    f2dna.dna1=dna2.replaceAll("0","1")
                    f2dna.dna1=dna2.replaceAll("1","0")
                }
                else if (r1%10==8){
                    f1dna.dna1=dna1.replaceAll("1","0")
                    f2dna.dna1=dna1.replaceAll("0","1")
                }
                else{
                    f1dna.dna1=dna1.replaceAll("1","0")
                    f2dna.dna1=dna2.replaceAll("1","0")
                }
            }

            else {

                f1dna.dna1 = dna1
                f2dna.dna1 = dna2
            }

            f1dna.save()
            f2dna.save()


        }
        def hfitnessValue1 = hardConstraint1()
        def sfitnessValue1 = softConstraint1()

        def hfitnessValue2 = hardConstraint2()
        def sfitnessValue2 = softConstraint2()

        def totalFitness1 = hfitnessValue1 + sfitnessValue1
        def totalFitness2 = hfitnessValue2 + sfitnessValue2



        createChildChromosome(totalFitness1, totalFitness2)
            if(totalFitness1>totalFitness2){
                optimumFitness=totalFitness1
            }
            else
            {
                optimumFitness=totalFitness2
            }

            print("This inside where loop")
            print("optimum:"+optimumFitness)

    }
        print("optimum:"+optimumFitness)
        print("outside where loop")
        if(optimumFitness==240) {
            redirect(action: "storeCode")

        }

    }
    def hardConstraint1(){
        def fitnessValue=0
        def dna=[]
        for(def i=0;i<16;i++) {
            def fitness1 = Fitness1.findByToken(i)

            dna[i]=fitness1.dna1


        }
        def i
        def ul
        def ll=0
        for(i=0;i<4;i++){
            ul=ll+12
            for(def j=ll;j<=ul;j=j+4){
                for(def k=j+4;k<=ul;k=k+4){

                    if(dna[j]==dna[k]){
                        fitnessValue=fitnessValue-5

                    }
                    else{
                        fitnessValue=fitnessValue+5
                    }
                }
            }
            ll++
        }
        return fitnessValue
        print("fitness:"+fitnessValue)
    }
    def softConstraint1(){
        def fitnessValue1=0
        def dna=[]
        for(def i=0;i<16;i++) {
            def fitness1 = Fitness1.findByToken(i)

            dna[i]=fitness1.dna1

        }
        def i=0
        def ll=i
        def ul=ll+3
        for(i=0;i<4;i++){
            for(def j=ll;j<ul;j++){
                for(def k=j+1;k<=ul;k++){

                    if(dna[j]==dna[k]){
                        fitnessValue1=fitnessValue1-5

                    }
                    else{
                        fitnessValue1=fitnessValue1+5
                    }
                }
            }
            ll=ul+1
            ul=ll+3
        }
        return fitnessValue1
        print("fitness1:"+fitnessValue1)

    }
    def hardConstraint2(){
        def fitnessValue=0
        def dna=[]
        for(def i=0;i<16;i++) {
            def fitness2 = Fitness2.findByToken(i)

            dna[i]=fitness2.dna1


        }
        def i
        def ul
        def ll=0
        for(i=0;i<4;i++){
            ul=ll+12
            for(def j=ll;j<=ul;j=j+4){
                for(def k=j+4;k<=ul;k=k+4){

                    if(dna[j]==dna[k]){
                        fitnessValue=fitnessValue-5

                    }
                    else{
                        fitnessValue=fitnessValue+5
                    }
                }
            }
            ll++
        }
        return fitnessValue
        print("fitness:"+fitnessValue)
    }
    def softConstraint2(){
        def fitnessValue1=0
        def dna=[]
        for(def i=0;i<16;i++) {
            def fitness2 = Fitness2.findByToken(i)

            dna[i]=fitness2.dna1

        }
        def i=0
        def ll=i
        def ul=ll+3
        for(i=0;i<4;i++){
            for(def j=ll;j<ul;j++){
                for(def k=j+1;k<=ul;k++){

                    if(dna[j]==dna[k]){
                        fitnessValue1=fitnessValue1-5

                    }
                    else{
                        fitnessValue1=fitnessValue1+5
                    }
                }
            }
            ll=ul+1
            ul=ll+3
        }
        return fitnessValue1
        print("fitness1:"+fitnessValue1)

    }

    def createChildChromosome(def totalFitness1, def totalFitness2){
        def wholeCode
        def myList=[]
        def wholeCode1
        def myList1=[]
            for(def i=0;i<16;i++) {
                def fitness1 = Fitness1.findByToken(i)
                def dna0 = fitness1.dna0
                def dna1 = fitness1.dna1
                def dna2 = fitness1.dna2

                wholeCode=dna0+"."+dna1+"."+dna2

                myList=[wholeCode]+myList
            }

            Chromosome chromosome=new Chromosome()
            chromosome.chromo=myList
            chromosome.fitness=totalFitness1
            print("mylist:"+myList)
            chromosome.save()
            Fitness1.findAll().each {it.delete(flush:true, failOnError:true)}

        for(def i=0;i<16;i++) {
            def fitness2 = Fitness2.findByToken(i)
            def dna0 = fitness2.dna0
            def dna1 = fitness2.dna1
            def dna2 = fitness2.dna2

            wholeCode1=dna0+"."+dna1+"."+dna2

            myList1=[wholeCode1]+myList1
        }

        Chromosome chromosome1=new Chromosome()
        chromosome1.chromo=myList1
        chromosome1.fitness=totalFitness2
        print("mylist1:"+myList1)

        chromosome1.save()
        Fitness2.findAll().each {it.delete(flush:true, failOnError:true)}


    }
    def storeCode(){

        def chromosome=Chromosome.findByFitness(240)
        def chromos = chromosome.chromo.substring(1, 159)
        def gene
        def dna
        gene = chromos.split(",")
        print("genes;"+gene)
        for(def i=0;i<16;i++) {

            print("gene" + [i] + ":" + gene[i].replaceAll("\\s", ""))
            dna = gene[i].tokenize(".")
            for (def j = 0; j < 3; j++) {
                printf("dna:" + dna[j].replaceAll("\\s", ""))

            }
            FinalDna finalDna=new FinalDna()
            finalDna.time=dna[0]
            finalDna.teacherSubject=dna[1]
            finalDna.section=dna[2]
            finalDna.token=i


            finalDna.save()

        }
        redirect(action: 'storeActualData')

//
    }
    def storeActualData(){
        for(def i=0;i<16;i++){
            def finalDna=FinalDna.findByToken(i)
            def time=finalDna.time.replaceAll("\\s","")
            print("time:"+time)
            def teacherSubject=finalDna.teacherSubject
            print("TeacherSubject:"+teacherSubject)
            def section=finalDna.section
            print("section:"+section)

            def slot=Slot.findByCode(time)
            print("startTime:"+slot.startTime)
            def startTime=slot.startTime
            def endTime=slot.endTime

            def teacher=Teacher.findByCode(teacherSubject)
            def name=teacher.name
            def subject=teacher.subject

            def faculty=Faculty.findByCode(section)

            def section1=faculty.section

            ActualData actualData=new ActualData()

            actualData.startTime=startTime
            actualData.endTime=endTime
            actualData.name=name
            actualData.subject=subject
            actualData.section=section1

            actualData.save()
        }
        redirect(action: "viewRoutine")

    }

    def viewRoutine(){
        def actualDataA=ActualData.findAllBySection("A")
        def actualDataB=ActualData.findAllBySection("B")
        def actualDataC=ActualData.findAllBySection("C")
        def actualDataD=ActualData.findAllBySection("D")

        render(view: "result", model:[a:actualDataA,b:actualDataB,c:actualDataC,d:actualDataD])


    }




}
