package mahaveer.reactive.common;

import mahaveer.reactive.paginate.Paginator;
import rx.Observable;
import rx.Scheduler;
import rx.functions.FuncN;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by qxw121 on 1/3/16.
 */
public class UtilOld {

    public static <I,O> Observable<O> executeAsync(Function<I, O> funct, I input){
        // We can use annotations to define the scheduler
        return Observable.create(subscriber ->
        {
            subscriber.onStart();
            try {
                O data = funct.apply(input);
                subscriber.onNext(data);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });

    }

    public static <I,M,O> Observable<O> prepareAsyncServiceSplits(
            List<I> inputSplits, Service<I,Observable<M>> service, FuncN<O> zipper, Scheduler rxScheduler) {
        List<Observable<M>> observableSplits = new ArrayList<>();
        //TODO: Try Observabl.from(List).map instead
        for(I input: inputSplits){
            //TODO: Use HystrixObservableCommand.toObservable() instead of executing service inside flatMap
            Observable<M> observable = Observable.just(input).flatMap(x -> {
                return service.execute(input);
            });
            observable.subscribeOn(rxScheduler);
            observableSplits.add(observable);
        }
        Util.println("Util: All observable services set up");
        return Observable.just(observableSplits).flatMap(x -> {
                    Util.println("ZipObservable(asyncServiceSplit): About to zip" + zipper.getClass().getSimpleName());
                    return Observable.zip(observableSplits, zipper);
                }
        );


    }
    public static <I,M, O> Observable<O> prepareAsyncPaginatorSplits(
            List<I> inputSplits, Paginator<I, M> paginator, FuncN<O> zipper, Scheduler rxScheduler) {
        List<Observable<M>> observableSplits = new ArrayList<>();
        for(I input: inputSplits){
            Observable<M> observable = Util.createPaginateObservable(paginator, input)
                    .subscribeOn(rxScheduler);
            observableSplits.add(observable);
        }
        Util.println("Util: All observables set up now");
        return Observable.just(observableSplits).flatMap(x -> {
                    Util.println("ZipObservable(asyncPaginatorSplit): About to zip" + zipper.getClass().getSimpleName());
                    return Observable.zip(observableSplits, zipper);
                }
        );
    }
    public static <I,O> Observable<O> executeAsyncFlatmap(Function<I, O> funct, I input){
        // We can use annotations to define the scheduler
        return Observable.just(input).flatMap(x -> {
            Util.println("Exeucting actual function for:" + input);
            O data = funct.apply(x);
            return Observable.just(data);
        });
    }

    public static <I,O> O executeSplitsAsync(
            List<I> inputSplits, Paginator paginator, FuncN<O> zipper, Scheduler rxScheduler) {
        Observable<O> endResultObserver = Util.createAsyncPaginatorSplitsObservable(inputSplits, paginator, zipper, rxScheduler);
        return endResultObserver.toBlocking().single();

    }

    public static <I,O> O blockingSingle(Service<I, Observable<O>> asyncService, I input){
        Observable<O> outputObservable = asyncService.execute(input);
        Util.println("Prepared observable. Now about to do blocking execute");
        return outputObservable.toBlocking().single();
    }

}
