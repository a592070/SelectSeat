package selectseat

class OrderDetail {
    Long id;
    String type;

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated

    String areaSeatCode

    Order order
    Ticket ticket
    Area area

    static constraints = {
        areaSeatCode nullable: false, unique: true
    }
}
