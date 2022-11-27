package Views;

import Controller.Controller;
import Model.Observer;
import Model.PhraseState;
import Model.PromptStatistics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class StatView extends View implements Observer<PromptStatistics> {

    PromptStatistics promptStatistics;
    Button aButton;
    PhraseState state;
    String[] buttonColor0 = {"#121212", "#ffffff"};
    private int defaultFontSize = 36;
    private String defaultFontStyle = "Arial";
    String UIColor = "#a9a9a9";

    Font font;

    public StatView(Controller control) {
        super(control);
        initUI();
    }

    @Override
    public void update(PromptStatistics state) {
        promptStatistics = state;
        initUI();
    }

    private void initUI() {
        if (promptStatistics == null) return;
        // Note that the positioning of the elements will change as we progress
        // default font size and style
        font = new Font(defaultFontStyle, defaultFontSize);

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + UIColor + ";");

        // Button Not sure what the purpose is yet.
        aButton = new Button("Button");
        aButton.setId("Button");
        aButton.setPrefSize(100, 40);
        aButton.setFont(new Font(12));
        aButton.setStyle("-fx-background-color:" + buttonColor0[0] + "; -fx-text-fill: " + buttonColor0[1] + ";");

        HBox controls = new HBox(40, aButton);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.CENTER);
        root.setCenter(canvas);
        root.setBottom(controls);
        canvas = new Canvas(800, 400);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        // title
        Label title = new Label("Statistics of typing");
        title.setFont(Font.font(defaultFontStyle));
        title.setId("title");
        root.setTop(title);

        // Label to show word per minute. The number will be  replaced by data from PromptStatistics
        Label labelforwpm = new Label("Word per minute: " + promptStatistics.calculateWPM());
        labelforwpm.setFont(Font.font(defaultFontStyle));
        labelforwpm.setId("word per minute");
        root.setCenter(labelforwpm);

        // Label to show Accuracy. The number will be  replaced by data from PromptStatistics
        Label labelforaccuracy = new Label("Accuracy: " + promptStatistics.calculateAccuracy() + "%");
        labelforaccuracy.setFont(Font.font(defaultFontStyle));
        labelforaccuracy.setId("word per minute");
        root.setLeft(labelforaccuracy);

        // listview to show the list of mistyped words
        ListView<String> mistypedwords = new ListView<String>();
        mistypedwords.editableProperty().set(false);
        mistypedwords.setMouseTransparent(true);
        // this will be populated by missed words from PromptStatistics
        ObservableList<String> items = FXCollections.observableArrayList("List of Mistyped Words:");
        items.addAll(promptStatistics.getMistypedWords());
        mistypedwords.setItems(items);
        mistypedwords.setPrefSize(300, 100);
        root.setRight(mistypedwords);
    }
}

