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

    public Controller(Stage stage) {
        typingView = new TypingView(this);
        String tempPhrase = "The quick brown fox jumps over the lazy dog";
        correctness = new PhraseCorrectness(tempPhrase);
        correctness.register(typingView);
        correctness.setPhrase(tempPhrase);  // Force call update
        currentView = typingView;   // Default view
        stage.setScene(currentView.getScene());
    }

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
        throw new UnsupportedOperationException();
    }

    private void gameTick() {
        throw new UnsupportedOperationException();
    }

    private void switchView(Views view) {
        throw new UnsupportedOperationException();
    }

}

