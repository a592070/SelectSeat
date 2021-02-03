package selectseat

class Seat {
    Long id
    Long version

    int status
    int seatsIdx
    SeatMap seatMap

    static belongsTo = [seatMap: SeatMap]

    static constraints = {
        seatsIdx: insert: false
    }
    static mapping = {
        seatsIdx column: "seats_idx", insertable: false, updateable: false
    }

    def beforeInsert(){
        status = 0
    }
}
