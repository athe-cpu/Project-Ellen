package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch  extends AbstractActor {
  private Switchable turn;

    public PowerSwitch(Switchable switchable) {
        Animation normalAnimation = new Animation("sprites/switch.png", 16, 16);
        setAnimation(normalAnimation);
        turn = switchable;
    }

    public Switchable getDevice(){
        return turn;
    }
    public boolean isOn(){
        if (turn == null) {
               return false;
        }
        return true;
    }
    public void switchOn() {
        if (isOn()) {
            turn.turnOn();
            getAnimation().setTint(Color.WHITE);
        }
    }
    public void switchOff() {
       if (isOn()) {
           turn.turnOff();
           getAnimation().setTint(Color.GRAY);
       }
    }

}
