package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer{
    private boolean is_on;
    public Computer(){
        is_on=false;
        Animation normalAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
    }
    public boolean  isX(){
        return is_on;
    }
    public int add(int first, int second){
        if(isX()) {
             return first + second;
        }
        return 0;
    }
    public int sub(int first, int second){
        if(isX()) {
            return first - second;
        }
        return 0;
    }
    public float add(float first, float second){
        if(isX()) {
            return first + second;
        }
        return 0;
    }
    public float sub(float first, float second){
        if(isX()) {
            return first - second;
        }
        return 0;
    }
    public void setPowered(boolean powered) {
        is_on = powered;
        if(is_on) {
            getAnimation().play();
        }else getAnimation().pause();
    }
}
