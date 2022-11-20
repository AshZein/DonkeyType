package Controller;

import Views.TypingView;
import Views.View;

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
    View currentView;
    View otherView;
    TypingView typingView;

    public Controller() {
        throw new UnsupportedOperationException();
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

    public void handleKeystroke() {
        throw new UnsupportedOperationException();
    }

    private void gameTick() {
        throw new UnsupportedOperationException();
    }

    private void switchView(Views view) {
        throw new UnsupportedOperationException();
    }

}

