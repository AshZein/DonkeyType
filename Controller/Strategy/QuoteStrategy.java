package Controller.Strategy;

import PromptGenerator.PQuotePromptGenerator;
import PromptGenerator.NPQuotePromptGenerator;

import java.io.IOException;

public class QuoteStrategy implements Strategy{
    PQuotePromptGenerator pQuotes;
    NPQuotePromptGenerator nPQuotes;

    public QuoteStrategy() throws IOException {
        pQuotes = new PQuotePromptGenerator();
        nPQuotes = new NPQuotePromptGenerator();
    }

    @Override
    public String execute(int[] data) {
        if (data[0] == 1){
            return pQuotes.getNextPrompt();
        }
        else{
            return nPQuotes.getNextPrompt();
        }
    }
}
