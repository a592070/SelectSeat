package selectseat

class SeatRecord {
/* Default (injected) attributes of GORM */
    long id
    Long version

    String siteBitmap

    Area area
    static constraints = {
        siteBitmap nullable: false
        area nullable: false
    }
}
