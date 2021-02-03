package selectseat

import selectseat.utility.ToolService

class OrderList {
    Long id
    Long version

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated

    int totalNumber
    int totalPrice
    String orderCode = "O00000"
    String eventName
    String eventId
    String locationName
    User user

    static belongsTo = [user: User]
    static hasMany = [orderDetails: OrderDetail]

    static final String CODE_PREFIX = "O"

    static constraints = {
        orderCode nullable: false, unique: true
        totalNumber nullable: false
        totalPrice nullable: false
        eventName nullable: false
        eventId nullable: false
        locationName nullable: false
    }
    def beforeInsert(){
        def tmpNo = CODE_PREFIX + ToolService.generateRandomWord(5,true)
        while(OrderList.countByOrderCode(tmpNo) ){
            tmpNo = CODE_PREFIX +ToolService.generateRandomWord(5,true)
        }
        this.orderCode = tmpNo
    }
}
