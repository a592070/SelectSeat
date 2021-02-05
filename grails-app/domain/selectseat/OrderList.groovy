package selectseat

import selectseat.utility.ToolService

class OrderList {
    Long id
    Long version

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated

//    int totalNumber
    int totalPrice
    String orderCode
    String locationName

    Event event
    Location location
    User user

    static belongsTo = [user: User]
    static hasMany = [orderDetails: OrderDetail]

    static final String CODE_PREFIX = "OR"

    static constraints = {
        orderCode nullable: false, unique: true
        totalPrice nullable: false
        locationName nullable: false
    }
    def beforeInsert(){
        def tmpNo = CODE_PREFIX + ToolService.generateRandomWord(5,true)
        while(OrderList.countByOrderCode(tmpNo) ){
            tmpNo = CODE_PREFIX +ToolService.generateRandomWord(5,true)
        }
        this.orderCode = tmpNo
    }

    def getTotalNumber(){
        return orderDetails.size()
    }
}
