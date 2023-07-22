package sk.tuke.kpi.oop.game.items;


import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;

import java.util.Objects;

public class Energy extends AbstractActor implements Usable<Alive>{
    public Energy (){
        Animation energyAnimation = new Animation("sprites/energy.png", 16,16);
        setAnimation(energyAnimation);
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
    @Override
    public void useWith(Alive c) {
        if(c==null){
            return;
        }
        if(c.getHealth().getValue()==c.getHealth().getMax()){
            return;
        }else{
            c.getHealth().restore();
            Objects.requireNonNull(getScene()).removeActor(this);

        }
    }
}
