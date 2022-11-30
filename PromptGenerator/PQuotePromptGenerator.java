package PromptGenerator;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class PQuotePromptGenerator implements PromptInterface{
    RandomAccessFile quotes;
    Random random;

    public PQuotePromptGenerator() throws IOException {
        random = new Random();
        quotes = new RandomAccessFile("Assets/quotes_all_p.csv", "r");
    }

    @Override
    public String getNextPrompt() {
        try{
            return getQuote();
        } catch (Exception e){
            return "Error in getting word";
        }
    }

    private String getQuote() throws IOException{
        quotes.seek(random.nextLong(quotes.length()));

        quotes.readLine();

        return quotes.readLine().split(";")[1];
    }
}
