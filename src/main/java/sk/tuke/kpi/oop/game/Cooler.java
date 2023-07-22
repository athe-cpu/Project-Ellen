package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {
    private Reactor react;
    private int a;
    private boolean cooleron;

    public Cooler(Reactor react){
        this.react=react;
        Animation normalAnimation = new Animation("sprites/fan.png",32, 32,0.2F);
        setAnimation(normalAnimation);
    }
    public void set(){
        a=0;
    }
    private void coolReactor(){
        if(cooleron&&react!=null) {
            react.decreaseTemperature(a);
        }
   }
    @Override
    public void turnOn(){
        cooleron = true;
        a=1;
    }
    public Reactor getR() {
        return react;
    }

    @Override
    public void turnOff(){
        cooleron = false;
        a=0;
    }
    @Override
    public boolean isOn(){return cooleron;}

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }

}
