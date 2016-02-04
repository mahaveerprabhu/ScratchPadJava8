package mahaveer.reactive.paginate;

import java.util.Map;

/**
 * Created by qxw121 on 1/1/16.
 */
public class NumOutput {
    private final int num;
    private final Map<String, String> data;
    private Character paginateKey;
    private String prefix="";


    public NumOutput(int num, Map<String, String> data, String prefix) {
        this.num = num;
        this.data = data;
    }

    public NumOutput(int num, Map<String, String> data, String prefix, Character paginateKey) {
        this(num, data, prefix);
        this.paginateKey = paginateKey;
    }

    public int getNum() {
        return num;
    }

    public Map<String, String> getData() {
        return data;
    }

    public Character getPaginateKey() {
        return paginateKey;
    }

    @Override
    public String toString(){
        String pageKey = (paginateKey == null)?"":String.valueOf(paginateKey);
        return (prefix+num+pageKey);

    }
}
