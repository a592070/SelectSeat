package selectseat

class OrderList {
    Long id
    Long version

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated

    int totalNumber
    int totalPrice
    String orderCode
    String eventName
    String locationName

    static belongsTo = [user: User]
    static hasMany = [orderDetails: OrderDetail]


    static constraints = {
        orderCode nullable: false, unique: true
        totalNumber nullable: false
        totalPrice nullable: false
        eventName nullable: false
        locationName nullable: false
    }
}
