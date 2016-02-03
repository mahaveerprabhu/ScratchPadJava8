package mahaveer.reactive.paginate;

import mahaveer.reactive.common.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qxw121 on 1/1/16.
 */
public class NumDataPaginator implements Paginator<NumInput, NumOutput>{
    NumDataService service = new NumDataService();
    private Map<String, String> outputCollector = new HashMap<>();
    private int num;
    private String prefix = "";
    @Override
    public NumOutput execute(NumInput input) {
        num = input.getNum();
        prefix = input.getPrefix();
        Character pageKey = input.getpaginateKey();
        Util.println("Paginator execute for:" + input);
        return service.execute(input);
    }

    @Override
    public boolean hasMore(NumOutput output) {
        boolean hasMoreRecords = output.getPaginateKey() != null;
        Util.println("Output:"+output+" HasMore:"+hasMoreRecords);
        return hasMoreRecords;
    }

    @Override
    public NumInput prepareNextInput(NumInput previousInput, NumOutput previousOutput) {
        return new NumInput(previousInput.getNum(), previousOutput.getPaginateKey(), previousInput.getPrefix());
    }

    @Override
    public NumOutput getFinalOutput() {
        return new NumOutput(num, outputCollector, prefix);
    }


    @Override
    public void collect(NumOutput numOutput) {
        Util.println("Collection for output: "+numOutput+
        " data:"+numOutput.getData());
        outputCollector.putAll(numOutput.getData());
    }


}
