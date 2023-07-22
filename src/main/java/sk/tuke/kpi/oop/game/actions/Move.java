package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;


public class Move<T extends Movable> implements Action<T> {

    private T able;
    private final Direction regulation;
    private float length;
    private boolean start;
    private boolean is_was;

    @Override
    public void reset() {
        length = 0;
        able.stoppedMoving();
        is_was = false;
        start = false;
    }
    @Override
    public T getActor() {
        return able;
    }
    @Override
    public boolean isDone() {
        return start;
    }
    @Override
    public void setActor( T movable) {
        this.able = movable;
    }
    public Move(Direction direction, float duration) {
        this.regulation = direction;
        this.length = duration;
        start = false;
        is_was = false;
    }


    @Override
    public void execute(float deltaTime) {
        if (getActor() == null) {

            return;
        }
        length -= deltaTime;
        if (!is_was&&!isDone()) {
            is_was =true;
            able.startedMoving(regulation);
        }

        if (!isDone()) {
            if (length > 0) {
                moveActor();

            }else if(length<=0 ) {
                stop();
            }
        }
    }
    public void stop() {
        if (able == null) {
            return;
        }
        this.start = true;
        able.stoppedMoving();
    }
    public void moveActor (){
        able.setPosition(able.getPosX() + regulation.getDx() * able.getSpeed(), able.getPosY() + regulation.getDy() * able.getSpeed());
        assert getActor() != null;
        if((Objects.requireNonNull(getActor().getScene())).getMap().intersectsWithWall(able)) {
            int x=0;
            int y=0;
            if( regulation.getDx()>0){
                x = regulation.getDx()-2;
            }else if ( regulation.getDx()<0){
                x = regulation.getDx()+2;
            }
            if( regulation.getDy()>0){
                y = regulation.getDy()-2;
            }else if ( regulation.getDy()<0){
                y = regulation.getDy()+2;
            }
            able.setPosition(able.getPosX() + x* able.getSpeed() , able.getPosY() + y* able.getSpeed());
            able.collidedWithWall();
        }
    }
}
