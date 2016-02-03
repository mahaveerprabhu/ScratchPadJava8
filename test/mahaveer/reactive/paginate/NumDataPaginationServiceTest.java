package mahaveer.reactive.paginate;

import mahaveer.reactive.common.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertNotNull;

/**
 * Created by qxw121 on 1/1/16.
 */
public class NumDataPaginationServiceTest {

    private Scheduler rxScheduler;
    private ExecutorService threadPoolExecutor;
    private NumDataPaginationService service;

    @Before
    public void setup(){
        threadPoolExecutor = Executors.newFixedThreadPool(10);
        rxScheduler = Schedulers.from(threadPoolExecutor);
        service = new NumDataPaginationService(rxScheduler);
    }

    @Test
    public void testGetData(){
        Map<String, String> data = Util.blockingSingle(service.execute(new NumInput(3)));
        Util.println("Result:"+ data);
        assertNotNull(data);
    }


    @After
    public void teardown(){
        threadPoolExecutor.shutdown();
    }
}
