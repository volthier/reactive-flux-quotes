package volthier.com.webfluxstockquoteservice.service;

import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import volthier.com.webfluxstockquoteservice.model.Quote;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class QuoteGeneratorServiceimplTest {

    QuoteGeneratorServiceImpl quoteGeneratorService = new QuoteGeneratorServiceImpl();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void fetchQuoteStream() throws Exception {
        //get quoteFlux of quotes
        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        quoteFlux.take(100).subscribe(System.out::println);

        //Thread.sleep(10000);
    }

    @Test
    public void fetchQuoteStreamCountDown() throws Exception {
        //get quoteFlux of quotes
        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        //subscriber lambda
        Consumer<Quote> println = System.out::println;

        //error Handler
        Consumer<Throwable> errorHandler = e -> System.out.println("Algo deu ruim FEEEEEIIIIOOOOO !!!!!");

        //set CountDown latch to 1
        CountDownLatch countDownLatch = new CountDownLatch(1);

        //runnable called upon completed, coount down latch
        Runnable allDone = () -> countDownLatch.countDown();

        quoteFlux.take(100).subscribe(println, errorHandler, allDone);

        countDownLatch.await();
    }
}
