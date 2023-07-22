package sk.tuke.kpi.oop.game.observer;

import java.util.ArrayList;
import java.util.List;

public class Effect implements Observable{
    private List<Observer> aliens;

    public Effect() {
        aliens = new ArrayList<>();
    }
    @Override
    public void registerObserver(Observer o) {
        aliens.add(o);
    }
    @Override
    public void removeObserver(Observer o) {
        aliens.remove(o);
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer o : aliens)
                 o.update();
    }

}
