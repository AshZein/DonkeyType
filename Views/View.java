package Views;

import Controller.Controller;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
public class View {
    GraphicsContext gc;
    Canvas canvas;
    BorderPane root;
    Controller control;

    public View(Controller control){
        this.control = control;
        root = new BorderPane();
    }
    public BorderPane getRoot(){
        return this.root;
    }
}


