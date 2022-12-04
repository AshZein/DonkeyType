package Views;
import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;


public class AccessibilityView {
    final Stage dialog;

    HBox fontBox;
    HBox chimeBox;
    HashMap<String, Button> fontControlButton;
    Button toggleAudioButton;
    String [] buttonColorTime;
    Text demoText;
    Font headingFont = new Font(18);
    Controller control;
    TypingView typingView;
    public AccessibilityView(Controller control, TypingView typingView){
        this.control = control;
        this.typingView = typingView;

        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.control.stage);
        buttonColorTime = this.typingView.buttonColorTime;
        fontControlButton = new HashMap<>();

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(30, 10, 10, 10));
        dialogVBox.setStyle("-fx-background-color: "+ this.typingView.UIColor + ";");

        // Audio chime toggle button
        toggleAudioButton = new Button("Audio On");
        toggleAudioButton.setId("toggleAudio");
        toggleAudioButton.setPrefSize(100,50);
        toggleAudioButton.setFont(this.typingView.buttonFont);
        toggleAudioButton.setStyle("-fx-background-color:" + buttonColorTime[0]+ "; -fx-text-fill: " + buttonColorTime[1]+ ";");

        // demo text for prompt font size when adjusting the font
        demoText = new Text("Hello World");
        demoText.setFont(this.typingView.promptFont);

        // Font adjustment buttons
        Button increaseFontButton = new Button("+");
        increaseFontButton.setId("Increase");
        increaseFontButton.setPrefSize(100,50);
        increaseFontButton.setFont(this.typingView.buttonFont);
        increaseFontButton.setStyle("-fx-background-color:" + buttonColorTime[0]+ "; -fx-text-fill: " + buttonColorTime[1]+ ";");
        fontControlButton.put(increaseFontButton.getId(), increaseFontButton);

        Button decreaseFontButton = new Button("-");
        decreaseFontButton.setId("Decrease");
        decreaseFontButton.setPrefSize(100,50);
        decreaseFontButton.setFont(this.typingView.buttonFont);
        decreaseFontButton.setStyle("-fx-background-color:" + buttonColorTime[0]+ "; -fx-text-fill: " + buttonColorTime[1]+ ";");
        fontControlButton.put(decreaseFontButton.getId(), decreaseFontButton);


        // audio chime toggle handler
        toggleAudioButton.setOnAction(e -> {
            this.control.toggleAudio();
            if (this.control.getAudio()) {
                toggleAudioButton.setText("Audio On");
            } else {
                toggleAudioButton.setText("Audio Off");
            }
        });

        // Handling font setting
        increaseFontButton.setOnAction(e -> {
            this.control.setFont(1);
            demoText.setFont(this.typingView.promptFont);
        });

        decreaseFontButton.setOnAction(e -> {
            this.control.setFont(-1);
            demoText.setFont(this.typingView.promptFont);
        });


        fontBox = new HBox(20, decreaseFontButton, demoText, increaseFontButton);
        fontBox.setAlignment(Pos.CENTER);

        toggleAudioButton.setAlignment(Pos.CENTER);

        dialogVBox.setAlignment(Pos.CENTER);

        Text fontHeading = new Text("Font adjustment");
        fontHeading.setFont(headingFont);
        dialogVBox.getChildren().add(fontHeading);
        dialogVBox.getChildren().add(fontBox);

        Text audioChimeHeading = new Text("Audio Chime");
        audioChimeHeading.setFont(headingFont);
        dialogVBox.getChildren().add(audioChimeHeading);
        dialogVBox.getChildren().add(toggleAudioButton);

        Scene dialogScene = new Scene(dialogVBox, 500, 500);
        dialog.setScene(dialogScene);
    }

    public void changeFont(int n){
        if( (n == 1 && typingView.defaultFontSize < 47) || (n == -1 && typingView.defaultFontSize > 30)) {
            this.typingView.buttonFont = new Font(this.typingView.buttonFont.getSize() + n);
            for (String i : fontControlButton.keySet()) {
                Button b = fontControlButton.get(i);
                b.setPrefSize(b.getWidth() + 5 * n, b.getHeight() + n);
                b.setFont(this.typingView.buttonFont);
            }
            toggleAudioButton.setPrefSize(toggleAudioButton.getWidth() + 5 * n, toggleAudioButton.getHeight() + n);
            toggleAudioButton.setFont(this.typingView.buttonFont);
        }
    }

    public void show() {
        for (String button: fontControlButton.keySet()){
            fontControlButton.get(button).setFont(this.typingView.buttonFont);
            fontControlButton.get(button).setStyle("-fx-background-color:" + buttonColorTime[0]+ "; -fx-text-fill: " + buttonColorTime[1]+ ";");
        }
        dialog.show(); }
}
