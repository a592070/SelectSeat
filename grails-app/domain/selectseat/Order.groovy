package selectseat

class Order {
    Long id
    Long version

    String orderCode

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated

    int totalNumber

    User user
    Event event

    static constraints = {
        orderCode nullable: false, unique: true
        totalNumber nullable: false
    }
}
