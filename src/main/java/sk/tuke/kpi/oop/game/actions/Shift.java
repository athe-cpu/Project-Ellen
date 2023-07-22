package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

import java.util.Objects;

public class Shift<A extends Keeper> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime ){
         if(isFailed()){
            return;
          }
            getActor().getBackpack().shift();
            setDone(true);
    }

    public  boolean isFailed(){
        if (isDone()) {
            setDone(true);
            return true;
        }
        if (getActor()==null) {
            setDone(true);
            return true;
        }
        if(Objects.requireNonNull(getActor()).getBackpack().peek()==null){
            setDone(true);
            return true;
        }
        return false;
    }
}
