package mahaveer.reactive.common;

import mahaveer.reactive.paginate.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.functions.FuncN;

import java.util.ArrayList;
import java.util.List;

/**x
 * Created by qxw121 on 1/1/16.
 */
@Component
public class AsyncUtil {

    private static final Logger log = LoggerFactory.getLogger(AsyncUtil.class);

    private Scheduler scheduler;

    public AsyncUtil() {};

    public AsyncUtil(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    public <I,M, O> Observable<O> createAsyncPaginatorSplitsObservable(
            List<I> inputSplits, Paginator<I, M> paginator, FuncN<O> zipper) {
        return createAsyncPaginatorSplitsObservable(inputSplits, paginator, zipper);
    }
    public <I,M, O> Observable<O> createAsyncPaginatorSplitsObservable(
            List<I> inputSplits, Paginator<I, M> paginator, FuncN<O> zipper, Scheduler rxScheduler) {
        List<Observable<M>> observableSplits = new ArrayList<>();
        for(I input: inputSplits){
            Observable<M> observable = createPaginateObservable(paginator, input);
            setScheduler( observable, rxScheduler);
            observableSplits.add(observable);
        }
        log.debug("Util: All Paginator observables set up now");
        return createSplitsZipperObservable(zipper, observableSplits);
    }

    private <M> void setScheduler(Observable<M> observable, Scheduler rxScheduler) {
        if(rxScheduler != null) {
            observable.subscribeOn(rxScheduler);
        }
    }


    public <I,O> Observable<O> createPaginateObservable(final Paginator<I, O> paginator, final I input){
        // We can use annotations to define the scheduler
        return Observable.just(input).flatMap(new Func1<I, Observable<O>>() {
            @Override
            public Observable<O> call(I x) {
                O output = paginator.execute(input);
                paginator.collect(output);
                if (paginator.hasMore(output)) {
                    I nextInput = paginator.prepareNextInput(input, output);
                    return createPaginateObservable(paginator, nextInput);
                } else {
                    return Observable.just(paginator.getFinalOutput());
                }
            }
        });


    }

    private <M, O> Observable<O> createSplitsZipperObservable(final FuncN<O> zipper, final List<Observable<M>> observableSplits) {

        return Observable.just(observableSplits).flatMap(new Func1<List<Observable<M>>, Observable<O>>() {
            @Override
            public Observable<O> call(List<Observable<M>> observables) {
                log.debug("ZipObservable: About to zip" + zipper.getClass().getSimpleName());
                return Observable.zip(observables, zipper);

            }
        });
    }


    public <I extends ServiceInput,M extends ServiceOutput,O extends ServiceOutput> Observable<O> createAsyncServiceSplitsObservable(
            List<I> inputSplits, AsyncService<I, M> service, FuncN<O> zipper) {
        return createAsyncServiceSplitsObservable(inputSplits, service, zipper);
    }

    public <I extends ServiceInput,M extends ServiceOutput,O extends ServiceOutput> Observable<O> createAsyncServiceSplitsObservable(
            List<I> inputSplits, AsyncService<I, M> service, FuncN<O> zipper, Scheduler rxScheduler) {
        List<Observable<M>> observableSplits = new ArrayList<>();
        //TODO: Try Observabl.from(List).map instead
        for(I input: inputSplits){

            Observable<M> observable = createServiceObservable(service, input);
            observable.subscribeOn(rxScheduler);
            observableSplits.add(observable);
        }
        log.debug("Util: All Service observables set up now");
        return createSplitsZipperObservable(zipper, observableSplits);


    }

    private <I extends ServiceInput, M extends ServiceOutput> Observable<M> createServiceObservable(
                                            final AsyncService<I, M> service, final I input) {
        //TODO: Use HystrixObservableCommand.toObservable() instead of executing service inside flatMap
        return Observable.just(input).flatMap(new Func1<I, Observable<M>>(){

            @Override
            public Observable<M> call(I x) {
                return service.observe(x);
            }
        });
    }

    public <O> O blockingSingle(Observable<O> taskToExecute){
        log.debug("About to do blocking execute:" + taskToExecute.getClass().getName());
        return taskToExecute.toBlocking().single();
    }



}
