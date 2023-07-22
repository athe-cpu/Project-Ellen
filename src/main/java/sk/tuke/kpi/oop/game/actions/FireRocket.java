package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.Objects;

public class FireRocket<A extends Armed> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {

        if (getActor() == null || isDone()) {
            setDone(true);
            return;
        }

        Fireable fire = getActor().getRocket().fire();
        if (fire == null) {
            return;
        }

        int X = Direction.fromAngle(getActor().getAnimation().getRotation()).getDx();
        int Y = Direction.fromAngle(getActor().getAnimation().getRotation()).getDy();
        Direction side = setSide(X,Y);

        Objects.requireNonNull(getActor().getScene()).addActor(fire, getActor().getPosX() + 8 + X * 24, getActor().getPosY() + 8 + Y * 24);
        assert side != null;
        fire.getAnimation().setRotation(side.getAngle());
        new Move<Fireable>(Direction.fromAngle(getActor().getAnimation().getRotation()), Float.MAX_VALUE).scheduleFor(fire);

        setDone(true);
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
}
