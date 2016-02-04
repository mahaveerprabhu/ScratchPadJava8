package mahaveer.reactive.paginate;

import mahaveer.reactive.common.Service;
import mahaveer.reactive.common.Util;
import rx.Observable;
import rx.Scheduler;
import rx.functions.FuncN;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qxw121 on 1/1/16.
 */
public class NumDataPaginationService implements Service<NumInput, Observable<Map<String,String>>> {
    private Scheduler rxScheduler;

    public NumDataPaginationService(Scheduler rxScheduler){
        this.rxScheduler = rxScheduler;
    }

    @Override
    public Observable<Map<String, String>> execute(NumInput input){
        NumDataPaginator paginator = new NumDataPaginator();
        List<NumInput> inputSplits = new ArrayList<>();
        int num = input.getNum();
        for(int i=1; i <= num; i++){
            inputSplits.add(new NumInput(i,input.getPrefix()));
        }
        Util.println("No of paralle tasks = " + inputSplits.size());
        return Util.createAsyncPaginatorSplitsObservable(inputSplits, paginator, new MyZipper(), rxScheduler);
    }


    private static class  MyZipper implements FuncN<Map<String, String>> {

        @Override
        public Map<String, String> call(Object... objects) {
            Map<String, String> fullData = new ConcurrentHashMap<>();
            for(Object obj: objects){
                NumOutput numOutput = (NumOutput) obj;
                Util.println("Got data for:" + numOutput.getNum());
                fullData.putAll(numOutput.getData());
            }
            return fullData;
        }
    }
}
