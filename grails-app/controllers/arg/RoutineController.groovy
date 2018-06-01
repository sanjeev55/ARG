package arg

class RoutineController {

    def createChromosome(){
        def count=0
        while(count<4) {

            def myList = []
            int id, j
            for (id = 1; id <= 4; id++) {
                def faculty1 = Faculty.findById(id)
                def fcode = faculty1.code
                for (j = 1; j <= 4; j++) {
                    Random rand = new Random()

                    int randnum = rand.nextInt(5)
                    while (randnum == 0) {
                        randnum = rand.nextInt(5)
                    }
                    print(randnum)


                    def teacher1 = Teacher.findById(randnum)
                    def tcode = teacher1.code


                    def slot1 = Slot.findById(j)
                    def slcode = slot1.code
                    print("j:" + j)

                    def wholeCode = fcode +"."+ tcode +"."+ slcode
                    print(wholeCode)

                    myList = [wholeCode] + myList
                    def size=myList.size()
                    print("size:"+size)

                    print(myList)

                }
            }
            Chromosome chromosome = new Chromosome()
            chromosome.chromo = myList

            chromosome.save()

            def id1=chromosome.id
            splitDna(id1)
            print("id"+id1)
            count++
        }
        redirect(action: "crossOver")



    }
    def splitDna(def id){
        def id1=id
        def chromosome=Chromosome.findById(id)
        def chromos = chromosome.chromo.substring(1, 159)

        def gene
        def dna
        gene = chromos.split(",")
        print("genesss;"+gene)
        for(def i=0;i<16;i++) {

            print("gene" + [i] + ":" + gene[i].replaceAll("\\s",""))
            dna=gene[i].tokenize(".")
            for(def j=0;j<3;j++){
                printf("dna:"+dna[j].replaceAll("\\s",""))

            }
            Fitness fitness=new Fitness()
            fitness.token=i
            fitness.dna=dna[1]

            fitness.save()


        }
        calculateFitness(id1)
    }
    def calculateFitness(def id){
        def fitnessValue,fitnessValue1

        fitnessValue=hardConstraint()

        fitnessValue1=softConstraint()


        def totalFitness=fitnessValue+fitnessValue1


        def chromo=Chromosome.findById(id)
        chromo.fitness=totalFitness

        print("Total Fitness:"+totalFitness)

        Fitness.executeUpdate('delete from Fitness ')




    }
    def hardConstraint(){
        def fitnessValue=0
        def dna=[]
        for(def i=0;i<16;i++) {
            def fitness = Fitness.findByToken(i)

            dna[i]=fitness.dna
            print("dna"+[i]+":"+dna[i])

        }
        def i
        def ul
        def ll=0
        for(i=0;i<4;i++){
            ul=ll+12
            for(def j=ll;j<=ul;j=j+4){
                for(def k=j+4;k<=ul;k=k+4){
                    print("j:"+j+" "+"k:"+k)
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
    def softConstraint(){
        def fitnessValue1=0
        def dna=[]
        for(def i=0;i<16;i++) {
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

        Fitness1.executeUpdate('delete from Fitness1 ')
        Fitness2.executeUpdate('delete from Fitness2 ')

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
        Random rand = new Random()
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
            print("dna2:" + dna2)
            Random r=new Random()
            def r1=r.nextInt(100)
           // def r2=r.nextInt(9)

                if (r1 % 50== 0) {
                    f1dna.dna1 = dna1.replaceAll("0", "1")

                    f2dna.dna1 = dna2.replaceAll("1", "0")
                }


            else{
                f1dna.dna1=dna1
                f2dna.dna1=dna2
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
    }
    }
    def hardConstraint1(){
        def fitnessValue=0
        def dna=[]
        for(def i=0;i<16;i++) {
            def fitness = Fitness1.findByToken(i)

            dna[i]=fitness.dna1


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
            def fitness = Fitness1.findByToken(i)

            dna[i]=fitness.dna1

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
            def fitness = Fitness2.findByToken(i)

            dna[i]=fitness.dna1


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
            def fitness = Fitness2.findByToken(i)

            dna[i]=fitness.dna1

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

            chromosome.save()



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
        if(chromosome1.fitness==240) {

            def finalChromosome = Chromosome.executeQuery("select max(id) from Chromosome ")
            render(view: "result", model: [f: finalChromosome])
        }
    }




}
