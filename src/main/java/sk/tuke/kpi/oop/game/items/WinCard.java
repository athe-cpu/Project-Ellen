package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.WinDoor;

public class WinCard extends AbstractActor implements Collectible, Usable<WinDoor> {
    public WinCard() {
        Animation AccessCard = new Animation("sprites/winCard.png", 16, 16);
        setAnimation(AccessCard);
    }
    @Override
    public void useWith(WinDoor actor) {
        actor.unlock();
    }
   @Override
    public Class<WinDoor> getUsingActorClass() {
        return WinDoor.class;
    }
}
