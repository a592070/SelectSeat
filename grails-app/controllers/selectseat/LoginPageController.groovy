package selectseat

class LoginPageController {

    def index() { }

    def login(String query){
        def userResult =User.where{email =~ query}.get()
        if(userResult){
            int totalTicket=0
            def list = userResult.orders.unique()
            for (OrderList ol in list) {
                for (OrderDetail od in ol.orderDetails){
                    totalTicket++
                }

            }
            return [result: "有這個人", list:list, total: totalTicket]
        }

        return [result:"查無此人"]
    }
}
