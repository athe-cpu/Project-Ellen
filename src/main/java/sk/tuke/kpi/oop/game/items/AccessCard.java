package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.ClosedDoor;

public class AccessCard extends AbstractActor implements Collectible, Usable<ClosedDoor> {
    public AccessCard() {
        Animation AccessCard = new Animation("sprites/greenKey.png", 16, 16);
        setAnimation(AccessCard);
    }
    @Override
    public void useWith(ClosedDoor actor) {
        actor.unlock();
    }
    @Override
    public Class<ClosedDoor> getUsingActorClass() {
        return ClosedDoor.class;
    }
}
