package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

public class ChainBomb extends TimeBomb {
    public ChainBomb(float time) {
        super(time);
    }
    @Override
    public void fire() {
        Ellipse2D.Float Divide = new Ellipse2D.Float(this.getPosX()-50, this.getPosY()-50, 102, 102);
        super.fire();
        for (Actor actor: Objects.requireNonNull(getScene()).getActors()) {
            execute(actor,Divide);
        }
    }
    public void execute(Actor actor,Ellipse2D.Float Divide){
        if (!(actor instanceof ChainBomb) || ((ChainBomb) actor).isActivated()) {
            return;
        }
        Rectangle2D.Float coordinate = new Rectangle2D.Float(actor.getPosX(), actor.getPosY(), actor.getWidth(),actor.getHeight());
        action(actor,Divide,coordinate);

    }
    public void action(Actor actor,Ellipse2D.Float Divide,Rectangle2D.Float coordinate) {
        if (Divide.intersects(coordinate)) {
            explode(actor);
        }
    }

    public void explode(Actor actor){
        ((ChainBomb) actor).activate();
        }
}
