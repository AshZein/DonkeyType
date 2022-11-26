package Views;

import Controller.Controller;
import Model.Observer;
import Model.PhraseState;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class TypingView extends View implements Observer<PhraseState> {
    BorderPane borderPane;
    Button startButton, nextButton;
    String[] buttonColor0 = {"#121212", "#ffffff"}; // buttonColor set 0, {Button fill colour, button text colour"}

    Timeline timeline;
    Font font;
    PhraseState state;
    Color[] textPallette = {Color.WHITE, Color.GRAY, Color.RED}; // {correct, to be typed, Incorrect}

    // The UI background colour
    String UIColor = "#a9a9a9";


    //The font size and style for the Drawn Text Prompts
    private int defaultFontSize = 36;
    private String defaultFontStyle = "Arial";

    private int defCurrX = 0;
    private int defCurrY = 30;

    // Visual Cursor stuff
    private int cursorX = 0;
    private int cursorY = 30;
    Color cursorCol = Color.WHITE;

    public TypingView(Controller control){
        super(control);
        initUI();
    }

    public void update(PhraseState state) {
        this.state = state;
    }

    private void initUI() {
        font = new Font(defaultFontStyle, defaultFontSize);

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + UIColor + ";");

        //Buttons
        startButton = new Button("Start Test");
        startButton.setId("Start Test");
        startButton.setPrefSize(100, 40);
        startButton.setFont(new Font(12));
        startButton.setStyle("-fx-background-color:" + buttonColor0[0]+ "; -fx-text-fill: " +buttonColor0[1]+ ";");

        nextButton = new Button("Next Test");
        nextButton.setId("Next Test");
        nextButton.setPrefSize(100, 40);
        nextButton.setFont(new Font (12));
        nextButton.setStyle("-fx-background-color:" + buttonColor0[0]+ "; -fx-text-fill: " +buttonColor0[1]+ ";");

        // button spacing and positioning
        HBox controls = new HBox(40, startButton, nextButton);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.CENTER);

        //The canvas
        canvas = new Canvas(700, 150);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        borderPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String code = keyEvent.getText();
                if(keyEvent.getCode().toString().equals("BACK_SPACE")){
                    control.handleKeystroke("backspace");
                }
                else if(keyEvent.isShiftDown()){
                    control.handleKeystroke(keyEvent.getText().toUpperCase());
                }
                else if(code.length() == 1){
                    control.handleKeystroke(keyEvent.getText());
                }
                borderPane.requestFocus();

            }
        });

        // Positioning of various UI elements
        borderPane.setCenter(canvas);
        borderPane.setBottom(controls);

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.001), e->updateScreen()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        scene = new Scene(borderPane, 950, 650);
    }

    /*
     * Draws the prompt text on the canvas.
     */
    private void drawScreen() {
        if (this.state.getCursorPos() == this.state.getPhrase().length()){
            control.updatePrompt();
        }

        //Setting the text alignment, and font
        gc.setFont(font);
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
            text.setFont(font);
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
                check.setFont(font);
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
                this.drawCursor();
            }

        }
    }

    /*
     * Draws a cursor in the UI at the point where the user has typed up to.
     */
    private void drawCursor(){
        gc.setFill(cursorCol);
        gc.fillRect(this.cursorX, this.cursorY - this.font.getSize(), 2, this.font.getSize());
    }

    /*
     * Update the screen to show any changes caused by inputs.
     */
    private void updateScreen() {
        if(!(this.state == null)){
            // Refreshing the canvas, to simplify drawing adn un-drawing elements.
            canvas = new Canvas(700, 150);
            canvas.setId("Canvas");
            gc = canvas.getGraphicsContext2D();
            borderPane.setCenter(canvas);

            this.drawScreen();
            this.drawCursor();
        }
    }
}
