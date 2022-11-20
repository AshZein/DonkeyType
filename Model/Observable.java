package Model;

public interface Observable {

    public void register(Observer o);

    public void unRegister(Observer o);

    public void notifyObservers();
}
