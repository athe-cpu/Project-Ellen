package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class TimeBomb extends AbstractActor {
    private final Animation activated_bomb;
    private final Animation explode;
    private boolean is_Activated=false;
    private boolean explode_bomb=false;
    private float  seconds;
    public TimeBomb(float time){
        seconds=time;
        Animation bomb = new Animation("sprites/bomb.png");
        activated_bomb = new Animation("sprites/bomb_activated.png", 16, 16, 0.2f);
        explode = new Animation("sprites/small_explosion.png", 64, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(bomb);
    }
    public void activate(){
        is_Activated=true;
        setAnimation(activated_bomb);
        new ActionSequence<>(
            new Wait<>(this.seconds),
            new Invoke<>(this::fire),
            new Invoke<>(this::remove)
        ).scheduleFor(this);
    }
    public  boolean isActivated() {
        return is_Activated;
    }
    public  boolean isboom() {
        return explode_bomb;
    }
    private void remove() {
        Objects.requireNonNull(getScene()).removeActor(this);
    }

    public void fire() {
        setAnimation(explode);
        new Invoke<>(this::remove);
        explode_bomb=true;
    }
}
