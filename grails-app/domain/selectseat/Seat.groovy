package selectseat

class Seat {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String siteBitmap
    String columnName
    int rowAmount

    static belongsTo = [zone:Zone]
    static constraints = {
        siteBitmap nullable: false
        columnName nullable: false
        rowAmount nullable: false
    }
}
