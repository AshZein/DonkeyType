package Model;

public class PhraseState {
    private boolean[] correctness = {true, true, true, false, false, false, true, false, false, false, false};
    private int cursorPos = 6;


    public boolean[] getCorrectness() {
        return correctness;
    }

    public int getCursorPos() {
        return cursorPos;
    }
}
