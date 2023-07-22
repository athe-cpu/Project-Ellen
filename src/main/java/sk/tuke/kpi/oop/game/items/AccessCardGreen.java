package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Wall;

public class AccessCardGreen extends AbstractActor implements Collectible, Usable<Wall> {
    public AccessCardGreen() {
        Animation AccessCard = new Animation("sprites/goldenKey.png", 16, 16);
        setAnimation(AccessCard);
    }
    @Override
    public Class<Wall> getUsingActorClass() {
        return Wall.class;
    }
    @Override
    public void useWith(Wall actor) {
        actor.open();
    }
}
