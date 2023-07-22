package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;
import sk.tuke.kpi.oop.game.weapons.Rocketgun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Armed, Movable,Alive, Keeper, Actor {
    private  int ammo;
    private final int speed;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    public static final Topic<Ripley> RIPLEY_ALIVE = Topic.create("ripley alive", Ripley.class);
    private int decreaseEnergy;
    private  Disposable disposEnergy=null;
    private  Disposable disposPrinting=null;
    private final Animation playerAnimation;
    private final Animation dead;
    private final Health health;
    private final Firearm weaponRocket;
    private final Armor RipleyArmor;
    private Firearm weaponBullet;
    private final Backpack backpack;
    public Ripley (){
        super("Ellen");
         this.health = new Health(100,250);
         this.speed=2;
         this.weaponRocket = new Rocketgun(10,1000);
         this.weaponBullet =new Gun(10,1000);
         playerAnimation = new Animation( "sprites/player.png", 32, 32,  0.1f, Animation.PlayMode.LOOP_PINGPONG);
         dead = new Animation("sprites/player_die.png",32,32,0.1f, Animation.PlayMode.ONCE);
         setAnimation(playerAnimation);
         backpack = new Backpack("bag",10);
         playerAnimation.stop();
        RipleyArmor=new Armor(0);
        health.onExhaustion(() -> {
            this.setAnimation(dead);
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED,this);
        });
    }
    public void printWictory(){
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int windowWidth = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getWidth();
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> {
                    getScene().getGame().getOverlay().drawText("YOU WIN" , windowHeight/2, windowWidth/2);
                })
            )
        ).scheduleFor(this);
    }
    public void printDefeat(){
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int windowWidth = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getWidth();
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> {
                    getScene().getGame().getOverlay().drawText("YOU LOSE" , windowHeight/2, windowWidth/2);
                })
            )
        ).scheduleFor(this);
    }
    public Backpack getBackpack(){
        return backpack;
    }
    @Override
    public int getSpeed() {
        return this.speed;
    }
    @Override
    public void startedMoving(Direction direction) {
        playerAnimation.setRotation(direction.getAngle());
        playerAnimation.play();
    }
    @Override
    public void stoppedMoving() {
        playerAnimation.stop();
    }

    public void isdead(){
        if (this.health.getValue() <= 0) {
            this.setAnimation(dead);
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
        }
    }
    @Override
    public  Health getHealth() {
        return health;
    }

    @Override
    public Armor getArmor() {
        return RipleyArmor;
    }

    public int getHea() {
        return health.getValue();
    }

    public void setHealth(int energy){
        health.setValue(energy);
    }
    public  void setAmmo(int Ammo){
        ammo=Ammo;
    }
    public  int getAmmo(){
       return ammo;
    }
    public void showRipleyState(){
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int windowWidth = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getWidth();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int xTextPos = windowWidth - GameApplication.STATUS_LINE_OFFSET;
        Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_ALIVE,this);
        getScene().getGame().getOverlay().drawText("Ripley Health: " +health.getValue(), 500, yTextPos);
        getScene().getGame().getOverlay().drawText("(Bullet)Ammo: " +this.getFirearm().getAmmo(), xTextPos/40, 530);
        getScene().getGame().getOverlay().drawText("(Rocket)Ammo: " +this.getRocket().getAmmo(), xTextPos/40, 500);
    }
    public void printText(){
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        disposPrinting = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> {
                    getScene().getGame().getOverlay().drawText("Goal: Slay the boss or go out off here" , yTextPos/40, 40);
                })
            )
        ).scheduleFor(this);
    }

    public void decreaseEnergy() {
        isdead();
        disposEnergy = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() -> {
                    isdead();
                    if (health.getValue() > 0) {
                    decreaseEnergy=health.getValue()-1;
                    this.setHealth(decreaseEnergy);}
                }),
                new Wait<>(2)
            )
        ).scheduleFor(this);
    }

    public Disposable stopDecreasingEnergy() {
        return disposEnergy;
    }
    public Disposable stopPrintingText() {
        return disposPrinting;
    }
    @Override
    public Firearm getRocket() {
        return this.weaponRocket;
    }
    @Override
    public Firearm getFirearm() {
        return this.weaponBullet;
    }
    @Override
    public void setFirearm(Firearm weapon) {
        this.weaponBullet=weapon;
    }
}
