package selectseat

class Seat {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String siteBitmap
    String xAxis
    String yAxis

    static belongsTo = [zone:Zone]
    static constraints = {
        siteBitmap nullable: false
        xAxis nullable: false
        yAxis nullable: false
    }
}
