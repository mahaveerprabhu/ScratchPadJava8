package mahaveer.reactive.paginate;

import mahaveer.reactive.common.Service;
import mahaveer.reactive.common.Util;
import rx.Observable;
import rx.Scheduler;
import rx.functions.FuncN;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qxw121 on 1/1/16.
 */
public class NumMultiAccountService implements Service<List<String>, Observable<List<Map<String,String>>>> {

    private Scheduler rxScheduler;
    private Service<NumInput, Observable<Map<String, String>>> dataPaginationService;

    public NumMultiAccountService(NumDataPaginationService dataPaginationService, Scheduler rxScheduler) {
        this.rxScheduler = rxScheduler;
        this.dataPaginationService = dataPaginationService;
    }


    public Observable<List<Map<String, String>>> execute(List<String> input) {
        List<NumInput> inputSplits = new ArrayList<>();
        int i = 1;
        for (String prefix : input) {
            inputSplits.add(new NumInput(i, prefix));
            i++;
        }
        Util.println("No of parallel accounts = " + inputSplits.size());
        Observable<List<Map<String, String>>> observableAccountTasks =
                Util.createAsyncServiceSplitsObservable(inputSplits, dataPaginationService, new AccountZipper(), rxScheduler);
        return observableAccountTasks;

    }

    public Observable<List<Map<String, String>>> executeWithHystrix(List<String> input) {
        List<NumInput> inputSplits = new ArrayList<>();
        int i = 1;
        for (String prefix : input) {
            inputSplits.add(new NumInput(i, prefix));
            i++;
        }
        Util.println("No of parallel accounts = " + inputSplits.size());
        Observable<List<Map<String, String>>> observableAccountTasks =
                Util.createAsyncServiceSplitsObservable(inputSplits, dataPaginationService, new AccountZipper(), rxScheduler);
        return observableAccountTasks;

    }

    private static class AccountZipper implements FuncN<List<Map<String, String>>> {

        @Override
        public List<Map<String, String>> call(Object... objects) {
            List<Map<String, String>> fullData = new ArrayList<>();
            for (Object obj : objects) {
                Map<String, String> numOutput = (Map<String, String>) obj;
                fullData.add(numOutput);
//                Util.println("Got data for:" + numOutput.getNum());
//                fullData.putAll(numOutput.getData());
            }
            return fullData;

        }
    }
}