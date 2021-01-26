package selectseat

class User {
    Long id
    Long version

    String email
    static hasMany = [orders:OrderList]

    static constraints = {
        email email: true, blank: false, nullable: false, unique: true
    }
}
