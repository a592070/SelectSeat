package selectseat


import static selectseat.redis.SelectSeatRedisService.INTERPOINT

class OrderDetail {
    Long id;
    Long version;

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated

    String seatRedisKey

    Ticket ticket
    Zone zone
    OrderList order
    static belongsTo = [order: OrderList, ticket: Ticket, zone:Zone]

    static constraints = {
        zoneName nullable: false, blank: false
        seatRedisKey nullable: false, blank: false
        ticketType nullable: false, blank: false
    }

    String getSeatCode(){
        def rowColIdx = seatRedisKey.split(INTERPOINT)
        return ""
    }
    String getZoneName(){
        return zone.name
    }
    String getTicketType(){
        return ticket.type
    }

}
