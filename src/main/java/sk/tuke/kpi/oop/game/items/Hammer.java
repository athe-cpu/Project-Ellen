package sk.tuke.kpi.oop.game.items;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;


public class Hammer extends BreakableTool<Repairable> implements  Collectible {
    public Hammer() {
        this(1);
    }
    public  Hammer(int Uses){
        super(Uses);
        Animation normalAnimation =  new Animation("sprites/hammer.png");
        setAnimation(normalAnimation);
    }
    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
    public void useWith(Repairable first) {
        if (first == null) {
            return;
        }
        if (first.repair()) {
            super.useWith(first);
        }
    }
}
