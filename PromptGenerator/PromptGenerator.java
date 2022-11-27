package PromptGenerator;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class PromptGenerator implements PromptInterface{
    Random random;
    RandomAccessFile words; // Data of filled with random words
    // TODO: method for closing all files?

    public PromptGenerator() throws IOException {
        random = new Random();
        words = new RandomAccessFile("Assets/wordFrequency.csv", "r");
    }


    @Override
    public String getNextPrompt() {
        try{
            return getRandomWord();
        } catch (Exception e){
            return "Error in getting word";
        }
    }

    private String getRandomWord() throws IOException {
        // Set pointer to some random position in file
        words.seek(random.nextLong(words.length()));
        // Read file from pointer to end of current line
        words.readLine();
        // CSV file with word on second column
        return words.readLine().split(",")[1]; // return the line after that

    }
}
