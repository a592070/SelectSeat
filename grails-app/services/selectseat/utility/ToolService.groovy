package selectseat.utility

import grails.gorm.transactions.Transactional

@Transactional
class ToolService {

    def serviceMethod() {
    }

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
}
