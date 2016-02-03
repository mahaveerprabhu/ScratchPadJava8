package mahaveer.reactive.paginate;

import mahaveer.reactive.common.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qxw121 on 1/1/16.
 */
public class NumDataService {

    private static char PAGINATE_CHAR_MAX = 'C';
    public NumOutput execute(NumInput numInput){
        int num = numInput.getNum();
        Character paginateKey = numInput.getpaginateKey();
        String suffix = (paginateKey != null)?String.valueOf(paginateKey):"";
        Map<String, String> data = new HashMap<>();
        try {
            Util.println("DataService - About to generate data for: " + numInput);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i=1; i <= num; i++){
            String key = String.format("%s%s%s_%s", numInput.getPrefix(), num, suffix, i);
            data.put(key, key+":val");
        }

        Util.println("DataService - Generated data for: " + numInput+
            " Data: "+data);
        if(numInput.getNum() == 1){
            return new NumOutput(num, data, numInput.getPrefix(), getNextPaginateKey(numInput.getpaginateKey()));
        }else{
            return new NumOutput(num, data, numInput.getPrefix());
        }



    }

    private Character getNextPaginateKey(Character character) {
        if(character == null){
            return 'A';
        }else{
            if(character == 'C'){
                return null;
            }else{
                return ((char) (character + 1));
            }
        }
    }
}
