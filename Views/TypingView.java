package Views;

import Controller.Controller;
import Model.Observer;
import Model.PhraseState;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Objects;

public class TypingView extends View implements Observer<PhraseState> {
    HashMap<String, Button> timeLimButton;
    String[] buttonColorMain = {"#121212", "#ffffff"}; // buttonColor set for main buttons, {Button fill colour, button text colour}
    String[] buttonColorTime = {"#121212", "#ffffff", "#00ff00", "#000000"}; //buttonColor set for time set buttons, {Button fill colour, button text colour, selected fill colour, selected text}

    Timeline timeline;
    Font promptFont;
    Font buttonFont;
    Font timerFont;
    PhraseState state;
    Color[] textPallette = {Color.WHITE, Color.GRAY, Color.RED}; // {correct, to be typed, Incorrect}

    // The UI background colour
    String UIColor = "#a9a9a9";


    //The font size and style for the Drawn Text Prompts
    private int defaultFontSize = 36;
    private String defaultFontStyle = "Arial";

    private int defCurrX = 0;
    private int defCurrY = 90;

    // Visual Cursor stuff
    private int cursorX = 0;
    private int cursorY;
    Color cursorCol = Color.GRAY;

    public TypingView(Controller control){
        super(control);
        initUI();
    }

    public void update(PhraseState state) {
        this.state = state;
    }

