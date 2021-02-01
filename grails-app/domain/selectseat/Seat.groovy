package selectseat

class Seat {
    Long id
    Long version

    int status

    static belongsTo = [seatMap: SeatMap]

    static constraints = {
    }

    def beforeInsert(){
        status = 0
    }
}
