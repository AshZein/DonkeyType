package Controller;

import Model.PhraseCorrectness;
import Model.TypingStatistics;
import Views.TypingView;
import Views.View;
import Views.StatView;
import javafx.scene.Scene;
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
    Scene scene;
    TypingView typingView;
    StatView statView;
    PhraseCorrectness correctness;
    private boolean gameStarted;
    private long gameStartTime;
    public double timeLimit = 0;
    PromptGenerator promptGen;
    TypingStatistics typingStatistics;


    public Controller(Stage stage) throws IOException {
        typingView = new TypingView(this);
        statView = new StatView(this);

        promptGen = new PromptGenerator();
        String initialPhrase = promptGen.getNextPrompt();

        correctness = new PhraseCorrectness(initialPhrase);
        correctness.register(typingView);
        correctness.setPhrase(initialPhrase);  // Force call update

        typingStatistics = new TypingStatistics(initialPhrase);
        typingStatistics.register(statView);

        scene = new Scene(typingView.getRoot(), 950, 650);
        this.stage = stage;
        this.stage.setTitle("DonkeyType");
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void setTheme(Theme theme) {
        throw new UnsupportedOperationException();
    }

    public void setFont() {
        throw new UnsupportedOperationException();
    }

    public void startTest() {
        typingStatistics.resetStatistics();
        this.gameStarted = true;
        gameStartTime = System.nanoTime();
        typingStatistics.setTime(timeLimit);
    }


    public void endTest() {
        typingStatistics.setTime(timeLimit);
        switchView(Views.STATS);
        gameStarted = false;
        gameStartTime = 0;
        timeLimit = 0;
    }

    public void nextTest(){
        this.updatePrompt();
        typingView.swapTimeButtonColour("reset");
        switchView(Views.TYPING);
    }

    public void handleKeystroke(String input) {
        if(timeLimit != 0){
            if(gameStartTime == 0){
                this.startTest();
            }
            if (input.equals("backspace")) {
                correctness.removeCharacter();
                typingStatistics.removeCharacter();
            } else {
                typingStatistics.addCharacter(input.charAt(0), correctness.addCharacter(input.charAt(0)));
            }
        }
    }

    private void gameTick() {
        throw new UnsupportedOperationException();
    }

    private void switchView(Views view) {
        switch (view) {
            case STATS -> scene.setRoot(statView.getRoot());
            case TYPING -> scene.setRoot(typingView.getRoot());
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void updatePrompt(){
        String phrase = promptGen.getNextPrompt();
        correctness.setPhrase(phrase);
        typingStatistics.changePhrase(phrase);
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

