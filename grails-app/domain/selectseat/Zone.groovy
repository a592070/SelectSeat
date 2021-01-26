package selectseat

class Zone {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String name
    int  number
    int  columnNumber
    int  rowNumber

    static belongsTo = [event:Event]

    static constraints = {
        name nullable: false
        number nullable: false
        columnNumber nullable: false
        rowNumber nullable: false
    }
}
