package mahaveer.reactive.paginate;

import mahaveer.reactive.common.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertNotNull;

/**
 * Created by qxw121 on 1/1/16.
 */
public class NumMultiAccountServiceTest {

    private Scheduler rxScheduler;
    private ExecutorService threadPoolExecutor;
    private NumDataPaginationService dataPaginationService;
    private NumMultiAccountService service;

    @Before
    public void setup(){
        threadPoolExecutor = Executors.newFixedThreadPool(15);
        rxScheduler = Schedulers.from(threadPoolExecutor);
        dataPaginationService = new NumDataPaginationService(rxScheduler);
        service = new NumMultiAccountService(dataPaginationService, rxScheduler);
    }

    @Test
    public void testGetData(){
        List<String> accountsList= Arrays.asList("$","#","*");
        long start = System.currentTimeMillis();
        List<Map<String, String>> data = Util.blockingSingle(service.execute(accountsList));
        long end = System.currentTimeMillis();
        Util.println("Time (msec):"+(end-start));
        Util.println("Result:"+ data);
        assertNotNull(data);
    }


    @After
    public void teardown(){
        threadPoolExecutor.shutdown();
    }
}