    private void initUI() {
        promptFont = new Font(defaultFontStyle, defaultFontSize);
        buttonFont = new Font(12);
        timerFont = new Font(50);

        root.setStyle("-fx-background-color: " + UIColor + ";");

        //Time setting buttons
        timeLimButton = new HashMap<>();

        Button fullMinButton = new Button("60s");
        fullMinButton.setId("60s");
        fullMinButton.setPrefSize(100,40);
        fullMinButton.setFont(buttonFont);
        fullMinButton.setStyle("-fx-background-color:" + buttonColorTime[0]+ "; -fx-text-fill: " + buttonColorTime[1]+ ";");
        timeLimButton.put(fullMinButton.getId(), fullMinButton);

        Button halfMinButton = new Button("30s");
        halfMinButton.setId("30s");
        halfMinButton.setPrefSize(100,40);
        halfMinButton.setFont(buttonFont);
        halfMinButton.setStyle("-fx-background-color:" + buttonColorTime[0]+ "; -fx-text-fill: " + buttonColorTime[1]+ ";");
        timeLimButton.put(halfMinButton.getId(),halfMinButton);

        Button fifteenSecButton = new Button("15s");
        fifteenSecButton .setId("15s");
        fifteenSecButton .setPrefSize(100,40);
        fifteenSecButton .setFont(buttonFont);
        fifteenSecButton .setStyle("-fx-background-color:" + buttonColorTime[0]+ "; -fx-text-fill: " + buttonColorTime[1]+ ";");
        timeLimButton.put(fifteenSecButton.getId(), fifteenSecButton);

        Button fiveSecButton = new Button("5s");
        fiveSecButton.setId("5s");
        fiveSecButton.setPrefSize(100,40);
        fiveSecButton.setFont(buttonFont);
        fiveSecButton.setStyle("-fx-background-color:" + buttonColorTime[0]+ "; -fx-text-fill: " + buttonColorTime[1]+ ";");
        timeLimButton.put(fiveSecButton.getId(), fiveSecButton);

        //Prompt specification Button
        Button specButton = new Button("Test Options");
        specButton.setId("Test Options");
        specButton.setPrefSize(100,40);
        specButton.setFont(buttonFont);
        specButton.setStyle("-fx-background-color:" + buttonColorTime[0]+ "; -fx-text-fill: " + buttonColorTime[1]+ ";");

        // button spacing and positioning
        HBox timeControls = new HBox(40, fiveSecButton, fifteenSecButton, halfMinButton, fullMinButton);
        timeControls.setPadding(new Insets(20, 20, 20, 20));
        timeControls.setAlignment(Pos.CENTER);

        HBox specControls = new HBox(40, specButton);
        specControls.setPadding(new Insets(20, 20, 20, 20));
        specControls.setAlignment(Pos.CENTER);

        //The canvas
        canvas = new Canvas(700, 200);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();


        // Prompt Specification button handling
        specButton.setOnAction(e -> { SpecView specView = new SpecView(control, this); });

        // Handling the time limit setting buttons
        halfMinButton.setOnAction(e -> {
            if(!control.isGameStarted()) { // can't change time if game is started
            //Changing the colour for the selected button and deselected button
                swapTimeButtonColour(halfMinButton.getId());

                control.setTimeLimit(30.0);
            }
        });

        fullMinButton.setOnAction(e -> {
            if(!control.isGameStarted()) {
                //Changing the colour for the selected button and deselected button
                swapTimeButtonColour(fullMinButton.getId());

                control.setTimeLimit(60.0);
            }
        });
        fiveSecButton.setOnAction(e -> {
            if(!control.isGameStarted()) {
                //Changing the colour for the selected button and deselected button
                swapTimeButtonColour(fiveSecButton.getId());

                control.setTimeLimit(5.0);
            }
        });

        fifteenSecButton.setOnAction(e -> {
            if(!control.isGameStarted()) {
                //Changing the colour for the selected button and deselected button
                swapTimeButtonColour(fifteenSecButton.getId());

                control.setTimeLimit(15.0);
            }
        });

        //The event handler for keyboard events
        root.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String code = keyEvent.getText();
                if (keyEvent.getCode().toString().equals("BACK_SPACE")) { //backspace key pressed
                    control.handleKeystroke("backspace");

                } else if (keyEvent.isShiftDown()) { //Shift is pressed
                    control.handleKeystroke(keyEvent.getText().toUpperCase());

                } else if (code.length() == 1) { // every other single character key
                    control.handleKeystroke(keyEvent.getText());
                }

                root.requestFocus();
            }
        });


        // Positioning of various UI elements
        root.setCenter(canvas);
        root.setTop(timeControls);
        root.setBottom(specControls);

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.001), e->updateScreen()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /*
     * Draws the prompt text on the canvas.
     */
    private void drawScreen() {
        // if the user is at the end of the prompt need to fetch the next
        if (this.state.getCursorPos() == this.state.getPhrase().length()){
            control.updatePrompt();
        }

        //Setting the text alignment, and font
        gc.setFont(promptFont);
        gc.setTextAlign(TextAlignment.LEFT);

        // The coordinates for where to draw the text, these are the default values.
        int currX = defCurrX;
        int currY = defCurrY;
        cursorY = currY;
        char currChar;

        String phrase = this.state.getPhrase();
        boolean[] phraseBool = this.state.getCorrectness();
        int cursor = this.state.getCursorPos();

        int currLineWidth; // the width of the current line of text
        int currInd; // the current index of the word that will be pre-checked
        for(int ind = 0; ind < phrase.length(); ind++){
            currChar = phrase.charAt(ind);
            currInd = ind;

            Text text = new Text(Character.toString(currChar));
            text.setFont(promptFont);
            int dx = (int) Math.ceil(text.getLayoutBounds().getWidth()); //the width of the text to be printed

            // pre-checking whether the next drawn WORD (not character) will go out of the bound for the canvas.
            Text check;
            char checkChar;
            boolean pass = false;
            // Only need to check if a word that comes after a space will go out of bounds.
            if(currChar == ' '){
                currInd++;
                checkChar = phrase.charAt(currInd);
                check = new Text(Character.toString(checkChar));
                check.setFont(promptFont);
                currLineWidth = currX + (int) Math.ceil(check.getLayoutBounds().getWidth());

                while (checkChar != ' '){ // a word ends when the last character is followed by a space.
                    // the line width must be less than the canvas width
                    if(currLineWidth < canvas.getWidth()){
                        currInd++;
                        if(currInd == phrase.length()){
                            break;
                        }
                        checkChar = phrase.charAt(currInd);
                        check = new Text(Character.toString(checkChar));
                        currLineWidth = currLineWidth + (int) Math.ceil(check.getLayoutBounds().getWidth());
                    }
                    // the word will fall out of the canvas, so need to change x and y coordinates to the next line
                    else{
                        currX = defCurrX;
                        currY = currY + 45;
                        this.cursorY = currY;
                        pass = true;
                        break;
                    }
                }
            }

            //Setting the character colour.
            if(ind < cursor){
                if (phraseBool[ind]){ // Text typed correctly
                    gc.setFill(textPallette[0]);
                }
                else{ //Text typed incorrectly
                    gc.setFill(textPallette[2]);
                }
            }
            else { // Text which still needs to be typed
                gc.setFill(textPallette[1]);
            }

            if(currY <= canvas.getHeight() && !pass) {
                //Drawing the current character
                gc.fillText(Character.toString(currChar), currX, currY);
            }
            currX = currX + dx;
            // Sets where to draw a visual cursor
            if (ind == cursor){
                this.cursorX = currX-dx;
                this.cursorY = currY;
                this.drawCursor();
            }
        }
    }

    /*
     * adjusts the colour for the time limit buttons based on which button has been pressed. The selected button should
     * be a unique colour, while the other buttons should be a colour not equal to the selected button.
     */
    public void swapTimeButtonColour(String id){
        if (Objects.equals(id, "reset")){
            for(String i: timeLimButton.keySet()){
                timeLimButton.get(i).setStyle("-fx-background-color:" + buttonColorTime[0] + "; -fx-text-fill: " + buttonColorTime[1] + ";");
            }
        }

        for(String i: timeLimButton.keySet()){
            Button b = timeLimButton.get(i);
            if (i != id){ //id is for unselected button
                b.setStyle("-fx-background-color:" + buttonColorTime[0] + "; -fx-text-fill: " + buttonColorTime[1] + ";");
            }
            else{ //selected button
                b.setStyle("-fx-background-color:" + buttonColorTime[2] + "; -fx-text-fill: " + buttonColorTime[3] + ";");
            }
        }
    }

    /*
     * Draws a cursor in the UI at the point where the user has typed up to.
     */
    private void drawCursor(){
        gc.setFill(cursorCol);
        gc.fillRect(this.cursorX, this.cursorY - this.promptFont.getSize()+7, 2, this.promptFont.getSize()-2);
    }

    /*
     * Draws the current time of the countdown on the screen.
     */
    private void drawTimer(){
        // Timer Drawing
        gc.setFont(timerFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(textPallette[0]);
        Integer time = (int)control.getTimeLeft();
        if(control.isGameStarted()) { // prevent timer from being shown before user selects a limit
            if (time > 0) {
                gc.fillText(time.toString(), canvas.getWidth() / 2, 40);
            } else { // timer goes into negatives
                gc.fillText("0", canvas.getWidth() / 2, 40);
            }
        }

    }

    private void drawWarningGuide(){
        gc.setFont(timerFont);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(textPallette[0]);
        if(control.timeLimit == 0){
            gc.fillText("Please select a time limit above", canvas.getWidth()/2, 40);
        }
        else if(!control.isGameStarted()){
            gc.fillText("Start typing to begin", canvas.getWidth()/2,40);
        }
    }


    /*
     * Update the screen to show any changes caused by inputs.
     */
    private void updateScreen() {
        if(!(this.state == null)){
            // Refreshing the canvas, to simplify drawing adn un-drawing elements.
            canvas = new Canvas(700, 200);
            canvas.setId("Canvas");
            gc = canvas.getGraphicsContext2D();
            root.setCenter(canvas);

            this.drawWarningGuide();
            this.drawTimer();
            if(control.timeLimit != 0) {
                this.drawScreen();
            }
        }
    }
}
