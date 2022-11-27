package Controller;

import Model.PhraseCorrectness;
import Model.TypingStatistics;
import Views.TypingView;
import Views.View;
import Views.StatView;
import javafx.stage.Stage;
import PromptGenerator.PromptGenerator;

import java.io.IOException;

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
    boolean gameStarted;
    long gameStartTime;
    public double timeLimit = 0;
    PromptGenerator promptGen;
    TypingStatistics typingStatistics;


    public Controller(Stage stage) throws IOException {
        typingView = new TypingView(this);
        typingStatistics = new TypingStatistics();

        promptGen = new PromptGenerator();
        String tempPhrase = promptGen.getNextPrompt();

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
        this.gameStarted = true;
        typingStatistics.setTime(timeLimit);
    }


    public void endTest() {
        typingStatistics.setTime(timeLimit);
        switchView(Views.STATS);
        gameStarted = false;
        gameStartTime = 0;
        timeLimit = 0;
    }

    public void handleKeystroke(String input) {
        if (!gameStarted) startTest();
        if (timeLimit == 0) endTest();

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

    public void updatePrompt(){
        String phrase = promptGen.getNextPrompt();
        correctness.setPhrase(phrase);
        typingStatistics.changePhrase(phrase)
    }
    // setter for setting the timelimit the user desires
    public void setTimeLimit(double time){
        timeLimit = time;
    }

    // Getter for the time remaining in the countdown
    public double getTimeLeft(){
        if(gameStartTime == 0){ // can't start counting down if no key strokes have been entered
            return timeLimit;
        }
        double timeLeft = timeLimit - (double) ((System.nanoTime() - gameStartTime)/1000000000);
        if(timeLeft == 0){
            this.endTest();
        }
        return timeLeft;
    }
}

