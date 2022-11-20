package Views;

import Controller.Controller;
import Model.PhraseState;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;

public class TypingView extends View{
    Stage stage;
    BorderPane borderPane;
    Button startButton, nextButton;
    Timeline timeline;
    Font font;
    PhraseState state;
    Color[] textPallette = {Color.WHITE, Color.GRAY, Color.RED}; // {correct, to be typed, Incorrect}

    HashMap<String,String> numSymbols = new HashMap<>();

    //The font size and style for the Drawn Text Prompts
    private int defaultFontSize = 36;
    private String defaultFontStyle = "Arial";
    public TypingView(Controller control){
        super(control);
        initUI();
    }

    private void initUI() {
        font = new Font(defaultFontStyle, defaultFontSize);

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #778899;");

        //Buttons
        startButton = new Button("Start Test");
        startButton.setId("Start Test");
        startButton.setPrefSize(100, 40);
        startButton.setFont(new Font(12));
        startButton.setStyle("-fx-background-color: #121212; -fx-text-fill: #ffffff;");

        nextButton = new Button("Next Test");
        nextButton.setId("Next Test");
        nextButton.setPrefSize(100, 40);
        nextButton.setFont(new Font (12));
        nextButton.setStyle("-fx-background-color: #121212; -fx-text-fill: #ffffff;");

        // button spacing and positioning
        HBox controls = new HBox(40, startButton, nextButton);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.CENTER);

        //The canvas
        canvas = new Canvas(800, 400);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        borderPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String code = keyEvent.getText();
                if(code.length() == 1){
                    control.handleKeystroke();
                }

            }
        });

        // Positioning of various UI elements
        borderPane.setCenter(canvas);
        borderPane.setBottom(controls);

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.001), e->drawScreen()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        scene = new Scene(borderPane, 950, 650);
    }

    private void drawScreen() {
        //replace with phrase from the model
        String temp = "the quick";

        //Setting the text colour, alignment, and font
        gc.setFill(Color.WHITE);
        gc.setFont(font);
        gc.setTextAlign(TextAlignment.LEFT);


        // The coordinates for where to draw the text, these are the default values.
        int currX = 20;
        int currY = 30;

        char currChar;

        boolean[] phraseBool = this.state.getCorrectness();
        int cursor = this.state.getCursorPos();
        for(int ind = 0; ind < temp.length(); ind++){
             currChar = temp.charAt(ind);
            if(!Character.toString(currChar).equals(" ")) {
                Text text = new Text(Character.toString(currChar));
                text.setFont(font);
                int dx = (int) Math.ceil(text.getLayoutBounds().getWidth()); //the width of the text to be printed

                if(ind < cursor){
                    if (phraseBool[ind]){
                        gc.setFill(textPallette[0]);
                    }
                    else{
                        gc.setFill(textPallette[2]);
                    }
                }
                else {
                    gc.setFill(textPallette[1]);
                }
                //Drawing the current character
                gc.fillText(Character.toString(currChar), currX, currY);

                //To handle text wrapping.
                if (currX + dx + 14 + 10 < canvas.getWidth()){
                    currX = currX + dx;
                }
                else{
                    currX = 20;
                    currY = currY + 45;
                }
            }
            else {
                currX = currX + 12;
            }
        }
       gc.clip();
    }


    private void updateScreen() {throw new UnsupportedOperationException();}
}
