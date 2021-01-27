package selectseat

class Person {
    static mapWith = "redis"

    String firstName
    String lastName

    static constraints = {
        firstName blank:false
        lastName blank:false
    }

    static mapping = {
        lastName index:true
    }
}
