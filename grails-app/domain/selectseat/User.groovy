package selectseat

class User {
    Long id
    Long version

    String email

    static constraints = {
        email nullable: false, unique: true
    }
}
