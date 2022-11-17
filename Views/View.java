package Views;

import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
abstract class View {
    Stage stage;
    GraphicsContext gc;
    Canvas canvas;
    //Controller controller;

    public abstract void initUI();

    public abstract void drawScreen();

}


