package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.Objects;

public class Drop <A extends Keeper> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime){
            if(isFail()){
                return;
            }
            Collectible itemForPlace = Objects.requireNonNull(getActor()).getBackpack().peek();
            assert itemForPlace != null;
            Objects.requireNonNull(getActor().getScene()).addActor(itemForPlace, getActor().getPosX() + 8, getActor().getPosY() + 8);
            getActor().getBackpack().remove(itemForPlace);
            setDone(true);
    }
    public boolean isFail(){
        if (getActor()==null) {
            setDone(true);
            return true;
        }
        if (isDone()) {
            setDone(true);
            return true;
        }

        if(Objects.requireNonNull(getActor()).getBackpack().peek()==null) {
            setDone(true);
            return true;
        }
        return false;
    }
}
