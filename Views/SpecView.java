package Views;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpecView {
    CheckBox punctCheck;
    CheckBox numCheck;

    CheckBox normalCheck;
    CheckBox quoteCheck;

    public SpecView(Controller control, TypingView typingView){

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(control.stage);
        VBox dialogVBox = new VBox(30);
        dialogVBox.setPadding(new Insets(30, 20, 20, 20));
        dialogVBox.setStyle(typingView.UIColor);

        // prompt type check boxes
        normalCheck = new CheckBox("Normal");
        normalCheck.setFont(typingView.buttonFont);
        normalCheck.setStyle(typingView.buttonColorTime[0]);

        quoteCheck = new CheckBox("Quote");
        quoteCheck.setFont(typingView.buttonFont);
        quoteCheck.setStyle(typingView.buttonColorTime[0]);

        // Prompt modifier check boxes
        punctCheck = new CheckBox("Punctuation");
        punctCheck.setFont(typingView.buttonFont);
        punctCheck.setStyle(typingView.buttonColorTime[0]);
        punctCheck.setSelected(false);

        numCheck = new CheckBox("Numbers");
        numCheck.setFont(typingView.buttonFont);
        numCheck.setStyle(typingView.buttonColorTime[0]);
        numCheck.setSelected(false);

        //prompt type checkbox handlers
        quoteCheck.setOnAction(e -> {
            control.updateStrategy("quote");
            normalCheck.setSelected(false);
        });

        normalCheck.setOnAction(e -> {
            control.updateStrategy("normal");
            quoteCheck.setSelected(false);
        });

        // prompt modifier check box handlers
        punctCheck.setOnAction(e -> {
            control.updateStrategyData(getData());
        });

        numCheck.setOnAction(e -> {
            control.updateStrategyData(getData());
        });

        HBox specBox = new HBox(20, punctCheck, numCheck);
        specBox.setAlignment(Pos.CENTER);
        HBox typeBox = new HBox(40, normalCheck, quoteCheck);
        typeBox.setAlignment(Pos.CENTER);
        dialogVBox.setAlignment(Pos.TOP_CENTER);
        dialogVBox.getChildren().add(typeBox);
        dialogVBox.getChildren().add(specBox);

        Scene dialogScene = new Scene(dialogVBox, 450, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    private int[] getData(){
        int[] data = {0,0};
        if(punctCheck.isSelected()){
            data[0] = 1;
        }
        if (numCheck.isSelected()){
            data[1] = 1;
        }
        return data;
    }
}
