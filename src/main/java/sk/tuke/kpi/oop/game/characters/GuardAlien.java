package sk.tuke.kpi.oop.game.characters;

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

import java.util.Objects;

public class GuardAlien extends AbstractActor implements Movable,Enemy ,Alive, Actor {
    private final Animation alienAnimation = new Animation("sprites/monster_1.png",60, 128, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Health alienHealth;
    private int speed_od_move;
    private  Armor alienArmor;
    private Disposable cancel;
    public static final Topic<GuardAlien> GUARD_DIED = Topic.create("Guard died", GuardAlien.class);
    private  Behaviour<? super GuardAlien> move;
    public GuardAlien(int healthValue, Behaviour<? super GuardAlien> behaviour){
        this.move=behaviour;
        this.speed_od_move=1;
        this.alienArmor =new Armor(100);
        setAnimation(alienAnimation);
        alienHealth = new Health(healthValue,500);
        alienHealth.onExhaustion(() -> {
            Objects.requireNonNull(getScene()).getMessageBus().publish(GUARD_DIED,this);
            Objects.requireNonNull(getScene()).removeActor(this);
        });
    }
    public void startedMoving(Direction direction) {
        alienAnimation.setRotation(direction.getAngle());
        alienAnimation.play();
    }

    @Override
    public void stoppedMoving() {
        alienAnimation.stop();
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
                ((Alive) notdead).getHealth().drain(10);
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
}
