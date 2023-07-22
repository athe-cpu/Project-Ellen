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
import sk.tuke.kpi.oop.game.Strategy.Activity;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.ChaseRipley;
import sk.tuke.kpi.oop.game.observer.Effect;
import sk.tuke.kpi.oop.game.observer.Observer;

import java.util.Objects;

public class Monster extends AbstractActor implements Movable,Enemy ,Alive, Actor , Observer, Activity {
    public static final Topic<Monster> MONSTER_DIED = Topic.create("AlienMother died", Monster.class);
    private final Health alienHealth;
    private final Animation Monster;
    private int speed_od_move;
    private final Armor MonsterArmor;
    private  Behaviour<?  super Monster> move;
    public  Monster(int healthValue, Behaviour<? super Monster> behaviour, Effect o){
        this.speed_od_move=2;
        this.move=behaviour;
        o.registerObserver( this);
        MonsterArmor = new Armor(100);
        alienHealth = new Health(healthValue,1000);
        Monster = new Animation("sprites/monster_2.png",78,127,0.2f);
        setAnimation(Monster);
        alienHealth.onExhaustion(() -> {
            Objects.requireNonNull(getScene()).getMessageBus().publish(MONSTER_DIED,this);
            Objects.requireNonNull(getScene()).removeActor(this);
        });
    }
    public void showMonsterState(){
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().getOverlay().drawText("Boss Health: " +alienHealth.getValue(), 200, yTextPos);
        getScene().getGame().getOverlay().drawText("," +MonsterArmor.getValue(), 380, yTextPos);
    }
    public void startedMoving(Direction direction) {
        Monster.setRotation(direction.getAngle());
        Monster.play();
    }

    @Override
    public void stoppedMoving() {
        Monster.stop();
    }

    public void setSpeed(int number) {
        this.speed_od_move=number;
    }

    @Override
    public int getSpeed() {
        return speed_od_move;
    }

    @Override
    public Health getHealth() {
        return alienHealth;
    }

    @Override
    public Armor getArmor() {
        return MonsterArmor;
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
    @Override
    public void update() {
        alienHealth.drain(100);
    }

    @Override
    public void doIt() {
        Effect g = new Effect();
        move.stop().dispose();
        int x= this.getPosX();
        int y= this.getPosY();
        int health = alienHealth.getValue();
        Objects.requireNonNull(getScene()).removeActor(this);
        Monster gh =new Monster(health,new ChaseRipley(),g);
       getScene().addActor(gh,x,y);

    }
}
