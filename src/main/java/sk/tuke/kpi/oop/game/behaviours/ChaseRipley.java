package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class ChaseRipley implements Behaviour<Movable>{
    private Disposable disposable = null;
    private Move<Movable> move = null;
    public Disposable stop(){
        return null;
    }
    public void chaseRipley(Movable actor){
        int AlienX = actor.getPosX();
        int AlienY = actor.getPosY();
        int posx =-2 ;
        int posy = -2;
        int RipleyX = actor.getScene().getFirstActorByType(Ripley.class).getPosX();
        int RipleyY = actor.getScene().getFirstActorByType(Ripley.class).getPosY();
        Ripley rip =actor.getScene().getFirstActorByType(Ripley.class);

        if (AlienX != RipleyX) {
            if (AlienX > RipleyX) {
                posx=1;
            } else {
                posx=-1;
            }
        }
        if (AlienY != RipleyY) {
            if (AlienY > RipleyY) {
                posy=1;
            } else {
                posy=-1;
            }
        }
        if (AlienY == RipleyY) {
            posy=0;
        }
        if (AlienX == RipleyX) {
            posx=0;
        }
        if(posx==0&&posy==0){
            return;
        }

        Direction side = chuseSide(posx,posy);


        if (move != null) {
            move.stop();
            disposable.dispose();
            move = null;
        }


        actor.getAnimation().setRotation(side.getAngle());
            move = new Move<>(side, Float.MAX_VALUE);
        if(actor.intersects(rip)){
            move.stop();
            disposable.dispose();
            move = null;
            actor.getAnimation().setRotation(side.getAngle());
            return;
        }
            disposable = move.scheduleFor(actor);

    }
   public Direction chuseSide(int posx,int posy){
        if(posx==-1&&posy==-1){
            return Direction.NORTHEAST;
        }else if(posx==-1&&posy==0){
            return Direction.EAST;
        }else if(posx==-1&&posy==1){
            return Direction.SOUTHEAST;
        }else if(posx==0&&posy==-1){
            return Direction.NORTH;
        }else if(posx==0&&posy==0){
            return Direction.NONE;
        }else if(posx==0&&posy==1){
            return Direction.SOUTH;
        }else if(posx==1&&posy==-1){
            return Direction.NORTHWEST;
        }else if(posx==1&&posy==0){
            return Direction.WEST;
        }else if(posx==1&&posy==1){
            return Direction.SOUTHWEST;
        }
        return Direction.NONE;
   }
    @Override
    public void setUp(Movable movable) {
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::chaseRipley),
                new Wait<>(0.55f)
            )).scheduleFor(movable);
    }
}
