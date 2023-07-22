package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable {
    private int max = 20;
    private int min = 0;
    private int range = max - min + 1;
    private boolean repaired = false;
    private Disposable first;
    public DefectiveLight() {
       repaired = false;
    }
    public void changelight(){
            int rand = (int)(Math.random() * range) + min;
                if (rand == 1 ) {
                    super.toggle();
                }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        first = new Loop<>(new Invoke<Actor>(this::changelight)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if (first == null || repaired){
            repaired = false;
            breakL();
            return false;
        }
        else {
            repaired = true;
            first.dispose();
        }
        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(this::repair)
        ).scheduleFor(this);
        return true;
    }

    public void breakL() {
       first = new Loop<>(new Invoke<>(this::changelight)).scheduleFor(this);
    }

}
