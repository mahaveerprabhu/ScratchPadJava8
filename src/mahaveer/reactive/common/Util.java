package mahaveer.reactive.common;

import mahaveer.reactive.paginate.Paginator;
import rx.Observable;
import rx.Scheduler;
import rx.functions.FuncN;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**x
 * Created by qxw121 on 1/1/16.
 */
public class Util {

    public static <I,M, O> Observable<O> createAsyncPaginatorSplitsObservable(
            List<I> inputSplits, Paginator<I, M> paginator, FuncN<O> zipper, Scheduler rxScheduler) {
        List<Observable<M>> observableSplits = new ArrayList<>();
        for(I input: inputSplits){
            Observable<M> observable = Util.createPaginateObservable(paginator, input);
            observable = schedule( observable, rxScheduler);
            observableSplits.add(observable);
        }
        Util.println("Util: All Paginator observables set up now");
        return createSplitsZipperObservable(zipper, observableSplits);
    }

    private static <M> Observable<M> schedule(Observable<M> observable, Scheduler rxScheduler) {
        if(rxScheduler != null) {
            return observable.subscribeOn(rxScheduler);
        }
        return observable;
    }


    public static <I,O> Observable<O> createPaginateObservable(Paginator<I, O> paginator, I input){
        // We can use annotations to define the scheduler
        return Observable.just(input).flatMap(x -> {
            O output = paginator.execute(input);
            paginator.collect(output);
            if (paginator.hasMore(output)) {
                I nextInput = paginator.prepareNextInput(input, output);
                return createPaginateObservable(paginator, nextInput);
            } else {
                return Observable.just(paginator.getFinalOutput());
            }

        });

    }

    private static <M, O> Observable<O> createSplitsZipperObservable(FuncN<O> zipper, List<Observable<M>> observableSplits) {
        return Observable.just(observableSplits).flatMap(x -> {
                    println("ZipObservable: About to zip" + zipper.getClass().getSimpleName());
                    return Observable.zip(observableSplits, zipper);
                }
        );
    }


    public static <I,M,O> Observable<O> createAsyncServiceSplitsObservable(
            List<I> inputSplits, Service<I, Observable<M>> service, FuncN<O> zipper, Scheduler rxScheduler) {
        List<Observable<M>> observableSplits = new ArrayList<>();
        //TODO: Try Observabl.from(List).map instead
        for(I input: inputSplits){

            Observable<M> observable = createServiceObservable(service, input);
            observable = schedule(observable, rxScheduler);
            observableSplits.add(observable);
        }
        Util.println("Util: All Service observables set up now");
        return createSplitsZipperObservable(zipper, observableSplits);


    }

    private static <I, M> Observable<M> createServiceObservable(Service<I, Observable<M>> service, I input) {
        //TODO: Use HystrixObservableCommand.toObservable() instead of executing service inside flatMap
        return Observable.just(input).flatMap(x -> {
            return service.execute(input);
        });
    }

    public static <O> O blockingSingle(Observable<O> taskToExecute){
        println("About to do blocking execute:" + taskToExecute.getClass().getTypeName());
        return taskToExecute.toBlocking().single();
    }


    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm.ss.SSS");

    public static void println(String msg){
        System.out.println(Thread.currentThread().getName() + "->" + msg);
    }

    public static void printTime(){
        println(dateFormat.format(new Date()));
    }

}
