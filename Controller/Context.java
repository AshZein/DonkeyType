package Controller;
import Controller.Strategy.*;

import java.io.IOException;
import java.util.HashMap;

public class Context {
    private Strategy strategy;



    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public String executeStrategy(int[] data){ return strategy.execute(data); }
}
