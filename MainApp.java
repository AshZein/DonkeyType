import Controller.Controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application{
    Controller control;
    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage pStage) throws Exception {
        control = new Controller(pStage);

    }

    /**
     *
     */
}
