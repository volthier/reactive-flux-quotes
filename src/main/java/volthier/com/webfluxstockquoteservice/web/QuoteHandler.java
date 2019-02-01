package volthier.com.webfluxstockquoteservice.web;

import org.springframework.stereotype.Component;
import volthier.com.webfluxstockquoteservice.service.QuoteGeneratorService;

@Component
public class QuoteHandler {

    private final QuoteGeneratorService quoteGeneratorService;

    public QuoteHandler(QuoteGeneratorService quoteGeneratorService) {
        this.quoteGeneratorService = quoteGeneratorService;
    }


}
