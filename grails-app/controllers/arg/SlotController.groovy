package arg

class SlotController {

    def getStartTime(){
    def slot=Slot.findByCode("0")
    def startTime=slot.startTime

    render startTime
    }

    def getEndTime(){
        def slot=Slot.findByCode("11")
        def endTime=slot.endTime

        render endTime
    }
}
