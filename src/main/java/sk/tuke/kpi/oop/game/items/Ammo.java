package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;

import java.util.Objects;

public class Ammo extends AbstractActor implements Usable <Armed>  {
    public Ammo (){
        Animation ammobox = new Animation("sprites/ammo.png", 16,16);
        setAnimation(ammobox);
    }

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }
    @Override
    public void useWith(Armed player){
        if(player == null){
            return;
        }
        if(player.getFirearm().getAmmo()<player.getFirearm().getMax()){
            int a=0;
            int b=0;
            a+= player.getFirearm().getAmmo()+50;
            player.getFirearm().setAmmo(0);
            b+= player.getRocket().getAmmo()+10;
            player.getRocket().setAmmo(0);
            player.getFirearm().reload(a);
            player.getRocket().reload(b);
            a=0;
            b=0;
            Objects.requireNonNull(getScene()).removeActor(this);
        }
    }
}
