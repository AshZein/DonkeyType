package Controller.Strategy;

import PromptGenerator.PromptGenerator;
import PromptGenerator.NumberDecorator;
import PromptGenerator.SymbolDecorator;


import java.io.IOException;

public class NormalStrategy implements Strategy {
    PromptGenerator promptGenerator;

    public NormalStrategy() throws IOException {
        this.promptGenerator = new PromptGenerator();
    }

    @Override
    public String execute(int[] data) {
        if(data[0] == 1 && data[1] == 1){
            SymbolDecorator symDec = new SymbolDecorator(promptGenerator);
            return new NumberDecorator(symDec).getNextPrompt();
        }
        else if(data[0] == 1){
            return new SymbolDecorator(promptGenerator).getNextPrompt();
        }
        else if (data[1]==1){
            return new NumberDecorator(promptGenerator).getNextPrompt();
        }
        else{
            return promptGenerator.getNextPrompt();
        }
    }
}
