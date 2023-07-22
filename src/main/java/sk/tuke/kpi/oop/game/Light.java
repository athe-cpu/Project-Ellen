package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.Scene;
import org.jetbrains.annotations.NotNull;
public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private boolean is_powered;
    private boolean is_on;
    private final Animation animation_on;
    private final Animation animation_off;
    public Light(){
        animation_on = new Animation("sprites/light_on.png", 16, 16);
        animation_off = new Animation("sprites/light_off.png", 16, 16);
        setAnimation(animation_off);
        is_on = false;
        is_powered = false;
    }

    public void animation() {
if(is_on){
    if(is_powered){
        setAnimation(animation_on);
    }else setAnimation(animation_off);
}else setAnimation(animation_off);
    }
    public void toggle(){
        if (is_on) {
            is_on=false;
            animation();
            return;
        }
        if (!is_on) {
            is_on=true;
            animation();
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<Actor>(this::animation)).scheduleFor(this);
    }
    @Override
    public void turnOn(){
        is_on = true;
        animation();
    }
    @Override
    public void turnOff(){
        is_on = false;
        animation();
    }
    @Override
    public boolean isOn(){
        return is_on;
    }
    public void setPowered(boolean powered) {
        is_powered = powered;
        animation();
    }
}
