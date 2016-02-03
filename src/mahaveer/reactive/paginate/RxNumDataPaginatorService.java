package mahaveer.reactive.paginate;

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
public class RxNumDataPaginatorService {

    private Scheduler rxScheduler;

    public RxNumDataPaginatorService(Scheduler rxScheduler){
        this.rxScheduler = rxScheduler;
    }

    public Map<String, String> execute(int num){
        NumDataPaginator paginator = new NumDataPaginator();
        List<Observable<NumOutput>> observables = new ArrayList<>();
        for(int i=1; i <= num; i++){
            Observable<NumOutput> observable = Util.createPaginateObservable(paginator, new NumInput(i))
                    .subscribeOn(rxScheduler);
//            observable.flatMap(numOutput -> {
//                        return paginate(numOutput);
//                    }
//            );
            observables.add(observable);
        }
        Util.println("All observables set up now");
        Observable<Map<String, String>> endResultObserver = Observable.zip(observables, new MyZipper());
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
