package mahaveer.reactive;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;


public class Main {


    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm.ss.SSS");
    public static void main(String[] args) {

        Main obj = new Main();
	    // write your code here
        String[] shapesArray = {"rectangle", "squre", "circle"};
        List<String> shapesList = Arrays.asList(shapesArray);
//        obj.observableSubscribe(shapesArray);
//        obj.createCustomObservable(shapesList);
        printTime();
        println("AsyncData: " + obj.getBigDataUsingAsync(3));
        printTime();
//        println("Data: " + obj.getBigData(3));
//        printTime();
        obj.threadPoolExecutor.shutdown();
    }

    private Scheduler rxScheduler;
    ExecutorService threadPoolExecutor;
    public Main(){
        threadPoolExecutor = Executors.newFixedThreadPool(5);
        rxScheduler = Schedulers.from(threadPoolExecutor);
    }


    private Map<String, String> getBigData(int num){
        Map<String, String> fullData = new HashMap<>();
        for(int i=1; i <= num; i++){
            fullData.putAll(generateData(new NumInput(i, "")).getData());
        }
        return fullData;
    }

    private Map<String, String> getBigDataUsingAsync(int num){
        List<Observable<NumOutput>> observables = new ArrayList<>();
        for(int i=1; i <= num; i++){
            Observable<NumOutput> observable = executeAsync(this::generateData, new NumInput(i, ""))
                    .subscribeOn(rxScheduler);
//            observable.flatMap(numOutput -> {
//                        return paginate(numOutput);
//                    }
//            );
            observables.add(observable);
        }
        println("All observables set up now");
        Observable<Map<String, String>> endResultObserver = Observable.zip(observables, new MyZipper());
        // Observable<Map<String, String>> endResultObserver = Observable.
        return endResultObserver.toBlocking().single();
    }

    private static class  MyZipper implements FuncN<Map<String, String>>{

        @Override
        public Map<String, String> call(Object... objects) {
            Map<String, String> fullData = new ConcurrentHashMap<>();
            for(Object obj: objects){
                NumOutput numOutput = (NumOutput) obj;
                println("Got data for:"+numOutput.getNum());
                fullData.putAll(numOutput.getData());
            }
            return fullData;
        }
    }

    private <I,O> Observable<O> executeAsync(Function<I, O> funct, I input){
        // We can use annotations to define the scheduler
        return Observable.create(subscriber ->
        {
            subscriber.onStart();
            try{
                O data = funct.apply(input);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }catch(Exception e){
                subscriber.onError(e);
            }
        });

    }

    public NumOutput generateDataAndPaginate(int num){
        String[] suffixes = {"A", "B", "C"};
        Map<String, String> data;
        if(num%2 == 0){
            Map<String, String> mergedData = new HashMap<>();
            for(String str: suffixes){
                Map<String, String> partialData = generateData(new NumInput(num, str)).getData();
                mergedData.putAll(partialData);
            }
            println("Generated full data for:"+num);
            return new NumOutput(num, mergedData);
        }else{
            return new NumOutput(num, generateData(new NumInput(num, "")).getData());
        }
    }

    private static class NumInput{
        private final int num;
        private final String suffix;

        public NumInput(int num, String suffix) {
            this.num = num;
            this.suffix = suffix;
        }

        public int getNum() {
            return num;
        }

        public String getSuffix() {
            return suffix;
        }
    }
    private static class NumOutput{
        private final int num;
        private final Map<String, String> data;

        public NumOutput(int num, Map<String, String> data) {
            this.num = num;
            this.data = data;
        }

        public int getNum() {
            return num;
        }

        public Map<String, String> getData() {
            return data;
        }
    }

    // costly call. Takes a second to complete
    public NumOutput generateData(NumInput numInput) {
        int num = numInput.getNum();
        String sufix = numInput.getSuffix();
        Map<String, String> data = new HashMap<>();
        try {
            println("About to generate data for: " + num);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i=1; i <= num; i++){
            String key = String.format("%s%s_%s", num, sufix, i);
            data.put(key, key+":val");
        }
        println("Generated data for: " + num + sufix);
        return new NumOutput(num, data);
    }


    private void observableSubscribe(String[] shapesArray){
        Observable.from(shapesArray).subscribe(
                x -> println(x),
                x -> println("Error processing:" + x),
                () -> println("Completed")
        );

    }

    private static void println(String msg){
        System.out.println(Thread.currentThread().getName() + "->" + msg);
    }

    private static void printTime(){
        println(dateFormat.format(new Date()));
    }

    private void createCustomObservable(List<String> shapesList){
        println("******* Creating custom observable *******");
        Subscriber<String> s = null;
        Observable<String> observable = Observable.create(subscriber -> {
            subscriber.onStart();
            Iterator<String> iterator = shapesList.iterator();
            try {
                while (iterator.hasNext()) {
                    //introduce a processing delay for each item
                    Thread.sleep(1000);
                    if (subscriber.isUnsubscribed()) return;
                    subscriber.onNext(iterator.next());
                }
                if (!subscriber.isUnsubscribed()) subscriber.onCompleted();
            } catch (Exception e) {
                if (!subscriber.isUnsubscribed()) subscriber.onNext(iterator.next());
            }
        });
        println("Observable just created, not executed yet");
        observable.subscribe(
                x -> println(x),
                x -> println("Error processing:" + x),
                () -> println("Completed")
        );
        println("Completed execution");
    }


}
