package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;
import java.util.Objects;

public class Take <A extends Keeper> extends AbstractAction<A>{
    @Override
    public void execute(float deltaTime) {
        if(isFalse()){
            return;
        }
            Scene scene = getActor().getScene();
            List<Actor> actorsList = Objects.requireNonNull(getActor().getScene()).getActors();
            for (Actor actor : actorsList) {
                if (actor instanceof Collectible&&actor.intersects(Objects.requireNonNull(getActor()))) {
                    assert scene != null;
                    itemIntersects(scene, actor);
                    break;
                }
            }
            setDone(true);
    }
    private boolean isFalse(){
        if (getActor()==null) {
            setDone(true);
            return true;
        }
        if (isDone()) {
            setDone(true);
            return true;
        }
        if (Objects.requireNonNull(getActor()).getBackpack().getSize() >= getActor().getBackpack().getCapacity()) {
            setDone(true);
            return true;
        }
        return false;
    }
    private void itemIntersects(Scene scene,Actor actor){
           try {
               Objects.requireNonNull(getActor()).getBackpack().add((Collectible) actor);
               assert scene != null;
               scene.removeActor(actor);

           } catch (IllegalStateException exception) {
               assert scene != null;
               scene.getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
           }
       }
}


