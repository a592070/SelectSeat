package selectseat

class Seat {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String seatBitmap
    String xAxis
    String yAxis

    static belongsTo = [zone:Zone]
    static constraints = {
        seatBitmap nullable: false
        xAxis nullable: false
        yAxis nullable: false
    }
}
