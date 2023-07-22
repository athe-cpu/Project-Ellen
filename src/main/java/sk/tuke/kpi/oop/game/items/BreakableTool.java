package sk.tuke.kpi.oop.game.items;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A>  {
    private int Uses;
    private Ripley rip = new Ripley();
    public BreakableTool() {
        this(4);
    }
    public BreakableTool(int remainingUses) {
        this.Uses = remainingUses;
   }
    public int getRemainingUses() {
        return Uses;
    }
    public void setRemainingUses(int remaining ){
        Uses=remaining;
    }
    @Override
    public void useWith(A actor) {
        for (Actor actor1: Objects.requireNonNull(actor.getScene()).getActors()) {
            if (actor1 instanceof Ripley) {
                rip = (Ripley) actor1;
            }
        }
        this.Uses--;
        if (this.Uses == 0) {
            assert rip != null;
            rip.getBackpack().remove(((Collectible) this));
        }
    }
}
