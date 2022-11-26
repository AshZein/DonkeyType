package Model;

import java.util.ArrayList;
import java.util.HashSet;

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
        phrase = "";
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
     * Removes the last typed character;
     */
    void removeCharacter() {throw new UnsupportedOperationException();}

    /**
     * Reset the statistics state
     */
    void resetStatistics() {
        phrase = "";
        time = 0;
        correctlyTypedCharacters = 0;
        totalWords.clear();
        mistypedWords.clear();
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
        return (int) Math.ceil(time / ((double) correctlyTypedCharacters / 5));
    }

    /**
     * Calculate the accuracy of what is currently typed based on the percentage of mistyped words.
     * @return accuracy in percentage
     */
    public int calculateAccuracy() {
        HashSet<String> mistypedSet = new HashSet<>(mistypedWords);
        HashSet<String> totalTypedSet = new HashSet<>(totalWords);
        HashSet<String> intersection = new HashSet<>(totalWords);
        intersection.retainAll(mistypedSet);
        return (int) Math.ceil((double) intersection.size() / totalTypedSet.size()) * 100;
    }
}
