package Views;

import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
abstract class View { // ABSTRACT CLASS IS POINTLESS
    Stage stage;
    GraphicsContext gc;
    Canvas canvas;
    //Controller controller;

    abstract void initUI();

    abstract void drawScreen();

    abstract void updateScreen();

}


