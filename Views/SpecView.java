package Views;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpecView {
    CheckBox punctCheck;
    CheckBox numCheck;

    CheckBox normalCheck;
    CheckBox quoteCheck;
    final Stage dialog;
    HBox typeBox;
    HBox specBox;

    public SpecView(Controller control, TypingView typingView){

        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(control.stage);
        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(30, 10, 10, 10));
        dialogVBox.setStyle(typingView.UIColor);

        // prompt type check boxes
        normalCheck = new CheckBox("Normal");
        normalCheck.setFont(typingView.buttonFont);
        normalCheck.setStyle(typingView.buttonColorTime[0]);
        normalCheck.setSelected(true);

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
//prompt type checkbox handlers
        quoteCheck.setOnAction(e -> {
            if(!quoteCheck.isSelected()){
                quoteCheck.setSelected(true);
            }
            else{
                control.updateStrategy("quote");
                normalCheck.setSelected(false);
            }
            hideButtons("Numbers");
        });

        normalCheck.setOnAction(e -> {
            if(!normalCheck.isSelected()){
                normalCheck.setSelected(true);
            }
            else{
                control.updateStrategy("normal");
                quoteCheck.setSelected(false);
            }
            showButtons("Numbers");
        });

        // prompt modifier check box handlers
        punctCheck.setOnAction(e -> {
            control.updateStrategyData(getData());
        });

        numCheck.setOnAction(e -> {
            control.updateStrategyData(getData());
        });

        specBox = new HBox(20, punctCheck, numCheck);
        specBox.setAlignment(Pos.TOP_LEFT);

        typeBox = new HBox(40, normalCheck, quoteCheck);
        typeBox.setAlignment(Pos.TOP_LEFT);

        dialogVBox.setAlignment(Pos.TOP_LEFT);

        dialogVBox.getChildren().add(new Text("Type:"));
        dialogVBox.getChildren().add(typeBox);

        dialogVBox.getChildren().add(new Text("Modifiers:"));
        dialogVBox.getChildren().add(specBox);

        Scene dialogScene = new Scene(dialogVBox, 450, 200);
        dialog.setScene(dialogScene);
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

    public void hideButtons(String button){
        if(button == "Numbers") {
            specBox.getChildren().remove(numCheck);
        }
    }
    public void showButtons(String button){
        if(button == "Numbers"){
            specBox.getChildren().add(numCheck);
        }
    }
    /*
     * show the specView dialog box
     */
    public void show(){
        dialog.show();
    }
}
