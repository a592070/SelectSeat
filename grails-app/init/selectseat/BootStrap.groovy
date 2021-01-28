package selectseat

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->

        if (!Location.all && !User.all)
        initData()
        }


    def destroy = {
    }
    @Transactional
    private initData(){
        def today = new Date()

        def l1 =new Location(name: "文化中心").save()
        def l2 = new Location(name: "中正紀念堂").save()

        def e1 = new Event(name: "測試活動一號",date: today, location: l1).save()
        def e2 = new Event(name: "測試活動二號",date: today, location: l1).save()
        def e3 = new Event(name: "測試活動三號",date: today, location: l2).save()

        def e1AdultTicket = new Ticket(type: Ticket.TYPE_ADULT_TICKET, price: 1000, stock: 50, event: e1).save()
        def e1HalfTicket = new Ticket(type: Ticket.TYPE_HALF_TICKET, price: 500, stock: 50, event: e1).save()
        def e2AdultTicket = new Ticket(type: Ticket.TYPE_ADULT_TICKET, price: 1600, stock: 30, event: e2).save()
        def e2HalfTicket = new Ticket(type: Ticket.TYPE_HALF_TICKET, price: 1000, stock: 30, event: e2).save()
        def e3AdultTicket = new Ticket(type: Ticket.TYPE_ADULT_TICKET, price: 100, stock: 50, event: e3).save()
        def e3HalfTicket = new Ticket(type: Ticket.TYPE_HALF_TICKET, price: 50, stock: 50, event: e3).save()

        def e1ZoneA = new Zone(name: "活動一的 A 區",number: 50, event: e1).save()
        def e1ZoneB = new Zone(name: "活動一的 B 區",number: 30, event: e1).save()
        def e2Zone = new Zone(name: "活動二",number: 50, event: e2).save()
        def admin = new User(email: "admin@user.com").save()
        def member = new User(email: "member@user.com").save()
        println "before seat"
        def seatE1ZoneAA = new Seat(columnName: "A", rowAmount: 10, zone: e1ZoneA).save()
//        println seatE1ZoneAA.seatCode
        def seatE1ZoneAB = new Seat(columnName: "B", rowAmount: 10, zone: e1ZoneA).save()
//        println seatE1ZoneAB.seatBitmap
        def seatE1ZoneAC = new Seat(columnName: "C", rowAmount: 10, zone: e1ZoneA).save()
        def seatE1ZoneAD = new Seat(columnName: "D", rowAmount: 10, zone: e1ZoneA).save()
        def seatE1ZoneAE = new Seat(columnName: "E", rowAmount: 10, zone: e1ZoneA).save()
        def seatE1ZoneBA = new Seat(columnName: "A", rowAmount: 5, zone: e1ZoneB).save()
        def seatE1ZoneBB = new Seat(columnName: "B", rowAmount: 10, zone: e1ZoneB).save()
        def seatE1ZoneBC = new Seat(columnName: "C", rowAmount: 5, zone: e1ZoneB).save()
        def seatE1ZoneBD = new Seat(columnName: "D", rowAmount: 10, zone: e1ZoneB).save()
        println "after seat"

    }
}
