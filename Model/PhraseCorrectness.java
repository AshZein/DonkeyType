package Model;

import java.util.ArrayList;
import java.util.List;

public class PhraseCorrectness implements Observable{
    // The current phrase being compared against
    private String phrase;

    // Correctness of typed characters so far
    private boolean[] correctness;

    // Cursor position is exclusive. Everything below it has been typed, character at it has not been typed
    private int cursorPos;

    // Current state of the phrase
    private PhraseState phraseState;

    // List of observers
    private List<Observer> observers = new ArrayList<>();


    public PhraseCorrectness(String s) {
        // init attributes
        phraseState = new PhraseState();
        setPhrase(s);
    }

    public PhraseCorrectness(){
        // init attributes
        phraseState = new PhraseState();
        setPhrase("");

        // update phraseState
        updatePhraseState();
    }

    /**
     * Change the 'phrase' to be 's', reset 'cursorPos' to zero,
     * make new correctness array
     * Updates phraseCorrectness to match
     *
     * PhraseState must be initialized
     * @param s new string the phrase will be set to
     */
    public void setPhrase(String s){
        phrase = s;
        correctness = new boolean[s.length()];
        cursorPos = 0;
        updatePhraseState();
        notifyObservers();
    }

    /**
     * Type a new character.
     * @param c character that will be compared to the corresponding one in 'phrase'
     */
    public void addCharacter(char c){
        correctness[cursorPos] = (phrase.charAt(cursorPos) == c);
        cursorPos++;
        updatePhraseState();
        notifyObservers();
    }

    /**
     * Forget that the last character has been typed
     */
    public void removeCharacter(){
        cursorPos--;
        updatePhraseState();
        notifyObservers();
    }

    /**
     * Update the attributes in PhraseState to match the ones on phrase correctness
     * phrase, correctness
     */
    private void updatePhraseState(){
        phraseState.setPhrase(phrase);
        phraseState.setCorrectness(correctness);
        phraseState.setCursorPos(cursorPos);
    }

    public PhraseState getPhraseState(){ return phraseState; }

    @Override
    public void register(Observer o) {
        if(!observers.contains(o)) observers.add(o);
    }

    @Override
    public void unRegister(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers() {
        for (Observer o: observers) {
            o.update(phraseState);
        }
    }
}
