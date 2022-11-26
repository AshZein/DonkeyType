package Model;

import java.util.ArrayList;

public class PromptStatistics {
    private int score;
    private ArrayList<String> mistypedWords;
    private ArrayList<String> totalWords;
    private int correctlyTypedCharacters;
    private double time;
    private String phrase;

    /**
     * Constructor for PromptStatistics
     */
    PromptStatistics() {
        this.phrase = "";
        time = 0;
        correctlyTypedCharacters = 0;
        totalWords = new ArrayList<>();
        mistypedWords = new ArrayList<>();
    }

    /**
     * Constructor for PromptStatistics
     * @param phrase
     */
    PromptStatistics(String phrase) {
        this();
        this.phrase = phrase;
    }

    /**
     * Adds a character that the user typed
     * @param c
     * @param correct
     */
    void addCharacter(char c, boolean correct) {
        throw new UnsupportedOperationException();
    }

    /**
     * Reset the statistics state
     */
    void resetStatistics() {
        throw new UnsupportedOperationException();
    }

    /**
     * Changes the phrase that is currently being typed
     * @param phrase
     */
    void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    /**
     * Records the time taken to type all phrases since the last reset
     * @param time in seconds
     */
    void setTime(double time) {
        this.time = time;
    }

    /**
     * Getter for score
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for the user's mistyped words. This list may contain duplicates depending on whether the user mistypes
     * a word multiple times.
     * @return mistypedWords
     */
    public ArrayList<String> getMistypedWords() {
        return mistypedWords;
    }

    /**
     * Getter for a list of words typed by the user.
     * @return totalWords
     */
    public ArrayList<String> getTotalWords() {
        return mistypedWords;
    }

    /**
     * Getter for the time taken to type what is currently typed in seconds.
     * @return time
     */
    public double getTime() {
        return time;
    }


    /**
     * Calculate WPM of what is currently typed
     * @return WPM
     */
    public int calculateWPM() {
        throw new UnsupportedOperationException();
    }

    /**
     * Calculate the accuracy of what is currently typed
     * @return accuracy in percentage
     */
    public int calculateAccuracy() {
        throw new UnsupportedOperationException();
    }
}
