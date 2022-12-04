package Controller;

import Controller.Strategy.NormalStrategy;
import Controller.Strategy.QuoteStrategy;
import Controller.Strategy.Strategy;
import Model.PhraseCorrectness;
import Model.TypingStatistics;
import Views.TypingView;
import Views.StatView;
import Views.SpecView;
import Views.AccessibilityView;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import PromptGenerator.PromptGenerator;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

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
    public Stage stage;
    Scene scene;
    TypingView typingView;
    StatView statView;
    SpecView specView;
    AccessibilityView accessView;
    PhraseCorrectness correctness;
    MediaPlayer correctSoundPlayer;
    MediaPlayer incorrectSoundPlayer;
    private boolean gameStarted;
    private boolean playAudio;
    private long gameStartTime;
    public double timeLimit = 0;
    PromptGenerator promptGen;
    TypingStatistics typingStatistics;

    // Strategy attributes
    Context cont;
    private HashMap<String, Strategy> strategies;
    private int[] stratData = {0,0};


    public Controller(Stage stage) throws IOException {
        typingView = new TypingView(this);
        statView = new StatView(this);

        specView = new SpecView(this, typingView);
        accessView = new AccessibilityView(this, typingView);

        correctSoundPlayer = new MediaPlayer(new Media(new File("./Assets/correct.mp3").toURI().toString()));
        incorrectSoundPlayer = new MediaPlayer(new Media(new File("./Assets/error.mp3").toURI().toString()));
        playAudio = true;


        promptGen = new PromptGenerator();

        cont = new Context();
        strategies = new HashMap<>();
        strategies.put("normal", new NormalStrategy());
        strategies.put("quote", new QuoteStrategy());
        cont.setStrategy(strategies.get("normal"));

        String initialPhrase = cont.executeStrategy(stratData);

        correctness = new PhraseCorrectness(initialPhrase);
        correctness.register(typingView);
        correctness.setPhrase(initialPhrase);  // Force call update

        typingStatistics = new TypingStatistics(initialPhrase);
        typingStatistics.register(statView);

        scene = new Scene(typingView.getRoot(), 1100, 650);
        this.stage = stage;
        this.stage.setTitle("DonkeyType");
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void setTheme(Theme theme) {
        throw new UnsupportedOperationException();
    }

    public void setFont(int f) {
        typingView.changeFont(f);
        statView.changeFont(f);
        accessView.changeFont(f);
    }

    public void toggleAudio() {
        this.playAudio = !playAudio;
    }

    public boolean getAudio() {
        return playAudio;
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
                boolean correct = correctness.addCharacter(input.charAt(0));
                typingStatistics.addCharacter(input.charAt(0), correct);
                correctSoundPlayer.stop();
                incorrectSoundPlayer.stop();
                if (!correct && playAudio) {
                    incorrectSoundPlayer.play();
                }
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
        String phrase = cont.executeStrategy(stratData);

        ArrayList<String> mistyped = typingStatistics.getState().getMistypedWords();
        if ((mistyped.size() == 0 || !mistyped.get(mistyped.size() - 1).equals(correctness.getPhraseState().getPhrase())) && playAudio) {
            // typed correctly
            correctSoundPlayer.stop();
            correctSoundPlayer.play();
        }
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

    /*
     * Update the prompt type to either normal random words or full quotes.
     */
    public void updateStrategy(String strategy){
        if (strategy == "normal"){
            this.cont.setStrategy(strategies.get(strategy));
        }
        else if(strategy == "quote"){
            this.cont.setStrategy(strategies.get(strategy));
        }
        updatePrompt();
    }
    /*
     * Update the data used by strategies to fetch prompts with correct modifications
     */
    public void updateStrategyData(int[] data){
        this.stratData = data;
        updatePrompt();
    }

    public void showSpecView(){
        specView.show();
    }

    public void showAccessView() { accessView.show(); }
}

