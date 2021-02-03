package selectseat

class OrderDetail {
    Long id;
    Long version;

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated
//row
//column
//event_display date
//zone
//    String zoneName
//    String seatCode
//    String ticketType
//    int ticketPrice

    String seatRedisKey

    Ticket ticket
    Zone zone
    // ticket
    OrderList order
    static belongsTo = [order: OrderList, ticket: Ticket, zone:Zone]

    static constraints = {
        zoneName nullable: false, blank: false
        seatCode nullable: false, blank: false
        ticketType nullable: false, blank: false
        ticketPrice nullable: false, blank: false
    }

    String getSeatCode(){
        return ""
    }
    String getZoneName(){
        return zone.name
    }
    String getTicketType(){
        return ticket.type
    }

}
