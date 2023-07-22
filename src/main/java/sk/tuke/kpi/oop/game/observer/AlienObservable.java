package sk.tuke.kpi.oop.game.observer;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.ChaseRipley;
import sk.tuke.kpi.oop.game.characters.*;

import java.util.Objects;

public class AlienObservable extends AbstractActor implements Movable, Enemy, Alive, Actor, Observer {
    private final Animation alienAnimation = new Animation("sprites/alien.png",32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Health alienHealth;
    private int speed_od_move;
    private Armor alienArmor;
    private Disposable cancel;
    public static final Topic<AlienObservable> ALIEN_DIED = Topic.create("Alien died", AlienObservable.class);
    private  Behaviour<? super AlienObservable> move;

    public AlienObservable(Behaviour<? super AlienObservable> move, int alienHP, Effect observer) {
        this.move=move;
        setAnimation(alienAnimation);
        this.speed_od_move=1;
        observer.registerObserver( this);
        this.alienArmor =new Armor(0);
        alienHealth = new Health(alienHP, 100);
        alienHealth.onExhaustion(() -> {
            Objects.requireNonNull(getScene()).removeActor(this);
            Objects.requireNonNull(getScene()).getMessageBus().publish(ALIEN_DIED,this);
            observer.removeObserver( this);
        });
    }
    public void spawn(){
        Alien a = new Alien( new ChaseRipley(),100);
        Objects.requireNonNull(getScene()).addActor(a);
        a.setPosition(500,1000);
    }
    public void startedMoving(Direction direction) {
        alienAnimation.setRotation(direction.getAngle());
        alienAnimation.play();
    }

    @Override
    public void stoppedMoving() {
        alienAnimation.stop();
    }

    public void setSpeed(int number) {
        this.speed_od_move=number;
    }

    @Override
    public int getSpeed() {
        return speed_od_move;
    }
    @Override
    public Armor getArmor() {
        return alienArmor;
    }
    @Override
    public Health getHealth() {
        return alienHealth;
    }
    public void kill(){
        for (Actor notdead :Objects.requireNonNull(getScene()).getActors()) {
            if (notdead instanceof Alive && !(notdead instanceof Enemy) && this.intersects(notdead)) {
                ((Alive) notdead).getHealth().drain(5);
                new Invoke<>(this::kill);
                new Wait<>(0.5f);
            }
        }
    }
    public Disposable stopDecreasingEnergy() {
        return cancel;
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (move != null) {
            move.setUp(this);
        }
        cancel = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::kill),
                new Wait<>(0.5f)
            )).scheduleFor(this);
    }

    @Override
    public void update() {
        alienHealth.drain(0);
    }
}
