package Views;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
public class View {
    GraphicsContext gc;
    Canvas canvas;
    Scene scene;
    //Controller controller;

    public Scene getScene(){
        return this.scene;
    }
}


