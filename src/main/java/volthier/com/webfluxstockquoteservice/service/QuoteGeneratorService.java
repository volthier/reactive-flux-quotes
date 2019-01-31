package volthier.com.webfluxstockquoteservice.service;

import reactor.core.publisher.Flux;
import volthier.com.webfluxstockquoteservice.model.Quote;

import java.time.Duration;

public interface QuoteGeneratorService {

    Flux<Quote> fetchQuoteStream(Duration period);
}
