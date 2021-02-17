package arg

import org.bouncycastle.jce.provider.JCEBlockCipher
import java.sql.Time
import jxl.Workbook
import jxl.write.Label
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
class RoutineController {
    def collegeThreshold=College.findAll()
    def thresholdFinal=720+720+collegeThreshold.size()*10-100
    def threshold=thresholdFinal

    def selectYear(){
        ActualData.findAll().each {it.delete(flush:true, failOnError:true)}
        FinalDna.findAll().each {it.delete(flush:true, failOnError:true)}
        render(view:"selectYear")
    }

    def createChromosome(){
        def chromosomeCreationCount=0
        while(chromosomeCreationCount<6){
            def chromosomeList=[]
            def locationCount=0
            for(def dayCount=0;dayCount<6;dayCount++){
                for(def sectionCount=0;sectionCount<3;sectionCount++) {

                    for (def slotCount = 0; slotCount < 4; slotCount++) {
                        def getFaculty = Faculty.findById(sectionCount)
                        def facultyName_slot = getFaculty.className
                        print("faculty name slot:"+facultyName_slot)
                        def leisureCount = 0
                        def token = 1
                        while (token == 1){
                            def getSlotObj = Slot.findById(slotCount)
                        def slotCode = getSlotObj.code
                        def StartTime = getSlotObj.startTime
                        def EndTime = getSlotObj.endTime
                        def collegeObj = College.findAll()
                        def collegeSize = collegeObj.size()
                        Random obj = new Random()
                        def reference = obj.nextInt(collegeSize)
                        reference = reference + 1
                        def getCollegeData = College.findById(reference)
                        def facultyName_db = getCollegeData.faculty
                            print("Faculty Db:"+facultyName_db)
                        def determineFacultyCode = Faculty.findByClassName(facultyName_db)
                        def facultyCode = determineFacultyCode.code
                        def scode = getCollegeData.subjectCode
                        print("subject code:" + scode)
                        if (scode == "1111" || scode == "10001" || scode == "10000") {
                            leisureCount = leisureCount + 1
                            print("leisureCount:" + leisureCount)
                            //slotCount = slotCount - 1
                            print("inside if condition")
                        } else if ((scode != "1111" && scode != "10001" && scode != "10000") || leisureCount > 10) {
                            print("inside else condition")
                            /*  def teacherStartTime
                        def teacherEndTime*/
                            if (facultyName_slot == facultyName_db) {

                            def teacherStartTime = getCollegeData.tueStartTime
                            def teacherEndTime = getCollegeData.tueEndTime

                            if (locationCount >= 12 && locationCount <= 23) {
                                teacherStartTime = getCollegeData.wedStartTime
                                teacherEndTime = getCollegeData.wedEndTime
                            } else if (locationCount >= 24 && locationCount <= 35) {
                                teacherStartTime = getCollegeData.thurStartTime
                                teacherEndTime = getCollegeData.thurEndTime
                            } else if (locationCount >= 36 && locationCount <= 47) {
                                teacherStartTime = getCollegeData.friStartTime
                                teacherEndTime = getCollegeData.friEndTime
                            } else if (locationCount >= 48 && locationCount <= 59) {
                                teacherStartTime = getCollegeData.satStartTime
                                teacherEndTime = getCollegeData.satEndTime
                            } else {
                                teacherStartTime = getCollegeData.sunStartTime
                                teacherEndTime = getCollegeData.sunEndTime
                            }
                            print("Teacher available time:" + teacherStartTime + " " + teacherEndTime)
                            print("Slot time              " + StartTime + " " + EndTime)
                            def allocatedRepetition = getCollegeData.classesPerWeek
                           // def dbRepetition = getCollegeData.occurance
                            //print("dbrepetition before facultycomparison:" + dbRepetition)
                                print("Same faculty")
                                if ((teacherStartTime.before(StartTime) || teacherStartTime.equals(StartTime)) && (teacherEndTime.after(EndTime) || teacherEndTime.equals(EndTime))) {
                                    print("Within Available Time")
                                    print("Allocated workload " + allocatedRepetition)
                                   // print("Occurence: " + dbRepetition)
                                    //if (dbRepetition < (allocatedRepetition + 1)) {
                                        print("Within workload")
                                        def cSubjectCode = getCollegeData.subjectCode
                                        def cTeacherCode = getCollegeData.teacherCode
                                        locationCount=0
                                       /* dbRepetition = dbRepetition + 1
                                        getCollegeData.occurance = dbRepetition*/
                                        getCollegeData.save()
                                        def gene = facultyCode + "." + cTeacherCode + "." + cSubjectCode + "." + slotCode
                                        chromosomeList = chromosomeList + [gene]
                                        print("Gene formed :"+chromosomeList)
                                        break;
                                    //}
                                }
                            }

                        } else {
                            slotCount = slotCount - 1

                        }
                    }
                    }
                }
            }
            Chromosome chromosome = new Chromosome()
            chromosome.chromo = chromosomeList
            chromosome.save()

            def id1 = chromosome.id

            splitDna(id1)
            // print("id" + id1)
            chromosomeCreationCount++
        }
        redirect(action: "crossOver")
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
        def fitnessValueS=0

        fitnessValueH=hardConstraintH1()
        fitnessValueS=softConstraintS1()

        print("fitnessHard:"+fitnessValueH)
        print("fitnessSoft:"+fitnessValueS)



        def totalFitness=fitnessValueH+fitnessValueS
         print("Total Fitness:"+totalFitness)


        def chromo=Chromosome.findById(id)
        chromo.fitness=totalFitness
        chromo.fitnessHard=fitnessValueH
        chromo.fitnessSoft=fitnessValueS



      Fitness.executeUpdate('delete from Fitness ')




    }
    def hardConstraintH1(){
        def fitnessValue=0
        def fitnessValueH2=0
        def dna=[]
        for(def i=0;i<72;i++) {
            def fitness = Fitness.findByToken(i)

            dna[i]=fitness.dnaT
            //print("dna"+[i]+":"+dna[i])

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
                        if(dna[j]==dna[k]&&dna[j]!="11111"){        //slot comparison for same day same period (teacher code)
                            fitnessValue=fitnessValue//-5

                        }
                        else{
                            fitnessValue=fitnessValue+10
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
        for(def i=0;i<72;i++) {
            def code = Fitness.findByToken(i)
            def tcode = code.dnaT
            if (tcode != "11111") {
                def college = College.findByTeacherCode(tcode)
                def startTime
                def endTime
                if (i >= 0 && i <= 11) {
                    //0-11 denotes 12 components of chromosome which represents routine of tuesday for 3 sections
                    startTime = college.tueStartTime
                    endTime = college.tueEndTime
                } else if (i >= 12 && i <= 23) {
                    startTime = college.wedStartTime
                    endTime = college.wedEndTime
                } else if (i >= 24 && i <= 35) {
                    startTime = college.thurStartTime
                    endTime = college.thurEndTime
                } else if (i >= 36 && i <= 47) {
                    startTime = college.friStartTime
                    endTime = college.friEndTime
                } else if (i >= 48 && i <= 59) {
                    startTime = college.satStartTime
                    endTime = college.satEndTime
                } else {
                    startTime = college.sunStartTime
                    endTime = college.sunEndTime
                }

                def value = i % 4

                def slot = Slot.findById(value)

                def startTime1 = slot.startTime
                def endTime1 = slot.endTime

                if (startTime1 >= startTime && endTime1 <= endTime) {
                 /*   print("time available")
                    print("StartTime1:" + startTime1)
                    print("StartTime:" + startTime)
                    print("EndTime:" + endTime)
                    print("EndTime1:" + endTime1)*/
                    fitnessValue = fitnessValue + 10
                   //print("Fitness inside if:" + fitnessValue)

                } else {
                   /* print("time not available")
                    print("StartTime1:" + startTime1)
                    print("StartTime:" + startTime)
                    print("EndTime:" + endTime)
                    print("EndTime:" + endTime1)*/
                    fitnessValue = fitnessValue //- 5
                    print("Fitness inside else:" + fitnessValue)
                }

            }
            else{
                fitnessValue=fitnessValue+10
            }
        }
      print("fitnessH2:"+fitnessValue)

       fitnessValueH3=hardConstraintH3(fitnessValue)
        return fitnessValueH3
    }

    def hardConstraintH3(def fitnessValue){


        def collegeWorkLoad=College.findAll()
        def size=collegeWorkLoad.size()
        for(def count=0;count<size;count++){
            def comparisonCount=0;      //This stores the number of occurence of the subject
            def collegeWorkLoad1=College.findById(count+1)
            def teacherCode=collegeWorkLoad1.teacherCode
            def subjectCode=collegeWorkLoad1.subjectCode
            def facultyCode1=collegeWorkLoad1.faculty
            def facultyCodeClass=Faculty.findByClassName(facultyCode1)
            def facultyCode=facultyCodeClass.code
            def workAllocated=collegeWorkLoad1.classesPerWeek
            for(def loop=0;loop<72;loop++){
                def comparisonCollege=Fitness.findByToken(loop)       //retrieve gene from fitness1 table
                def rteacherCode=comparisonCollege.dnaT
                def rsubjectCode=comparisonCollege.dnaS
                def rfacultyCode=comparisonCollege.dnaC
                if((rteacherCode!=teacherCode)||(rsubjectCode!=subjectCode)||(rfacultyCode!=facultyCode)){

                }
                else{
                    comparisonCount++
                }

            }
            if((workAllocated==comparisonCount-1)||(workAllocated==comparisonCount)||(workAllocated==comparisonCount+1)){
                fitnessValue=fitnessValue+10
            }
            else{
                def multiplier
                if(comparisonCount<workAllocated){
                    multiplier=workAllocated-comparisonCount
                }
                else{
                    multiplier=comparisonCount-workAllocated
                }
                fitnessValue=fitnessValue-5*multiplier
            }
        }
        print("fitness H3:"+fitnessValue)
        return fitnessValue
    }
    def softConstraintS1(){
        def fitnessValue=0
        def dna=[]
        for(def i=0;i<72;i++) {
            def fitness = Fitness.findByToken(i)

            dna[i]=fitness.dnaS
            //print("dna"+[i]+":"+dna[i])
        }
        def i=0
        def ll=i
        def ul=ll+3
        for(i=0;i<18;i++){
            for(def j=ll;j<ul;j++){
                for(def k=j+1;k<=ul;k++){
                    //print("j:"+j+" "+"k:"+k)
                    if(dna[j]==dna[k]){
                        if(dna[j]=="1111")
                        {
                            fitnessValue=fitnessValue+10
                        }
                        else {
                            fitnessValue = fitnessValue //- 5
                        }
                    }
                    else{
                        fitnessValue=fitnessValue+10
                    }
                }
            }
            ll=ul+1
            ul=ll+3
        }
        print("fitnessS1:"+fitnessValue)
        def fitnessvalueS1=softConstraintS2(fitnessValue)
        return fitnessvalueS1


    }
    def softConstraintS2(def fitnessValue){

        def insignificantLocation1=0    //leisure period on 1st and last (4th) is not significant
        def insignificantLocation2=3
        //def leisure1="10000"
        def leisure1="1111"
        def i,j
        def k=0
        for(i=0;i<18;i++){
            for(j=k;j<k+4;j++){
                    def fitness = Fitness.findByToken(j)
                    def subjectCode = fitness.dnaS
                   // print("subjectCode:"+subjectCode)
                    if (subjectCode.equals(leisure1)){
                        if(j!=insignificantLocation1&&j!=insignificantLocation2) {
                        def fitness1=Fitness.findByToken(j-1)
                        def subjectCode1=fitness1.dnaS//Subject code one step behind location j
                       // print("subjectCode1:"+subjectCode1)
                        def fitness2=Fitness.findByToken(j+1)
                        def subjectCode2=fitness2.dnaS//Subject code one step ahead location j
                      //  print("subjectCode2:"+subjectCode2)
                        if((subjectCode1!=leisure1) && (subjectCode2!=leisure1)){
                            fitnessValue=fitnessValue//-5
                           // print("fitness negative:"+fitnessValue)
                        }
                        else{
                            fitnessValue=fitnessValue+10
                          // print("fitness Positive:"+fitnessValue)
                        }
                    }
                }
            }
            k=k+4
            insignificantLocation1=insignificantLocation1+4
            insignificantLocation2=insignificantLocation2+4
        }
        print("fitnessS2:"+fitnessValue)
        return fitnessValue as Integer
    }
    def crossOver() {
        def optimumFitness = 0

        while (optimumFitness<threshold) {

        def chromosome = Chromosome.findAll([sort: "fitness", order: "desc"])
        def chromo = []
            def fitness=[]
        chromo = chromosome.chromo
            fitness=chromosome.fitness
        for (def i = 0; i < 2; i++) {
            print("fitnessHIghhh:"+fitness[i])
            print("Chromosome" + [i] + ":" + chromo[i])
            print("size:" + chromo.size())
            def chromos = chromo[i].substring(1, chromo[i].size()-1).replaceAll("\\s", "")

            def gene
            def dna
            gene = chromos.split(",")

            for (def j = 0; j < 72; j++) {

                print("gene" + [j] + ":" + gene[j].replaceAll("\\s", ""))
                dna = gene[j].tokenize(".")//split wrt "."
                for (def k = 0; k < 4; k++) {
                    printf("dna:" + dna[k].replaceAll("\\s", ""))

                }
                if (i == 0) {
                    Fitness1 fitness1 = new Fitness1()
                    fitness1.token = j
                    fitness1.dnaF = dna[0]
                    fitness1.dnaT = dna[1]
                    fitness1.dnaS = dna[2]
                    fitness1.dnaSl = dna[3]

                    fitness1.save()

                } else {
                    Fitness2 fitness2 = new Fitness2()
                    fitness2.token = j
                    fitness2.dnaF = dna[0]
                    fitness2.dnaT = dna[1]
                    fitness2.dnaS = dna[2]
                    fitness2.dnaSl = dna[3]

                    fitness2.save()
                }
            }
            if(i==0) {
                 hardConstraintF1()
            }
            else {
                 hardConstraintF2()
            }
        }

            Random rand = new Random()
            def randnum = rand.nextInt(72)
            print("randnum:" + randnum)
            //print(rand)
            Random random = new Random()
            def seed1=random.nextInt(100)
            def seed2=random.nextInt(100)
            for (def i = 0; i < 72; i++) {
                //we will take 16 iterations taking each gene at a time but whether crossover happens or not depends on crossover probability
                def randnum1 = random.nextInt(10)
                def multiplier=25
                def increment=1
                def modulus=72
                def randnumFitness1=(multiplier*seed1+increment)%modulus  //newly added for new crossover
                def randnumFitness2=(multiplier*seed2+increment)%modulus    //Linear congruential method to generate random numbers from 0 to 71 without repeatation
                seed1=randnumFitness1
                seed2=randnumFitness2
                //print("randnum1:" + randnum1)
                def dnaT2
                def dnaT1
                def dnaS1
                def dnaS2
                def dnaF1
                def dnaF2
                def temp1,temp2
                //def f1dna = Fitness1.findByToken(i)        // genes are withdrawn serially from db //previous part
                //dnaT1 = f1dna.dnaT
                //dnaS1 = f1dna.dnaS
               /* def indicator1=f1dna.indicator
                print("dnaT1:" + dnaT1)
                print("dnaS1:" + dnaS1)

                def f2dna = Fitness2.findByToken(i)
                dnaT2 = f2dna.dnaT
                dnaS2 = f2dna.dnaS
                def indicator2=f2dna.indicator
                print("dnaT2:" + dnaT2)
                print("dnaS2:" + dnaS2)*/

                def fitnessDna1=Fitness1.findByToken(randnumFitness1)
                dnaT1=fitnessDna1.dnaT
                dnaS1=fitnessDna1.dnaS
                dnaF1=fitnessDna1.dnaF
                def findFaculty=Faculty.findByCode(dnaF1)
                def facultyName1=findFaculty.className
                def timeReference1
                def tStart1
                def tEnd1
                def indicator1=fitnessDna1.indicator

                def fitnessDna2=Fitness2.findByToken(randnumFitness2)
                dnaT2=fitnessDna2.dnaT
                dnaS2=fitnessDna2.dnaS
                dnaF2=fitnessDna2.dnaF
                def findFaculty2=Faculty.findByCode(dnaF2)
                def facultyName2=findFaculty2.className
                def timeReference2
                def tStart2
                def tEnd2
                def indicator2=fitnessDna2.indicator


                if(dnaF1==dnaF2) {

                    if ((indicator1 == 1) || (indicator2 == 1)) {
                        if (randnum1 < 3) {
                            //cross over with probability 3/10 if indicator indicates it is not fit
                            def collegeReference1 = College.findByTeacherCode(dnaT1)
                            if (randnumFitness1 <= 11) {
                                tStart1 = collegeReference1.tueStartTime
                                tEnd1 = collegeReference1.tueEndTime
                            }
                            if (randnumFitness1 >= 12 && randnumFitness1 <= 23) {
                                tStart1 = collegeReference1.wedStartTime
                                tEnd1 = collegeReference1.wedEndTime
                            }
                            if (randnumFitness1 >= 24 && randnumFitness1 <= 35) {
                                tStart1 = collegeReference1.thurStartTime
                                tEnd1 = collegeReference1.thurEndTime
                            }
                            if (randnumFitness1 >= 36 && randnumFitness1 <= 47) {
                                tStart1 = collegeReference1.friStartTime
                                tEnd1 = collegeReference1.friEndTime
                            }
                            if (randnumFitness1 >= 48 && randnumFitness1 <= 59) {
                                tStart1 = collegeReference1.satStartTime
                                tEnd1 = collegeReference1.satEndTime
                            }
                            if (randnumFitness1 >= 60 && randnumFitness1 <= 71) {
                                tStart1 = collegeReference1.sunStartTime
                                tEnd1 = collegeReference1.sunEndTime
                            }
                            def collegeReference2 = College.findByTeacherCode(dnaT2)
                            if (randnumFitness2 <= 11) {
                                tStart2 = collegeReference2.tueStartTime
                                tEnd2 = collegeReference2.tueEndTime
                            }
                            if (randnumFitness2 >= 12 && randnumFitness2 <= 23) {
                                tStart2 = collegeReference2.wedStartTime
                                tEnd2 = collegeReference2.wedEndTime
                            }
                            if (randnumFitness2 >= 24 && randnumFitness2 <= 35) {
                                tStart2 = collegeReference2.thurStartTime
                                tEnd2 = collegeReference2.thurEndTime
                            }
                            if (randnumFitness2 >= 36 && randnumFitness2 <= 47) {
                                tStart2 = collegeReference2.friStartTime
                                tEnd2 = collegeReference2.friEndTime
                            }
                            if (randnumFitness2 >= 48 && randnumFitness2 <= 59) {
                                tStart2 = collegeReference2.satStartTime
                                tEnd2 = collegeReference2.satEndTime
                            }
                            if (randnumFitness2 >= 60 && randnumFitness2 <= 71) {
                                tStart2 = collegeReference2.sunStartTime
                                tEnd2 = collegeReference2.sunEndTime
                            }
                            def slot1 = randnumFitness1 % 4
                            def slot2 = randnumFitness2 % 4
                            def slotReference1 = Slot.findById(slot1)
                            def slotReference2 = Slot.findById(slot2)
                            def slotStartTime1 = slotReference1.startTime
                            def slotEndTime1 = slotReference1.endTime
                            def slotStartTime2 = slotReference2.startTime
                            def slotEndTime2 = slotReference2.endTime
                            if ((tStart1.before(slotStartTime2) || (tStart1.equals(slotStartTime2))) && ((tEnd1.after(slotEndTime2)) || (tEnd1.equals(slotStartTime2)))) {
                                if ((tStart2.before(slotStartTime1) || (tStart2.equals(slotStartTime1))) && ((tEnd2.after(slotEndTime1)) || (tEnd2.equals(slotStartTime1))))

                                {
                                    temp1 = dnaT1
                                    temp2 = dnaS1
                                    dnaT1 = dnaT2
                                    dnaS1 = dnaS2
                                    dnaT2 = temp1
                                    dnaS2 = temp2
                                }
                            }

                        }
                    } else {
                        if (randnum1 < 2) {
                            //cross over with probability 2/10 if indicator indicates it is fit
                            def collegeReference1 = College.findByTeacherCode(dnaT1)
                            if (randnumFitness1 <= 11) {
                                tStart1 = collegeReference1.tueStartTime
                                tEnd1 = collegeReference1.tueEndTime
                            }
                            if (randnumFitness1 >= 12 && randnumFitness1 <= 23) {
                                tStart1 = collegeReference1.wedStartTime
                                tEnd1 = collegeReference1.wedEndTime
                            }
                            if (randnumFitness1 >= 24 && randnumFitness1 <= 35) {
                                tStart1 = collegeReference1.thurStartTime
                                tEnd1 = collegeReference1.thurEndTime
                            }
                            if (randnumFitness1 >= 36 && randnumFitness1 <= 47) {
                                tStart1 = collegeReference1.friStartTime
                                tEnd1 = collegeReference1.friEndTime
                            }
                            if (randnumFitness1 >= 48 && randnumFitness1 <= 59) {
                                tStart1 = collegeReference1.satStartTime
                                tEnd1 = collegeReference1.satEndTime
                            }
                            if (randnumFitness1 >= 60 && randnumFitness1 <= 71) {
                                tStart1 = collegeReference1.sunStartTime
                                tEnd1 = collegeReference1.sunEndTime
                            }
                            def collegeReference2 = College.findByTeacherCode(dnaT2)
                            if (randnumFitness2 <= 11) {
                                tStart2 = collegeReference2.tueStartTime
                                tEnd2 = collegeReference2.tueEndTime
                            }
                            if (randnumFitness2 >= 12 && randnumFitness2 <= 23) {
                                tStart2 = collegeReference2.wedStartTime
                                tEnd2 = collegeReference2.wedEndTime
                            }
                            if (randnumFitness2 >= 24 && randnumFitness2 <= 35) {
                                tStart2 = collegeReference2.thurStartTime
                                tEnd2 = collegeReference2.thurEndTime
                            }
                            if (randnumFitness2 >= 36 && randnumFitness2 <= 47) {
                                tStart2 = collegeReference2.friStartTime
                                tEnd2 = collegeReference2.friEndTime
                            }
                            if (randnumFitness2 >= 48 && randnumFitness2 <= 59) {
                                tStart2 = collegeReference2.satStartTime
                                tEnd2 = collegeReference2.satEndTime
                            }
                            if (randnumFitness2 >= 60 && randnumFitness2 <= 71) {
                                tStart2 = collegeReference2.sunStartTime
                                tEnd2 = collegeReference2.sunEndTime
                            }
                            def slot1 = randnumFitness1 % 4
                            def slot2 = randnumFitness2 % 4
                            def slotReference1 = Slot.findById(slot1)
                            def slotReference2 = Slot.findById(slot2)
                            def slotStartTime1 = slotReference1.startTime
                            def slotEndTime1 = slotReference1.endTime
                            def slotStartTime2 = slotReference2.startTime
                            def slotEndTime2 = slotReference2.endTime
                            if ((tStart1.before(slotStartTime2) || (tStart1.equals(slotStartTime2))) && ((tEnd1.after(slotEndTime2)) || (tEnd1.equals(slotStartTime2)))) {
                                if ((tStart2.before(slotStartTime1) || (tStart2.equals(slotStartTime1))) && ((tEnd2.after(slotEndTime1)) || (tEnd2.equals(slotStartTime1))))
                                {
                            temp1 = dnaT1
                            temp2 = dnaS1
                            dnaT1 = dnaT2
                            dnaS1 = dnaS2
                            dnaT2 = temp1
                            dnaS2 = temp2
                        }
                            }
                        }
                    }
                }

            fitnessDna1.dnaT=dnaT1
            fitnessDna1.dnaS=dnaS1
            fitnessDna2.dnaT=dnaT2
            fitnessDna2.dnaS=dnaS2


            fitnessDna1.save()
            fitnessDna2.save()

        }
             /*for(def rep=0;rep<2;rep++){
           if(rep==0){
               hardConstraintF1()
           }
           else{
               hardConstraintF2()
           }
       }*/
        mutation()

        def hfitnessValue1 = hardConstraintF1()
        def sfitnessValue1 = softConstraintFS1()
        print("hfitnessValue1"+hfitnessValue1)
        print("sfitnessValue1"+sfitnessValue1)
        def hfitnessValue2 = hardConstraintF2()
        def sfitnessValue2 = softConstraintFS2()
            print("hfitnessValue2"+hfitnessValue2)
            print("sfitnessValue2"+sfitnessValue2)
        def totalFitness1 = hfitnessValue1+sfitnessValue1
        def totalFitness2 = hfitnessValue2+sfitnessValue2
print("total fitness1:"+totalFitness1)
print("total fitness2:"+totalFitness2)


        createChildChromosome(totalFitness1, totalFitness2, hfitnessValue1, sfitnessValue1, hfitnessValue2, sfitnessValue2)
            //if(totalFitness1>totalFitness2){
                if(hfitnessValue1>hfitnessValue2) {
                optimumFitness=hfitnessValue1
            }
            else
            {
                optimumFitness=hfitnessValue2
            }

          print("This inside where loop")
            print("optimum:"+optimumFitness)

    }
        print("optimum:"+optimumFitness)
       print("outside where loop")
        if(optimumFitness>=threshold) {
            redirect(action: "storeCode", params: [optimumFitness: optimumFitness])

        }

    }
    def mutation(){
        print("inside mutation")
        def fitness11
        def fitness22

        def startTime
        def endTime
        def facultyNameSlot
        def facultyName
        def facultyName1
        def token = 1
        for(def mutatechromosome=0;mutatechromosome<2;mutatechromosome++) {     //for mutation of 2 chromosome from fitness1 and fitness2 table
            print("inside mutateChromosome")

            for (def i = 0; i < 72; i++) {
                def faculty11
                def tCode
                def sCode

                Random random = new Random()
                def randnum2 = random.nextInt(10)   //mutation probability
                //operation for chromosome in fitness1 table
                fitness11 = Fitness1.findByToken(i)
                faculty11 = fitness11.dnaF  //faculty code of gene
                facultyNameSlot = Faculty.findByCode(faculty11)       //retrieve faculty of gene
                facultyName = facultyNameSlot.className
                /*  if (facultyName == "BEX") {
                    facultyName1 = "BEX"          //assigning faculty name of the gene
                } else {
                    facultyName1 = "BCT"
                }*/
                tCode = fitness11.dnaT          //teacher code of gene
                sCode = fitness11.dnaS          //subject code of gene

                fitness22 = Fitness2.findByToken(i)     //if condition vitra read nagareko le bahira define gareko
                if (mutatechromosome == 1) {     //mutation operation for chromosome in fitness2
                    print("inside count 1")
                    def faculty111 = fitness22.dnaF
                    def facultyNameSlot1 = Faculty.findByCode(faculty111)
                    facultyName = facultyNameSlot1.className
                    /*if (facultyName11 == "BEX") {
                        facultyName1 = "BEX"
                    } else {
                        facultyName1 = "BCT"
                    }*/
                    tCode = fitness22.dnaT
                    sCode = fitness22.dnaS

                }
                if (randnum2 < 1) {        //mutation operation if it is within the mutation probability i.e. 20%
                    print("inside randnum2")
                    def college = College.findAll()
                    def size = college.size()
                    def randnum3 = random.nextInt(size)

                    def college1 = College.findById(randnum3 + 1)

                    def facultyCollege = college1.faculty   //mutated data ko faculty

                    def teacherCode = college1.teacherCode  //mutated data ko teacher code
                    def subjectCode = college1.subjectCode  //mutated data ko subject code

                    while (token == 1) {
                        print("inside token")

                        if (facultyCollege != facultyName) { //apply mutation only if faculty matches
                            print("inside if")
                            print("faculty in db=" + facultyCollege)
                            print("faculty=" + facultyName)
                            randnum3 = random.nextInt(size)
                            college1 = College.findById(randnum3 + 1)

                            facultyCollege = college1.faculty
                            teacherCode = college1.teacherCode
                            subjectCode = college1.subjectCode
                        } else {
                            print("before comparison")
                            if (i >= 0 && i <= 11) {
                                //0-11 denotes 12 components of chromosome which represents routine of tuesday for 3 sections
                                startTime = college1.tueStartTime
                                endTime = college1.tueEndTime
                            } else if (i >= 12 && i <= 23) {
                                startTime = college1.wedStartTime
                                endTime = college1.wedEndTime
                            } else if (i >= 24 && i <= 35) {
                                startTime = college1.thurStartTime
                                endTime = college1.thurEndTime
                            } else if (i >= 36 && i <= 47) {
                                startTime = college1.friStartTime
                                endTime = college1.friEndTime
                            } else if (i >= 48 && i <= 59) {
                                startTime = college1.satStartTime
                                endTime = college1.satEndTime
                            } else {
                                startTime = college1.sunStartTime
                                endTime = college1.sunEndTime
                            }
                            def slot = i % 4
                            def slotReference = Slot.findById(slot)
                            def slotStartTime = slotReference.startTime
                            def slotEndTime = slotReference.endTime
                            if ((slotStartTime.after(startTime) || slotStartTime.equals(startTime)) && (slotEndTime.before(endTime) || (slotEndTime.equals(endTime)))) {
                                print("time match")
                                print("faculty in db=" + facultyCollege)
                                print("faculty=" + facultyName)
                                tCode = teacherCode
                                sCode = subjectCode
                                break
                                //}
                            }
                            else{
                                randnum3 = random.nextInt(size)
                                college1 = College.findById(randnum3 + 1)
                                facultyCollege = college1.faculty
                                teacherCode = college1.teacherCode
                                subjectCode = college1.subjectCode
                            }
                        }

                    }


                    if (mutatechromosome == 0) {
                        fitness11.dnaT = tCode
                        fitness11.dnaS = sCode
                        fitness11.save()

                    } else {
                        fitness22.dnaT = tCode
                        fitness22.dnaS = sCode
                        fitness22.save()
                    }
                }
            }

        }
    }
    def hardConstraintF1(){
        def fitnessValue=0
        def fitnessValueF11=0
        def dna=[]

        for(def i=0;i<72;i++) {
            def fitness = Fitness1.findByToken(i)

            dna[i]=fitness.dnaT
            //print("dna"+[i]+":"+dna[i])

        }
        def i,j,k,l;
        def ul;
        def ll=0;
        def initial=0;
        for(l=0;l<6;l++){       //routine of 6 days a week
            for(i=initial;i<initial+4;i++){
                ul=ll+8;
                for(j=ll;j<=ul;j=j+4){
                    for(k=j+4;k<=ul;k=k+4){
                        def fitness1=Fitness1.findByToken(j)
                        def fitness2=Fitness1.findByToken(k)//+4 because same slot repeats after duration of 4

                        if(dna[j]==dna[k]&&dna[j]!="11111"){
                            //slot comparison for same day same period (teacher code)
                            fitnessValue=fitnessValue //-5
                            fitness1.indicator=1
                            fitness2.indicator=1

                        }
                        else{
                            fitnessValue=fitnessValue+10
                            fitness2.indicator=0
                            fitness1.indicator=0

                        }
                        fitness1.save(flush: true)
                        fitness2.save(flush: true)
                    }
                }
                ll++;
            }
            ll=ll+8;                //+8 because difference in routine between one section and last section is 8 periods here (will increase with increase in section)
            initial=initial+12;     //+12 because routine of a day(eg sunday) consists of 12 dna (will increase will increase in section)
        }
       // print("Fitness H1:"+fitnessValue)
        fitnessValueF11=hardConstraintF11(fitnessValue)
        return fitnessValueF11
    }

    def hardConstraintF11(def fitnessValue){
        def fitnessValueF111=0
        for(def i=0;i<72;i++) {
            def code = Fitness1.findByToken(i)
            def tcode = code.dnaT
            print("tcode:"+tcode)
            def presentindicator = code.indicator
            if (presentindicator == 0){
                def fitness1 = Fitness1.findByToken(i)
            if (tcode != "11111") {
                def college = College.findByTeacherCode(tcode)
                print("College:"+college)
                def startTime
                def endTime
                if (i >= 0 && i <= 11) {
                    //0-11 denotes 12 components of chromosome which represents routine of tuesday for 3 sections
                    startTime = college.tueStartTime
                    endTime = college.tueEndTime
                } else if (i >= 12 && i <= 23) {
                    startTime = college.wedStartTime
                    endTime = college.wedEndTime
                } else if (i >= 24 && i <= 35) {
                    startTime = college.thurStartTime
                    endTime = college.thurEndTime
                } else if (i >= 36 && i <= 47) {
                    startTime = college.friStartTime
                    endTime = college.friEndTime
                } else if (i >= 48 && i <= 59) {
                    startTime = college.satStartTime
                    endTime = college.satEndTime
                } else {
                    startTime = college.sunStartTime
                    endTime = college.sunEndTime
                }

                def value = i % 4

                def slot = Slot.findById(value)

                def startTime1 = slot.startTime
                def endTime1 = slot.endTime

                if (startTime1 >= startTime && endTime1 <= endTime) {
                    /*print("time available")
                    print("StartTime1:" + startTime1)
                    print("StartTime:" + startTime)
                    print("EndTime:" + endTime)
                    print("EndTime1:" + endTime1)*/
                    fitnessValue = fitnessValue + 10
                    fitness1.indicator = 0
                    //print("Fitness inside if:" + fitnessValue)

                } else {
                   /* print("time not available")
                    print("StartTime1:" + startTime1)
                    print("StartTime:" + startTime)
                    print("EndTime:" + endTime)
                    print("EndTime:" + endTime1)*/
                    fitnessValue = fitnessValue //- 5
                    fitness1.indicator = 1
                   // print("Fitness inside else:" + fitnessValue)
                }

            } else {
                fitnessValue = fitnessValue + 10
                fitness1.indicator = 0

            }
            fitness1.save(flush: true)
        }
        }
        //print("fitnessF111:"+fitnessValue)
        fitnessValueF111=hardConstraintF111(fitnessValue)
        return fitnessValueF111
    }
    def hardConstraintF111(def fitnessValue){

        def collegeWorkLoad=College.findAll()
        def size=collegeWorkLoad.size()
        for(def count=0;count<size;count++){
            def comparisonCount=0;      //This stores the number of occurence of the subject
            def collegeWorkLoad1=College.findById(count+1)
            def teacherCode=collegeWorkLoad1.teacherCode
            def subjectCode=collegeWorkLoad1.subjectCode
            def facultyCode1=collegeWorkLoad1.faculty
            def facultyCodeClass=Faculty.findByClassName(facultyCode1)
            def facultyCode=facultyCodeClass.code
            def workAllocated=collegeWorkLoad1.classesPerWeek
            for(def loop=0;loop<72;loop++){
            def comparisonCollege=Fitness1.findByToken(loop)       //retrieve gene from fitness1 table
                def presentIndicator=comparisonCollege.indicator
                def rteacherCode=comparisonCollege.dnaT
                def rsubjectCode=comparisonCollege.dnaS
                def rfacultyCode=comparisonCollege.dnaF

                if((rteacherCode!=teacherCode)||(rsubjectCode!=subjectCode)||(rfacultyCode!=facultyCode)){

                }
                else{
                    comparisonCount++
                }
                def indicatorWorkLoad=presentIndicator
                if((comparisonCount>workAllocated+1)||comparisonCount==0)
                {
                    indicatorWorkLoad=1
                }
                else{
                    if(presentIndicator==0) {
                        indicatorWorkLoad = 0
                    }
                }
                comparisonCollege.indicator=indicatorWorkLoad
                comparisonCollege.save(flush: true)
            }
            if((workAllocated==comparisonCount-1)||(workAllocated==comparisonCount)||(workAllocated==comparisonCount+1)){
                fitnessValue=fitnessValue+10
            }
            else{
                def multiplier
                if(comparisonCount<workAllocated){
                    multiplier=workAllocated-comparisonCount
                }
                else{
                    multiplier=comparisonCount-workAllocated
                }
                fitnessValue=fitnessValue-5*multiplier
            }
        }
        return fitnessValue
    }
    def softConstraintFS1(){
        def fitnessValue1=0
        def dna=[]
        for(def i=0;i<72;i++) {
            def fitness = Fitness1.findByToken(i)

            dna[i]=fitness.dnaS
            print("dna"+[i]+":"+dna[i])
        }
        def i=0
        def ll=i
        def ul=ll+3
        for(i=0;i<18;i++){
            for(def j=ll;j<ul;j++){
                for(def k=j+1;k<=ul;k++){
                    //print("j:"+j+" "+"k:"+k)
                    if(dna[j]==dna[k]){
                        if(dna[j]=="1111")
                        {
                            fitnessValue1=fitnessValue1+10
                        }
                        else {
                            fitnessValue1 = fitnessValue1 //- 5
                        }
                    }
                    else{
                        fitnessValue1=fitnessValue1+10
                    }
                }
            }
            ll=ul+1
            ul=ll+3
        }
      //  print("fitness1:"+fitnessValue1)
        def fitnessvalueFS1=softConstraintFS11(fitnessValue1)
        return fitnessvalueFS1

    }
    def softConstraintFS11(def fitnessValue){
        def insignificantLocation1=0    //leisure period on 1st and last (4th) is not significant
        def insignificantLocation2=3
        //def leisure1="10000"
        def leisure1="1111"
        def i,j
        def k=0
        for(i=0;i<18;i++){
            for(j=k;j<k+4;j++){
                def fitness = Fitness1.findByToken(j)
                def subjectCode = fitness.dnaS
               // print("subjectCode:"+subjectCode)
                if (subjectCode.equals(leisure1)){
                    if(j!=insignificantLocation1&&j!=insignificantLocation2) {
                        def fitness1=Fitness1.findByToken(j-1)
                        def subjectCode1=fitness1.dnaS//Subject code one step behind location j
                       // print("subjectCode1:"+subjectCode1)
                        def fitness2=Fitness1.findByToken(j+1)
                        def subjectCode2=fitness2.dnaS//Subject code one step ahead location j
                      //  print("subjectCode2:"+subjectCode2)
                        if((subjectCode1!=leisure1) && (subjectCode2!=leisure1)){
                            fitnessValue=fitnessValue//-5
                           // print("fitness negative:"+fitnessValue)
                        }
                        else{
                            fitnessValue=fitnessValue+10
                          //  print("fitness Positive:"+fitnessValue)
                        }
                    }
                }
            }
            k=k+4
            insignificantLocation1=insignificantLocation1+4
            insignificantLocation2=insignificantLocation2+4
        }

        return fitnessValue as Integer
    }
    def hardConstraintF2(){
        def fitnessValue=0
        def fitnessValueF22=0
        def dna=[]
        for(def i=0;i<72;i++) {
            def fitness = Fitness2.findByToken(i)

            dna[i]=fitness.dnaT
            //print("dna"+[i]+":"+dna[i])

        }
        def i,j,k,l;
        def ul;
        def ll=0;
        def initial=0;
        for(l=0;l<6;l++){       //routine of 6 days a week
            for(i=initial;i<initial+4;i++){
                ul=ll+8;
                for(j=ll;j<=ul;j=j+4){
                    for(k=j+4;k<=ul;k=k+4){
                        def fitness1=Fitness2.findByToken(j)
                        def fitness2=Fitness2.findByToken(k)//+4 because same slot repeats after duration of 4
                        if(dna[j]==dna[k]&&dna[j]!="11111"){        //slot comparison for same day same period (teacher code)
                            fitnessValue=fitnessValue//-5
                            fitness1.indicator=1
                            fitness2.indicator=1
                        }
                        else{
                            fitnessValue=fitnessValue+10
                            fitness1.indicator=0
                            fitness2.indicator=0
                        }
                        fitness1.save()
                        fitness2.save()
                    }
                }
                ll++;
            }
            ll=ll+8;                //+8 because difference in routine between one section and last section is 8 periods here (will increase with increase in section)
            initial=initial+12;     //+12 because routine of a day(eg sunday) consists of 12 dna (will increase will increase in section)
        }
       // print("Fitness h2:"+fitnessValue)
        fitnessValueF22=hardConstraintF22(fitnessValue)
        return fitnessValueF22
    }

    def hardConstraintF22(def fitnessValue){
        def fitnessValueF222=0
        for(def i=0;i<72;i++) {
            def code = Fitness2.findByToken(i)
            def tcode = code.dnaT
            def presentIndicator=code.indicator
            def fitness1=Fitness2.findByToken(i)
            fitness1.indicator=presentIndicator
            if (tcode != "11111") {
                def college = College.findByTeacherCode(tcode)
                def startTime
                def endTime
                if (i >= 0 && i <= 11) {
                    //0-11 denotes 12 components of chromosome which represents routine of tuesday for 3 sections
                    startTime = college.tueStartTime
                    endTime = college.tueEndTime
                } else if (i >= 12 && i <= 23) {
                    startTime = college.wedStartTime
                    endTime = college.wedEndTime
                } else if (i >= 24 && i <= 35) {
                    startTime = college.thurStartTime
                    endTime = college.thurEndTime
                } else if (i >= 36 && i <= 47) {
                    startTime = college.friStartTime
                    endTime = college.friEndTime
                } else if (i >= 48 && i <= 59) {
                    startTime = college.satStartTime
                    endTime = college.satEndTime
                } else {
                    startTime = college.sunStartTime
                    endTime = college.sunEndTime
                }

                def value = i % 4

                def slot = Slot.findById(value)

                def startTime1 = slot.startTime
                def endTime1 = slot.endTime

                if (startTime1 >= startTime && endTime1 <= endTime) {
                 /*   print("time available")
                    print("StartTime1:" + startTime1)
                    print("StartTime:" + startTime)
                    print("EndTime:" + endTime)
                    print("EndTime1:" + endTime1)*/
                    fitnessValue = fitnessValue + 10
                    if(presentIndicator==0) {
                        fitness1.indicator = 0
                    }
                    //print("Fitness inside if:" + fitnessValue)

                } else {
                   /* print("time not available")
                    print("StartTime1:" + startTime1)
                    print("StartTime:" + startTime)
                    print("EndTime:" + endTime)
                    print("EndTime:" + endTime1)*/
                    fitnessValue = fitnessValue //- 5
                    fitness1.indicator=1
                  //  print("Fitness inside else:" + fitnessValue)
                }

            }
            else{
                fitnessValue=fitnessValue+10
                if(presentIndicator==0) {
                    fitness1.indicator = 0
                }
            }
            fitness1.save()
        }
       // print("fitnessF222:"+fitnessValue)

       fitnessValueF222=hardConstraintF222(fitnessValue)
        return fitnessValueF222
    }
    def hardConstraintF222(def fitnessValue){
        def collegeWorkLoad=College.findAll()
        def size=collegeWorkLoad.size()
        for(def count=0;count<size;count++){
            def comparisonCount=0;      //This stores the number of occurence of the subject
            def collegeWorkLoad1=College.findById(count+1)
            def teacherCode=collegeWorkLoad1.teacherCode
            def subjectCode=collegeWorkLoad1.subjectCode
            def facultyCode1=collegeWorkLoad1.faculty
            def facultyCodeClass=Faculty.findByClassName(facultyCode1)
            def facultyCode=facultyCodeClass.code
            def workAllocated=collegeWorkLoad1.classesPerWeek
            for(def loop=0;loop<72;loop++){
                def comparisonCollege=Fitness2.findByToken(loop)       //retrieve gene from fitness1 table
                def rteacherCode=comparisonCollege.dnaT
                def rsubjectCode=comparisonCollege.dnaS
                def rfacultyCode=comparisonCollege.dnaF
                def presentIndicator=comparisonCollege.indicator
                if((rteacherCode!=teacherCode)||(rsubjectCode!=subjectCode)||(rfacultyCode!=facultyCode)){

                }
                else{
                    comparisonCount++
                }
                def indicatorWorkLoad=presentIndicator
                if(comparisonCount>workAllocated+1)
                {
                    indicatorWorkLoad=1
                }
                else{
                    if(presentIndicator==0) {
                        indicatorWorkLoad = 0
                    }
                }
                comparisonCollege.indicator=indicatorWorkLoad
                comparisonCollege.save()
            }
            if((workAllocated==comparisonCount-1)||(workAllocated==comparisonCount)||(workAllocated==comparisonCount+1)){
                fitnessValue=fitnessValue+10
            }
            else{
                def multiplier
                if(comparisonCount<workAllocated){
                    multiplier=workAllocated-comparisonCount
                }
                else{
                    multiplier=comparisonCount-workAllocated
                }
                fitnessValue=fitnessValue-5*multiplier
            }
        }
        return fitnessValue
    }
    def softConstraintFS2(){
        def fitnessValue1=0
        def dna=[]
        for(def i=0;i<72;i++) {
            def fitness = Fitness2.findByToken(i)

            dna[i]=fitness.dnaS
            //print("dna"+[i]+":"+dna[i])
        }
        def i=0
        def ll=i
        def ul=ll+3
        for(i=0;i<18;i++){
            for(def j=ll;j<ul;j++){
                for(def k=j+1;k<=ul;k++){
                    //print("j:"+j+" "+"k:"+k)
                    if(dna[j]==dna[k]){
                        if(dna[j]=="1111")
                        {
                            fitnessValue1=fitnessValue1+10
                        }
                        else {
                            fitnessValue1 = fitnessValue1 //- 5
                        }
                    }
                    else{
                        fitnessValue1=fitnessValue1+10
                    }
                }
            }
            ll=ul+1
            ul=ll+3
        }
      //  print("fitness1:"+fitnessValue1)
        def fitnessvalueFS2=softConstraintFS22(fitnessValue1)
        return fitnessvalueFS2

    }
    def softConstraintFS22(def fitnessValue){
        def insignificantLocation1=0    //leisure period on 1st and last (4th) is not significant
        def insignificantLocation2=3
        //def leisure1="10000"
        def leisure1="1111"
        def i,j
        def k=0
        for(i=0;i<18;i++){
            for(j=k;j<k+4;j++){
                def fitness = Fitness2.findByToken(j)
                def subjectCode = fitness.dnaS
               // print("subjectCode:"+subjectCode)
                if (subjectCode.equals(leisure1)){
                    if(j!=insignificantLocation1&&j!=insignificantLocation2) {
                        def fitness1=Fitness2.findByToken(j-1)
                        def subjectCode1=fitness1.dnaS//Subject code one step behind location j
                       // print("subjectCode1:"+subjectCode1)
                        def fitness2=Fitness2.findByToken(j+1)
                        def subjectCode2=fitness2.dnaS//Subject code one step ahead location j
                      //  print("subjectCode2:"+subjectCode2)
                        if((subjectCode1!=leisure1) && (subjectCode2!=leisure1)){
                            fitnessValue=fitnessValue //-5
                           // print("fitness negative:"+fitnessValue)
                        }
                        else{
                            fitnessValue=fitnessValue+10
                           // print("fitness Positive:"+fitnessValue)
                        }
                    }
                }
            }
            k=k+4
            insignificantLocation1=insignificantLocation1+4
            insignificantLocation2=insignificantLocation2+4
        }

        return fitnessValue as Integer
    }
    def createChildChromosome(def totalFitness1, def totalFitness2, def hfitnessValue1, def sfitnessValue1, def hfitnessValue2, def sfitnessValue2){
        def wholeCode
        def myList=[]
        def wholeCode1
        def myList1=[]
            for(def i=0;i<72;i++) {
                def fitness1 = Fitness1.findByToken(i)
                def dnaF = fitness1.dnaF
                def dnaT = fitness1.dnaT
                def dnaS = fitness1.dnaS
                def dnaSl = fitness1.dnaSl

                wholeCode=dnaF+"."+dnaT+"."+dnaS+"."+dnaSl

                myList=myList+[wholeCode]
            }

            Chromosome chromosome=new Chromosome()
            chromosome.chromo=myList
            chromosome.fitness=totalFitness1
            chromosome.fitnessHard=hfitnessValue1
            chromosome.fitnessSoft=sfitnessValue1
            print("mylist:"+myList)
            chromosome.save()
            Fitness1.findAll().each {it.delete(flush:true, failOnError:true)}

        for(def i=0;i<72;i++) {
            def fitness2 = Fitness2.findByToken(i)
            def dnaF = fitness2.dnaF
            def dnaT = fitness2.dnaT
            def dnaS = fitness2.dnaS
            def dnaSl = fitness2.dnaSl

            wholeCode1=dnaF+"."+dnaT+"."+dnaS+"."+dnaSl
            myList1=myList1+[wholeCode1]
        }

        Chromosome chromosome1=new Chromosome()
        chromosome1.chromo=myList1
        chromosome1.fitness=totalFitness2
        chromosome1.fitnessHard=hfitnessValue2
        chromosome1.fitnessSoft=sfitnessValue2
        print("mylist1:"+myList1)

        chromosome1.save()
        Fitness2.findAll().each {it.delete(flush:true, failOnError:true)}


    }
    def storeCode(){
        def optimumFitness=params.optimumFitness
        print("optimumFitmess:"+optimumFitness)
        //def chromosome=Chromosome.findByFitness(optimumFitness)
            def chromosome=Chromosome.findByFitnessHard(optimumFitness)
        def chromosome1=chromosome.chromo
        def size=chromosome1.size()
        def chromos = chromosome.chromo.substring(1, size-1)
        def gene
        def dna
        gene = chromos.split(",")
        //print("genes;"+gene)
        for(def i=0;i<72;i++) {

            print("gene" + [i] + ":" + gene[i].replaceAll("\\s", ""))
            dna = gene[i].tokenize(".")
            for (def j = 0; j < 4; j++) {
                printf("dna:" + dna[j].replaceAll("\\s", ""))

            }
            FinalDna finalDna=new FinalDna()
            finalDna.faculty=dna[0]
            finalDna.teacher=dna[1]
            finalDna.subject=dna[2]
            finalDna.slot=dna[3]
            finalDna.token=i


            finalDna.save()

        }
        redirect(action: 'storeActualData')

//
    }
    def storeActualData(){
        for(def i=0;i<72;i++){
            def finalDna=FinalDna.findByToken(i)
            def time=finalDna.slot.replaceAll("\\s","")
           // print("time:"+time)
            def teacher=finalDna.teacher.replaceAll("\\s","")
          //  print("Teacher:"+teacher)
            def subject=finalDna.subject.replaceAll("\\s","")
           // print("Subject:"+subject)
            def faculty=finalDna.faculty.replaceAll("\\s","")
           // print("faculty:"+faculty)

            def slot=Slot.findByCode(time)
            def startTime=slot.startTime
            def endTime=slot.endTime


            def subject1=Subject.findByCode(subject)
            def sname=subject1.name

            def faculty1=Faculty.findByCode(faculty)
            def faculty11=faculty1.className


            def teacher1=College.findByTeacherCodeAndSubjectCodeAndFaculty(teacher,subject,faculty11)
            def tname=teacher1.teacher

            ActualData actualData=new ActualData()

            actualData.startTime=startTime
            actualData.endTime=endTime
            actualData.teacherName=tname
            actualData.subjectName=sname
            actualData.faculty=faculty11
            actualData.token=i
            if (i >= 0 && i <= 11) {
               actualData.day="Tuesday"
            } else if (i >= 12 && i <= 23) {
                actualData.day="Wednesday"
            } else if (i >= 24 && i <= 35) {
                actualData.day="Thursday"
            } else if (i >= 36 && i <= 47) {
                actualData.day="Friday"
            } else if (i >= 48 && i <= 59) {
                actualData.day="Saturday"
            } else {
                actualData.day="Sunday"
            }

            actualData.save()
        }
        redirect(action: "viewRoutine")
    }
    def viewRoutine(){
        render(view:"routineGenerated")
    }
    def selectTeacher(){
        def teacher=Teacher.findAll()

        render(view:"enterTeacher", model:[t:teacher])
    }
    def selectClass(){
        render(view:"enterClass")
    }

    def viewClass(){
        def faculty=params.faculty
        def actualData=ActualData.findAllByFaculty(faculty)
        def slot=Slot.findAll()
        def day=Day.findAll()


        render(view: "result", model:[a:actualData, s:slot, d:day, f:faculty])
    }
    def viewTeacher(){
        def teacher=params.teacher
        def actualData=ActualData.findAllByTeacherName(teacher)
        def slot=Slot.findAll()
        def time1=Slot.findByCode("0")
        def time2=Slot.findByCode("1")
        def time3=Slot.findByCode("10")
        def time4=Slot.findByCode("11")


        render (view:"routineTeacher", model:[a:actualData, s:slot, t:teacher, t1:time1, t2:time2, t3:time3,t4:time4])
    }
    def downloadExcel(){
        def id=params.id
        def section
        if(id=="BCT A"){
            section=0
        }
        else if(id=="BCT B"){
            section=4
        }
        else {
            section = 8
        }
        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="Routine.xls"')
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Routine", 0)
        def actualData
        def teacherName
        def subjectName

        //def size=actualData.size()
        //for(def i=0;i<size;i++){
        sheet1.addCell(new Label(0,0, id))
        sheet1.addCell(new Label(1,0, "07:00:00-8:45:00"))
        sheet1.addCell(new Label(2,0, "8:45:00-10:15:00"))
        sheet1.addCell(new Label(3,0, "11:00:00-12:30:00"))
        sheet1.addCell(new Label(4,0, "12:30:00-2:00:00"))
        sheet1.addCell(new Label(0,1, "Tuesday"))
        sheet1.addCell(new Label(0,2, "Wednesday"))
        sheet1.addCell(new Label(0,3, "Thursday"))
        sheet1.addCell(new Label(0,4, "Friday"))
        sheet1.addCell(new Label(0,5, "Saturday"))
        sheet1.addCell(new Label(0,6, "Sunday"))
            for(def row=1;row<=6;row++){
                for(def column=1;column<=4;column++){
                    actualData=ActualData.findByToken(section)
                    section++
                    if(section%4==0)
                    {
                        section=section+8
                    }
                    teacherName=actualData.teacherName
                    subjectName=actualData.subjectName
                    sheet1.addCell(new Label(column,row, subjectName+"("+teacherName+")"))

                }
            }
        sheet1.addCell(new Label(0,10, "BCT B"))
        sheet1.addCell(new Label(1,10, "07:00:00-8:45:00"))
        sheet1.addCell(new Label(2,10, "8:45:00-10:15:00"))
        sheet1.addCell(new Label(3,10, "11:00:00-12:30:00"))
        sheet1.addCell(new Label(4,10, "12:30:00-2:00:00"))
        sheet1.addCell(new Label(0,11, "Tuesday"))
        sheet1.addCell(new Label(0,12, "Wednesday"))
        sheet1.addCell(new Label(0,13, "Thursday"))
        sheet1.addCell(new Label(0,14, "Friday"))
        sheet1.addCell(new Label(0,15, "Saturday"))
        sheet1.addCell(new Label(0,16, "Sunday"))
        section=4
        for(def row=11;row<=16;row++){
            for(def column=1;column<=4;column++){
                actualData=ActualData.findByToken(section)
                section++
                if(section%4==0)
                {
                    section=section+8
                }
                teacherName=actualData.teacherName
                subjectName=actualData.subjectName
                sheet1.addCell(new Label(column,row, subjectName+"("+teacherName+")"))

            }
        }
        sheet1.addCell(new Label(0,20,"BEX"))
        sheet1.addCell(new Label(1,20, "07:00:00-8:45:00"))
        sheet1.addCell(new Label(2,20, "8:45:00-10:15:00"))
        sheet1.addCell(new Label(3,20, "11:00:00-12:30:00"))
        sheet1.addCell(new Label(4,20, "12:30:00-2:00:00"))
        sheet1.addCell(new Label(0,21, "Tuesday"))
        sheet1.addCell(new Label(0,22, "Wednesday"))
        sheet1.addCell(new Label(0,23, "Thursday"))
        sheet1.addCell(new Label(0,24, "Friday"))
        sheet1.addCell(new Label(0,25, "Saturday"))
        sheet1.addCell(new Label(0,26, "Sunday"))
        section=8
        for(def row=21;row<=26;row++){
            for(def column=1;column<=4;column++){
                actualData=ActualData.findByToken(section)
                section++
                if(section%4==0)
                {
                    section=section+8
                }
                teacherName=actualData.teacherName
                subjectName=actualData.subjectName
                sheet1.addCell(new Label(column,row, subjectName+"("+teacherName+")"))

            }
        }
        sheet1.addCell(new Label(1,29,"Problems:"))
        def problemRow=30
        def i,j,k,l
        def ul
        def ll=0
        def initial=0
        for(l=0;l<6;l++){       //routine of 6 days a week
            for(i=initial;i<initial+4;i++){
                ul=ll+8
                for(j=ll;j<=ul;j=j+4){
                    for(k=j+4;k<=ul;k=k+4){
                        def finalData1=ActualData.findByToken(j)
                        def finalData2=ActualData.findByToken(k)//+4 because same slot repeats after duration of 4
                        def teacher1=finalData1.teacherName
                        def teacher2=finalData2.teacherName
                        def sTime=finalData1.startTime
                        def eTime=finalData1.endTime
                        def day=finalData1.day
                        if(teacher1==teacher2&&(teacher1!="Project")){        //slot comparison for same day same period (teacher code)
                            sheet1.addCell(new Label(1,problemRow,"Time Conflict for "+teacher1+" from "+sTime+" to "+eTime+" on "+day))
                            problemRow++
                        }
                    }
                }
                ll++
            }
            ll=ll+8                //+8 because difference in routine between one section and last section is 8 periods here (will increase with increase in section)
            initial=initial+12     //+12 because routine of a day(eg sunday) consists of 12 dna (will increase will increase in section)
        }
        problemRow++
        def today
        for(i=0;i<72;i++) {
            def finalRoutine = ActualData.findByToken(i)
            def tName=finalRoutine.teacherName
            print("tName:"+tName)
            def fName=finalRoutine.faculty
            print("fName:"+fName)
                def college = College.findByTeacherAndFaculty(tName,fName)
            print("college:"+college)
                def startTime
                def endTime
                if (i <= 11) {
                    //0-11 denotes 12 components of chromosome which represents routine of tuesday for 3 sections
                    startTime = college.tueStartTime
                    endTime = college.tueEndTime
                    today="Tuesday"
                } else if (i >= 12 && i <= 23) {
                    startTime = college.wedStartTime
                    endTime = college.wedEndTime
                    today="Wednesday"
                } else if (i >= 24 && i <= 35) {
                    startTime = college.thurStartTime
                    endTime = college.thurEndTime
                    today="Thursday"
                } else if (i >= 36 && i <= 47) {
                    startTime = college.friStartTime
                    endTime = college.friEndTime
                    today="Friday"
                } else if (i >= 48 && i <= 59) {
                    startTime = college.satStartTime
                    endTime = college.satEndTime
                    today="Saturday"
                } else {
                    startTime = college.sunStartTime
                    endTime = college.sunEndTime
                    today="Sunday"
                }

                def value = i % 4

                def slot = Slot.findById(value)

                def startTime1 = slot.startTime
                def endTime1 = slot.endTime

                if (startTime1 >= startTime && endTime1 <= endTime) {


                }
            else{
                    sheet1.addCell(new Label(1,problemRow,"Teacher available time:"+startTime+" - "+endTime+" doesn't match for period: "+startTime1+" - "+endTime1+" on "+today+" for "+fName))
                    problemRow++

                }
        }

        problemRow++
        def collegeWorkLoad=College.findAll()
        def size=collegeWorkLoad.size()
        for(def count=0;count<size;count++){
            def comparisonCount=0      //This stores the number of occurence of the subject
            def collegeWorkLoad1=College.findById(count+1)
            def tName=collegeWorkLoad1.teacher
            def sName=collegeWorkLoad1.subject
            def fName=collegeWorkLoad1.faculty
            def workAllocated=collegeWorkLoad1.classesPerWeek
            def ftName
            def fsName
            def ffName
            for(def loop=0;loop<72;loop++){
                def comparisonCollege=ActualData.findByToken(loop)       //retrieve gene from actualdata table
                ftName=comparisonCollege.teacherName
                fsName=comparisonCollege.subjectName
                ffName=comparisonCollege.faculty
                print("College Teacher:"+tName)
                print("actual Data teacher:"+ftName)
                print("college subject:"+sName)
                print("actual subject:"+fsName)
                print("college faculty:"+fName)
                print("actual Data faculty:"+ffName)
                if((tName==ftName)&&(sName==fsName)&&(fName==ffName)){
                    comparisonCount++
                    printf("Match for "+tName+" with "+ftName+" , "+sName+" with "+fsName+" and "+fName+" with "+ffName)

                }
                else{
                    //printf("Not match for "+tName+" with "+ftName+" , "+sName+" with "+fsName+" and "+fName+" with "+ffName)
                }
            }
            if((workAllocated!=comparisonCount)&&(workAllocated!=comparisonCount-1)&&(workAllocated!=comparisonCount+1)){
                sheet1.addCell(new Label(1,problemRow,"Allocated workload for "+tName+" for "+sName+" in "+fName+" was "+workAllocated+" but workload in routine= "+comparisonCount))
                problemRow++
            }
        }



        //}
       /* sheet1.addCell(new Label(0,0, "First Name"))
        sheet1.addCell(new Label(1,0, "Last Name"))
        sheet1.addCell(new Label(2,0, "Age"))
        sheet1.addCell(new Label(0,1, "John"))
        sheet1.addCell(new Label(1,1, "Doe"))
        sheet1.addCell(new Label(2,1, "20"))
        sheet1.addCell(new Label(0,2, "Jane"))
        sheet1.addCell(new Label(1,2, "Smith"))
        sheet1.addCell(new Label(2,2, "18"))*/

        workbook.write()
        workbook.close()
    }




}
