package Model;

public interface Observable<T> {

    void register(Observer<T> o);

    void unRegister(Observer<T> o);

    void notifyObservers();
}
