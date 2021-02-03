package selectseat

class OrderDetail {
    Long id;
    Long version;

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated

    String zoneName
    String seatCode
    String ticketType
    int ticketPrice
    OrderList order

    static belongsTo = [order: OrderList]

    static constraints = {
        zoneName nullable: false, blank: false
        seatCode nullable: false, blank: false
        ticketType nullable: false, blank: false
        ticketPrice nullable: false, blank: false
    }
}
