package Views;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TypingView{
    Stage stage;
    BorderPane borderPane;
    Button startButton, nextButton;

    Timeline timeline;

    Canvas canvas;
    GraphicsContext gc;

    Font font;

    //The font size and style for the Drawn Text Prompts
    private int defaultFontSize = 36;
    private String defaultFontStyle = "Arial";
    public TypingView(Stage stage){
        this.stage = stage;
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

        // Positioning of various UI elements
        borderPane.setCenter(canvas);
        borderPane.setBottom(controls);

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.001), e->drawScreen()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(borderPane, 950, 650);
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void drawScreen() {
        //replace with phrase from the model
        String temp = "The quick brown fox jumped over the lazy doggy The quick brown fox jumped over the lazy doggy " +
                "The quick brown fox jumped over the lazy doggy The quick brown fox jumped " +
                "over the lazy doggy The quick brown fox jumped over the lazy doggy";

        //Setting the text colour, alignment, and font
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        gc.setFont(font);
        gc.setTextAlign(TextAlignment.LEFT);


        // The coordinates for where to draw the text, these are the default values.
        int currX = 20;
        int currY = 30;

        char currChar;
        for(int ind = 0; ind < temp.length(); ind++){
             currChar = temp.charAt(ind);
            if(!Character.toString(currChar).equals(" ")) {
                Text text = new Text(Character.toString(currChar));
                text.setFont(font);
                int dx = (int) Math.ceil(text.getLayoutBounds().getWidth()); //the width of the text to be printed

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
    }


    private void updateScreen() {

    }


}
