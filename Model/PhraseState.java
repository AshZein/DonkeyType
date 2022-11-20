package Model;

public class PhraseState {
    // The current phrase being compared against
    private String phrase;

    // Correctness of typed characters so far
    private boolean[] correctness;

    // Cursor position is exclusive. Everything below it has been typed, character at it has not been typed
    private int cursorPos;


    //// Setters
    public void setCorrectness(boolean[] correctness) { this.correctness = correctness; }

    public void setCursorPos(int cursorPos) { this.cursorPos = cursorPos; }

    public void setPhrase(String phrase) { this.phrase = phrase; }

    //// Getters
    public boolean[] getCorrectness() { return correctness; }

    public int getCursorPos() { return cursorPos; }

    public String getPhrase() { return phrase; }
}
