package Views;

import Controller.Controller;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
public class View {
    GraphicsContext gc;
    Canvas canvas;
    Scene scene;
    Controller control;

    public View(Controller control){
        this.control = control;
    }
    public Scene getScene(){
        return this.scene;
    }
}


