package Views;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
abstract class View {
    Stage stage;

    public abstract void initUI();

    public abstract void drawScreen();

}


