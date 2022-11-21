package Controller;

import Model.PhraseCorrectness;
import Views.TypingView;
import Views.View;
import javafx.stage.Stage;

enum Theme {
    NORMAL,
    HIGH_CONTRAST,
    DARK,
}

enum Views {
    TYPING,
    STATS,
}

public class Controller {
    Stage stage;
    View currentView;
    View otherView;
    TypingView typingView;
    PhraseCorrectness correctness;
    boolean gameStarted;
    double gameStartTime;

    public Controller(Stage stage) {
        typingView = new TypingView(this);
        String tempPhrase = "The quick brown fox jumps over the lazy dog";

        correctness = new PhraseCorrectness(tempPhrase);
        correctness.register(typingView);
        correctness.setPhrase(tempPhrase);  // Force call update
        currentView = typingView;   // Default view
        gameStarted = false;

        this.stage = stage;
        this.stage.setTitle("DonkeyType");
        this.stage.setScene(currentView.getScene());
        this.stage.show();
    }
    // i will implement this test to push
    public void setTheme(Theme theme) {
        throw new UnsupportedOperationException();
    }

    public void setFont() {
        throw new UnsupportedOperationException();
    }

    public void startTest() {
        throw new UnsupportedOperationException();
    }

    public void endTest() {
        throw new UnsupportedOperationException();
    }

    public void handleKeystroke(String input) {
        if (!gameStarted) {
            gameStarted = true;
            gameStartTime = System.nanoTime();
        }
        
        if (input.equals("backspace")) correctness.removeCharacter();
        else correctness.addCharacter(input.charAt(0));
    }

    private void gameTick() {
        throw new UnsupportedOperationException();
    }

    private void switchView(Views view) {
        throw new UnsupportedOperationException();
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

}

