package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class SmartCooler extends Cooler {

    public SmartCooler(Reactor react){
        super(react);
        Animation normalAnimation = new Animation("sprites/fan.png",32, 32,0.2F);
        setAnimation(normalAnimation);
    }

    private void coolReactor() {
        Reactor react = getR();
        if (react != null) {
            if (getR().getTemperature() >= 2500) {
                turnOn();
            } else if (getR().getTemperature() <= 1500) {
                turnOff();
            }
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene){
            set();
            super.addedToScene(scene);
            new Invoke<>(this::coolReactor).scheduleFor(this);
            new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
