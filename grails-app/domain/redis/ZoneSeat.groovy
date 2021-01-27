package redis

import selectseat.Event
import selectseat.Zone

class ZoneSeat implements Serializable{

//    Event event
//    Zone zone
    int eventId
    int ZoneId
    // bitmap: 每一排的座位
//    def columnSeat = []

    // 每一列的座位代號 代號是映射到每一排座位的key值

//    static mapWith = "none"

    @Override
    public String toString() {
        return "ZoneSeat{" +
                "event=" + eventId +
                ", zone=" + zoneId +
                '}';
    }
}
