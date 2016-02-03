package mahaveer.reactive.paginate;

import com.sun.istack.internal.Nullable;

/**
 * Created by qxw121 on 1/1/16.
 */
public class NumInput {
    private final int num;
    private Character paginateKey = null;
    private String prefix = "";

    public NumInput(int num, Character paginateKey, String prefix) {
        this.num = num;
        this.paginateKey = paginateKey;
        this.prefix = prefix;
    }

    public NumInput(int num) {
        this.num = num;

    }

    public NumInput(int num, String prefix) {
        this(num, null, prefix);
    }

    public int getNum() {
        return num;
    }

    @Nullable
    public Character getpaginateKey() {
        return paginateKey;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString(){
        String pageKey = (paginateKey == null)?"":String.valueOf(paginateKey);
        return (prefix+num+pageKey);
    }
}
