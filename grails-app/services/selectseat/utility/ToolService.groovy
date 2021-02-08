package selectseat.utility

import grails.gorm.transactions.Transactional
import org.hashids.Hashids
import selectseat.redis.SelectSeatRedisService

import static selectseat.redis.SelectSeatRedisService.ZONE_SEAT_FIELD_COLUMN_PREFIX
import static selectseat.redis.SelectSeatRedisService.ZONE_SEAT_FIELD_ROW_PREFIX

@Transactional
class ToolService {

    static String generateRandomWord(int digit, boolean numberOnly=false){
        //產生亂數密碼
        int[] word = new int[digit];
        int mod;
        for(int i = 0; i < digit; i++){
            if(numberOnly){
                word[i]=(int)((Math.random()*10) + 48);
            }else{
                mod = (int)((Math.random()*7)%3);
                if(mod ==1){ //數字
                    word[i]=(int)((Math.random()*10) + 48);
                }else if(mod ==2){ //大寫英文
                    word[i] = (char)((Math.random()*26) + 65);
                }else{ //小寫英文
                    word[i] = (char)((Math.random()*26) + 97);
                }
            }
        }
        StringBuffer randomWord = new StringBuffer();
        for(int j = 0; j < digit; j++){
            randomWord.append((char)word[j]);
        }
        return randomWord.toString();
    }

    static String encodeHashid(clazz,id){
        def hashidObj = new Hashids(clazz.name.replace('.',"_"),16)
        return hashidObj.encode(id)
    }
    static long decodeHashid(clazz,hashid){
        def hashidObj = new Hashids(clazz.name.replace('.',"_"),16)
        return hashidObj.decode(hashid)[0]
    }

    static List<List> seatMap2List(Map<String, String> seats, int rowCount, int columnCount){
        def seatList = []
        for (i in 0..<rowCount) {
            def rowList = []
            for (j in 0..<columnCount) {
                String keyName = ZONE_SEAT_FIELD_ROW_PREFIX + i + '_'+ZONE_SEAT_FIELD_COLUMN_PREFIX + j
                rowList << seats.get(keyName)
            }
            seatList << rowList
        }
        return seatList
    }

}
