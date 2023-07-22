package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.GameApplication;
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
import sk.tuke.kpi.oop.game.observer.Observable;

import java.util.Objects;

public class AlienMother extends AbstractActor implements Movable,Enemy ,Alive, Actor {
    public static final Topic<AlienMother> ALIEN_MOTHER_DIED = Topic.create("AlienMother died", AlienMother.class);
    private final Health alienHealth;
    private final Armor alienArmor;
    private final Animation AlienMother;
    private int speed_od_move;
    private  Behaviour<?  super AlienMother> move;
    public  AlienMother( int healthValue,Behaviour<? super AlienMother> behaviour){
        this.speed_od_move=2;
        this.move=behaviour;

        alienArmor  = new Armor(100);
        alienHealth = new Health(healthValue,1000);
        AlienMother = new Animation("sprites/mother.png",112,162,0.2f);
        setAnimation(AlienMother);
        alienHealth.onExhaustion(() -> {
            Objects.requireNonNull(getScene()).getMessageBus().publish(ALIEN_MOTHER_DIED,this);
            Objects.requireNonNull(getScene()).removeActor(this);
        });
    }
    public void showAlienMotherState(){
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().getOverlay().drawText("Boss Health: " +alienHealth.getValue(), 200, yTextPos);
        getScene().getGame().getOverlay().drawText("," +alienArmor.getValue(), 380, yTextPos);
    }
    public void startedMoving(Direction direction) {
        AlienMother.setRotation(direction.getAngle());
        AlienMother.play();
    }

    @Override
    public void stoppedMoving() {
        AlienMother.stop();
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
                ((Alive) notdead).getHealth().drain(20);
                new Invoke<>(this::kill);
                new Wait<>(0.5f);
            }
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (move != null) {
            move.setUp(this);
        }
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::kill),
                new Wait<>(0.5f)
            )).scheduleFor(this);
    }


}
