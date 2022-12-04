package Controller;
import Controller.Strategy.*;

public class Context {
    private Strategy strategy;

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public String executeStrategy(int[] data){ return strategy.execute(data); }
}
