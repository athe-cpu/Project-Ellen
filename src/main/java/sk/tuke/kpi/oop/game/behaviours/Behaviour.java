package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;

public interface Behaviour<D extends Actor> {
    void setUp(D actor);
    Disposable stop();
}
