package arg

class RoutineController {

    def generateRoutine(){
        def count=0
        while(count<4) {
            Teacher teacher = new Teacher()
            Subject subject = new Subject()
            Faculty faculty = new Faculty()
            Slot slot = new Slot()
            def myList = []
            def chromosomeList
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

                    def wholeCode = fcode + tcode + slcode
                    print(wholeCode)

                    myList = [wholeCode] + myList





                    print(myList)

                }


            }
            Chromosome chromosome = new Chromosome()
            chromosome.chromo = myList

            chromosome.save()
            count++
        }





    }
}
