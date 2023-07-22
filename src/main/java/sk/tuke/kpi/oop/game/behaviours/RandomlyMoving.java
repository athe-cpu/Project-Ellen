package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable> {
    private Disposable stop;
    public void move(Movable actor) {
        int max,min,Y,X,range;
         Direction side;
         min = -150;
         max = 150;
         range = max - min + 1;
         Y = (int)(Math.random() * range) + min;
         Y = setY(Y);
         X = (int)(Math.random() * range) + min;
         X = setX(X);
        side = setSide(X , Y);
        actor.getAnimation().setRotation(side.getAngle());
        new Move<>(side, 1).scheduleFor(actor);
    }
    public int setX(int X ){
        if(X<-50){
           return -1;
        } else if(X<50){
            return 0;
        }else if(X>50){
            return 1;
        }
        return 0;
    }
    public int setY(int Y ){
        if(Y<-50){
            return -1;
        } else if(Y<50){
            return 0;
        }else if(Y>50){
            return 1;
        }
        return 0;
    }
    public Direction setSide(int X , int Y){
        if(X==1&&Y==0) {
            return Direction.EAST;
        }else if(X==0&&Y==1){
            return Direction.NORTH;
        }else if(X==-1&&Y==0){
            return Direction.WEST;
        }else if(X==0&&Y==-1){
            return Direction.SOUTH;
        }
        return setSide1(X,Y);
    }
    public Direction setSide1(int X , int Y){
        if(X==-1&&Y==1){
            return Direction.NORTHWEST;
        }else if(X==-1&&Y==-1){
            return Direction.SOUTHWEST;
        }else if(X==1&&Y==-1){
            return Direction.SOUTHEAST;
        }else if(X==1&&Y==1){
            return Direction.NORTHEAST;
        }
        return  Direction.NONE;
    }
    public Disposable stop(){
        return stop;
    }
    @Override
    public void setUp(Movable movable) {
        if (movable==null) {
            return;
        }
        stop =  new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(this::move),
                    new Wait<>(0.55f)
                )).scheduleFor(movable);
    }

}

