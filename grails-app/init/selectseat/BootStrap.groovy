package selectseat

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        initData()
    }


    def destroy = {
    }
    @Transactional
    private initData(){
        def today = new Date()
        def endDay = new Date(today.getTime() + 2*60*60*1000)



        def l1 =new Location(name: "文化中心")
        def l2 = new Location(name: "中正紀念堂")
        [l1, l2].each {
            if(Location.findByName(it.name) == null) it.save()
        }

        def e1 = new Event(name: "測試活動一號",beginDate: today, endDate: endDay, location: l1)
        def e2 = new Event(name: "測試活動二號",beginDate: today, endDate: endDay, location: l1)
        def e3 = new Event(name: "測試活動三號",beginDate: today, endDate: endDay, location: l2)
        [e1, e2, e3].each {
            if(Event.findByName(it.name) == null) it.save()
        }

        def e1AdultTicket = new Ticket(type: Ticket.TYPE_ADULT_TICKET, price: 1000, event: e1)
        def e1HalfTicket = new Ticket(type: Ticket.TYPE_CONCESSION_TICKET, price: 500, event: e1)
        def e2AdultTicket = new Ticket(type: Ticket.TYPE_ADULT_TICKET, price: 1600, event: e2)
        def e2HalfTicket = new Ticket(type: Ticket.TYPE_CONCESSION_TICKET, price: 1000, event: e2)
        def e3AdultTicket = new Ticket(type: Ticket.TYPE_ADULT_TICKET, price: 100, event: e3)
        def e3HalfTicket = new Ticket(type: Ticket.TYPE_CONCESSION_TICKET, price: 50, event: e3)
        [e1AdultTicket, e1HalfTicket, e2AdultTicket, e2HalfTicket, e3AdultTicket, e3HalfTicket].each {
            if(Ticket.find(it) == null) it.save()
        }

        def e1ZoneA = new Zone(name: "活動一的 A 區", columnCount:10, rowCount: 5, totalSeat: 50, event: e1)
        def e1ZoneB = new Zone(name: "活動一的 B 區", columnCount:10, rowCount: 3, totalSeat: 30, event: e1)
        def e2Zone = new Zone(name: "活動二", columnCount:10, rowCount: 4, totalSeat: 40, event: e2)
        [e1ZoneA, e1ZoneB, e2Zone].each {
            if(Zone.findByName(it.name) == null) it.save()
        }


        def admin = new User(email: "admin@user.com")
        def member = new User(email: "member@user.com")
        [admin, member].each {
            if(User.findByEmail(it.email) == null) it.save()
        }

//        def l1 =new Location(name: "文化中心").save()
//        def l2 = new Location(name: "中正紀念堂").save()
//
//        def e1 = new Event(name: "測試活動一號",date: today, location: l1).save()
//        def e2 = new Event(name: "測試活動二號",date: today, location: l1).save()
//        def e3 = new Event(name: "測試活動三號",date: today, location: l2).save()
//
//        def e1AdultTicket = new Ticket(type: Ticket.TYPE_ADULT_TICKET, price: 1000, stock: 50, event: e1).save()
//        def e1HalfTicket = new Ticket(type: Ticket.TYPE_HALF_TICKET, price: 500, stock: 50, event: e1).save()
//        def e2AdultTicket = new Ticket(type: Ticket.TYPE_ADULT_TICKET, price: 1600, stock: 30, event: e2).save()
//        def e2HalfTicket = new Ticket(type: Ticket.TYPE_HALF_TICKET, price: 1000, stock: 30, event: e2).save()
//        def e3AdultTicket = new Ticket(type: Ticket.TYPE_ADULT_TICKET, price: 100, stock: 50, event: e3).save()
//        def e3HalfTicket = new Ticket(type: Ticket.TYPE_HALF_TICKET, price: 50, stock: 50, event: e3).save()
//
//        def e1ZoneA = new Zone(name: "活動一的 A 區",number: 50, event: e1).save()
//        def e1ZoneB = new Zone(name: "活動一的 B 區",number: 30, event: e1).save()
//        def e2Zone = new Zone(name: "活動二",number: 50, event: e2).save()
//        def admin = new User(email: "admin@user.com").save()
//        def member = new User(email: "member@user.com").save()
//        println "before seat"
//        def seatE1ZoneAA = new SeatMap(columnName: "A", rowAmount: 10, zone: e1ZoneA).save()
//        for (i in 0..<seatE1ZoneAA.rowAmount) {
//            new Seat(seatMap: seatE1ZoneAA).save()
//        }

//        def seatE1ZoneAB = new SeatMap(columnName: "B", rowAmount: 10, zone: e1ZoneA)
//        seatE1ZoneAB.save()
//        for (i in 0..<seatE1ZoneAB.rowAmount) {
//            seatE1ZoneAB.addToSeats(new Seat(seatMap: seatE1ZoneAB))
//        }

//        def seatE1ZoneAC = new SeatMap(columnName: "C", rowAmount: 10, zone: e1ZoneA)
//        for (i in 0..<seatE1ZoneAC.rowAmount) {
//            seatE1ZoneAC.addToSeats(new Seat(seatMap: seatE1ZoneAC))
//        }
//        seatE1ZoneAC.save()

//        def seatE1ZoneAD = new SeatMap(columnName: "D", rowAmount: 10, zone: e1ZoneA)
//        for (i in 0..<seatE1ZoneAD.rowAmount) {
//            seatE1ZoneAD.addToSeats(new Seat(seatMap: seatE1ZoneAD))
//        }
//        seatE1ZoneAD.save()

//        def seatE1ZoneAE = new SeatMap(columnName: "E", rowAmount: 10, zone: e1ZoneA)
//        for (i in 0..<seatE1ZoneAE.rowAmount) {
//            seatE1ZoneAE.addToSeats(new Seat(seatMap: seatE1ZoneAE))
//        }
//        seatE1ZoneAE.save()

//        def seatE1ZoneBA = new SeatMap(columnName: "A", rowAmount: 5, zone: e1ZoneB)
//        for (i in 0..<seatE1ZoneBA.rowAmount) {
//            seatE1ZoneBA.addToSeats(new Seat(seatMap: seatE1ZoneBA))
//        }
//        seatE1ZoneBA.save()

//        def seatE1ZoneBB = new SeatMap(columnName: "B", rowAmount: 10, zone: e1ZoneB)
//        for (i in 0..<seatE1ZoneBB.rowAmount) {
//            seatE1ZoneBB.addToSeats(new Seat(seatMap: seatE1ZoneBB))
//        }
//        seatE1ZoneBB.save()

//        def seatE1ZoneBC = new SeatMap(columnName: "C", rowAmount: 5, zone: e1ZoneB)
//        for (i in 0..<seatE1ZoneBC.rowAmount) {
//            seatE1ZoneBC.addToSeats(new Seat(seatMap: seatE1ZoneBC))
//        }
//        seatE1ZoneBC.save()

//        def seatE1ZoneBD = new SeatMap(columnName: "D", rowAmount: 10, zone: e1ZoneB)
//        for (i in 0..<seatE1ZoneBD.rowAmount) {
//            seatE1ZoneBD.addToSeats(new Seat(seatMap: seatE1ZoneBD))
//        }
//        seatE1ZoneBD.save()

//        println "after seat"

    }
}
