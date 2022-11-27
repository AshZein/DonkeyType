package Model;

import java.util.ArrayList;
import java.util.List;

public class TypingStatistics implements Observable<PromptStatistics> {
    private List<Observer<PromptStatistics>> observers;
    private PromptStatistics state;

    /**
     * Constructor for TypingStatistics
     */
    public TypingStatistics() {
        state = new PromptStatistics();
        observers = new ArrayList<>();
    }

    /**
     * Constructor for TypingStatistics
     * @param phrase
     */
    public TypingStatistics(String phrase) {
        observers = new ArrayList<>();
        state = new PromptStatistics(phrase);
    }

    /**
     * Adds a character that the user typed
     * @param c
     * @param correct
     */
    public void addCharacter(char c, boolean correct) {
        state.addCharacter(c, correct);
        notifyObservers();
    }

    /**
     * Remove the last typed character
     */
    public void removeCharacter() {
        state.removeCharacter();
        notifyObservers();
    }

    /**
     * Sets the phrase currently being typed
     * @param phrase
     */
    public void changePhrase(String phrase) {
        state.setPhrase(phrase);
        notifyObservers();
    }

    /**
     * Sets the time taken to type what is currently typed
     * @param time in seconds
     */
    public void setTime(double time) {
        state.setTime(time);
        notifyObservers();
    }

    /**
     * Resets the state of the statistics model
     */
    public void resetStatistics() {
        state.resetStatistics();
        notifyObservers();
    }

    /**
     * Getter for PromptStatistics state
     */
    public PromptStatistics getState() {
        return state;
    }

    /**
     * Register a new observer
     * @param o
     */
    @Override
    public void register(Observer<PromptStatistics> o) {
        if (!observers.contains(o)) observers.add(o);
    }

    /**
     * Unregister an observer
     * @param o
     */
    @Override
    public void unRegister(Observer<PromptStatistics> o) {
        observers.remove(o);
    }

    /**
     * Notifies all observers of an update in state
     */
    @Override
    public void notifyObservers() {
        for (Observer<PromptStatistics> o : observers) {
            o.update(state);
        }
    }
}
