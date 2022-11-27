package Controller;

import Model.PhraseCorrectness;
import Model.TypingStatistics;
import Views.TypingView;
import Views.View;
import Views.StatView;
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
    StatView statView;
    PhraseCorrectness correctness;
    TypingStatistics typingStatistics;
    private boolean gameStarted;
    private String currentPhrase;
    double timeLimit = 0;

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

    public void setTheme(Theme theme) {
        throw new UnsupportedOperationException();
    }

    public void setFont() {
        throw new UnsupportedOperationException();
    }

    public void startTest() {
        if (!gameStarted) gameStarted = true;
        typingStatistics.resetStatistics();
        typingStatistics.changePhrase(currentPhrase);
        correctness.setPhrase(currentPhrase);
    }

    public void endTest() {
        typingStatistics.setTime(timeLimit);
        switchView(Views.STATS);
    }

    public void handleKeystroke(String input) {
        if (!gameStarted) startTest();

        if (input.equals("backspace")) {
            correctness.removeCharacter();
            typingStatistics.removeCharacter();
        }
        else {
            typingStatistics.addCharacter(input.charAt(0), correctness.addCharacter(input.charAt(0)));
        }
    }

    private void gameTick() {
        throw new UnsupportedOperationException();
    }

    private void switchView(Views view) {
        switch (view) {
            case STATS -> stage.setScene(statView.getScene());
            case TYPING -> stage.setScene(typingView.getScene());
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

}

