package mahaveer.reactive.paginate;

import mahaveer.reactive.common.Util;
import mahaveer.reactive.common.UtilOld;
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
public class RxNumDataAggregatorService {

    private Scheduler rxScheduler;
    private NumDataService numDataService = new NumDataService();

    public RxNumDataAggregatorService(Scheduler rxScheduler){
        this.rxScheduler = rxScheduler;
    }

    public Map<String, String> execute(int num){
        List<Observable<NumOutput>> observables = new ArrayList<>();
        for(int i=1; i <= num; i++){
            Observable<NumOutput> observable = UtilOld.executeAsyncFlatmap(numDataService::execute, new NumInput(i))
                    .subscribeOn(rxScheduler);
//            observable.flatMap(numOutput -> {
//                        return paginate(numOutput);
//                    }
//            );
            observables.add(observable);
        }
        Util.println("All observables set up now");
        Observable<Map<String, String>> endResultObserver = Observable.zip(observables, new MyZipper());
        // Observable<Map<String, String>> endResultObserver = Observable.
        return endResultObserver.toBlocking().single();
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
