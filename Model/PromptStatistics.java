package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class PromptStatistics {
    private int score;
    private final ArrayList<Character> WORD_DELIMETERS = new ArrayList<>(Arrays.asList(' ', ',', '.', '?', '!', '/', '@', ';'));
    private ArrayList<String> mistypedWords;
    private ArrayList<String> totalWords;
    private int correctlyTypedCharacters;
    private double time;
    private String phrase;
    private int cursorPos;

    /**
     * Constructor for PromptStatistics
     */
    PromptStatistics() {
        phrase = "";
        cursorPos = 0;
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
        setPhrase(phrase);
    }

    /**
     * Adds a character that the user typed
     * @param c
     * @param correct
     */
    void addCharacter(char c, boolean correct) {
        // check if input is valid (fail silently)
        if (cursorPos >= phrase.length()) return;

        // update score
        if (correct) {
            correctlyTypedCharacters++;
            score += 2;
        } else {
            score--;
            // get current word

            if (!WORD_DELIMETERS.contains(phrase.charAt(cursorPos))) {
                // update mistypedWord
                int i = cursorPos;
                while (!(i == 0 || WORD_DELIMETERS.contains(phrase.charAt(i)))) {
                    i--;
                }
                int j = cursorPos;
                while (j < phrase.length() && !WORD_DELIMETERS.contains(phrase.charAt(j))) {
                    j++;
                }
                mistypedWords.add(phrase.substring(i, j));
            }
        }


        cursorPos++;

    }

    /**
     * Removes the last typed character;
     */
    void removeCharacter() {
        if (cursorPos > 0) cursorPos--;
    }

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
        cursorPos = 0;
        this.totalWords.addAll(Arrays.asList(phrase.split("\\W+")));
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
     * Getter for the user's mistyped words.
     * @return mistypedWords
     */
    public ArrayList<String> getMistypedWords() {

        return new ArrayList<>(new HashSet<String>(mistypedWords));
    }

    /**
     * Getter for a list of words typed by the user.
     * @return totalWords
     */
    public ArrayList<String> getTotalWords() {
        return totalWords;
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
        return (int) Math.ceil(((double) correctlyTypedCharacters / 5) / (time / 60));
    }

    /**
     * Calculate the accuracy of what is currently typed based on the percentage of mistyped words.
     * @return accuracy in percentage
     */
    public int calculateAccuracy() {
        HashSet<String> mistypedSet = new HashSet<>(mistypedWords);
        HashSet<String> totalTypedSet = new HashSet<>(totalWords);
        HashSet<String> intersection = new HashSet<>(totalWords);
        intersection.removeAll(mistypedSet);
        return (int) (Math.ceil((double) intersection.size() / totalTypedSet.size() * 10000)) / 100;
    }
}
