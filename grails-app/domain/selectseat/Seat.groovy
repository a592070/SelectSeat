package selectseat

class Seat {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    // 0,0,0,1,0
    // [[true,...],
    // [xxx]]
    String siteBitmap
    String columnName
    int rowAmount

//    transient redisService

    static belongsTo = [zone:Zone]
    static constraints = {
        siteBitmap nullable: false
        columnName nullable: false
        rowAmount nullable: false
    }


}
