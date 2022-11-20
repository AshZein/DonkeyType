package Model;

public interface Observable<T> {

    public void register(Observer<T> o);

    public void unRegister(Observer<T> o);

    public void notifyObservers();
}
