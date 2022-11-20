import Model.PhraseState;
import Views.TypingView;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application{
    TypingView view;
    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage pStage) throws Exception {
        PhraseState phraseState = new PhraseState();
        this.view = new TypingView(pStage, phraseState);

    }

    /**
     *
     */
}
