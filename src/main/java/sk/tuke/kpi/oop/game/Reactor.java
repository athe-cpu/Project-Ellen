package sk.tuke.kpi.oop.game;
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.gamelib.Scene;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable {
    private int temperature;
    private int damage=0;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation Extinguish;
    private int turnOn =0;
    private Set<EnergyConsumer> devices;
    public Reactor(){
        devices = new HashSet<>();
        this.temperature = 0;
        this.damage = 0;
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.01f);
        Extinguish = new Animation("sprites/reactor_extinguished.png",80,80);
        updateAnimation();

    }
    public int getTemperature(){
        return this.temperature;
    }
    public int getDamage(){
        return this.damage;
    }

    public int getDama(int heat ) {
        if(temperature>=2000) {
            int a = damage;
            damage = Math.round(((heat - 2000) * 100) / 4000);
            if (a > damage) {
                damage = a;
            }
        }
        return damage;
    }
    public int increaseHeat(int increment){
        if(damage>=33&&damage<=66){
            temperature += Math. round(increment*0.5);
        } else if(damage>66&&damage<100){
            temperature += increment;
        } else if(damage>=100){
            //temperature=6000;
            damage=100;
        }
        return  temperature;
    }
    public void increaseTemperature(int increment){
          if(turnOn==1&&increment>0) {
              if (increment >= 6000) {
                  temperature += increment;
                  damage=100;
                  setAnimation(brokenAnimation);
                  turnOff();
                  return;
              }
                  temperature += increment;
                  temperature = increaseHeat(increment);
                  damage = getDama(temperature);
              if (temperature >= 6000||damage>=100) {
                 // temperature=6000;
                  damage=100;
                  setAnimation(brokenAnimation);
                  turnOff();
              }
              updateAnimation();

       }
    }
    public void decreaseTemperature(int decrement){
        if(turnOn==1) {
            if (temperature - decrement < 0) {
                this.temperature = 0;
                return;
            }
            if (decrement <= 0) {
                return;
            }
            if (temperature <= 0) {
                return;
            }
            if (damage >= 100) {
                this.temperature = 0;
            }
            if (damage >= 50) {
                this.temperature -= decrement * 0.5;
            }
            if (damage < 50) {
                this.temperature -= decrement;
            }
            updateAnimation();
        }
    }

    private void updateAnimation(){
        if( turnOn==0) {
            normalAnimation = new Animation("sprites/reactor.png", 80, 80,0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(normalAnimation);
        }
        if( turnOn==1) {
            if(temperature<4000){
                normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f-((damage/100f)*0.1f), Animation.PlayMode.LOOP_PINGPONG);
                setAnimation(normalAnimation);
            }else if(temperature>=4000&&damage<90){
                hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f-((damage/100f)*0.05f), Animation.PlayMode.LOOP_PINGPONG);
                setAnimation(hotAnimation);
            }else if(damage>=90&&damage<100){
                hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.015f, Animation.PlayMode.LOOP_PINGPONG);
                setAnimation(hotAnimation);
            }else setAnimation(normalAnimation);
        }

        if(damage>=100) {
            brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(brokenAnimation);
            turnOff();
        }
    }
    public boolean repair(){
        if(damage>0&&damage<100) {
            temperature = ((damage - 50) * 40) + 2000;
            if (damage <= 50 && damage > 0) {
                damage = 0;
                updateAnimation();
                return true;
            }
            if (damage > 50) {
                damage -= 50;
                updateAnimation();
                return true;
            }
        }
        return false;
    }
    public boolean extinguish(){
        if ( damage == 0  || turnOn==1) {
            return false;
        }
        else {
            this.temperature =  this.getTemperature() - 4000;
            setAnimation(Extinguish);
            return true;
        }
    }

    @Override
    public boolean isOn(){
        if(turnOn == 0) {
            return false;
        }
            return true;
    }
    @Override
    public void turnOn(){
        turnOn = 1;
        updateDevices();
        updateAnimation();
        isOn();
    }
    @Override
    public void turnOff(){
        turnOn = 0;
        updateDevices();
        isOn();
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        scene.scheduleAction(new PerpetualReactorHeating(2), this);
        new PerpetualReactorHeating(2).scheduleFor(this);

    }

    private void updateDevice(EnergyConsumer device){
        if(isOn() && damage < 100) {
            device.setPowered(true);
        }else device.setPowered(false);
    }

    private void updateDevices(){
        this.devices.forEach(this::updateDevice);
    }

    public void addDevice(EnergyConsumer device){
        this.devices.add(device);
        device.setPowered(isOn());
    }
    public void removeDevice(EnergyConsumer device){
        device.setPowered(false);
        this.devices.remove(device);
    }

}
