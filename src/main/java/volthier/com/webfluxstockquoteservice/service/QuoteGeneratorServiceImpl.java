package volthier.com.webfluxstockquoteservice.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import volthier.com.webfluxstockquoteservice.model.Quote;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

@Service
public class QuoteGeneratorServiceImpl implements QuoteGeneratorService {

    private final MathContext mathContext = new MathContext( 2);
    private final Random random = new Random();
    private final List<Quote> prices = new ArrayList<>();


    public QuoteGeneratorServiceImpl() {
        this.prices.add(new Quote("Tabaco", 6.75));
        this.prices.add(new Quote("Luz", 180.06));
        this.prices.add(new Quote("Agua", 80.16));
        this.prices.add(new Quote("Internet", 145.09));
        this.prices.add(new Quote("Telefone", 120.00));
        this.prices.add(new Quote("Gasolina", 380.71));
        this.prices.add(new Quote("Condominio", 480.32));
        this.prices.add(new Quote("Emprestimo", 250.00));
        this.prices.add(new Quote("Compras Mes", 160.16));
        this.prices.add(new Quote("Lazer", 160.16));

    }



    @Override
    public Flux<Quote> fetchQuoteStream(Duration period) {
        return Flux.generate(() -> 0,
                (BiFunction<Integer, SynchronousSink<Quote>, Integer>) (index, sink) -> {
                    Quote updatedQuote = updatedQuote(this.prices.get(index));
                    sink.next(updatedQuote);
                    return ++index % this.prices.size();
        }).zipWith(Flux.interval(period))
                .map(t -> t.getT1())
                .map(quote -> {
                    quote.setInstant(Instant.now());
            return quote;
        }).log("volthier.com.BRulz");

    }

    private Quote updatedQuote(Quote quote){
        BigDecimal priceChange = quote.getPrice().multiply(new BigDecimal( 0.05 * this.random.nextDouble()), this.mathContext);
        return new Quote(quote.getTicker(), quote.getPrice().add(priceChange));
    }
}
