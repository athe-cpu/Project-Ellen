package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {
    public Helicopter(){
        Animation normalAnimation = new Animation("sprites/heli.png",64, 64);
        setAnimation(normalAnimation);
    }

    public void invoke(){
         int y;
         int x;
        Player player = (Player) getScene().getFirstActorByName("Player");
        y=this.getPosY();
        x=this.getPosX();
        if(y>player.getPosY()){
            y--;
            setPosition(x,y);
        }
        if(y<player.getPosY()){
            y++;
            setPosition(x,y);
        }
        if(x<player.getPosX()){
            x++;
            setPosition(x,y);
        }
        if(x>player.getPosX()){
            x--;
            setPosition(x,y);
        }
        if(intersects(player)){
            player.setEnergy(player.getEnergy()-1);
        }
    }

    public void searchAndDestroy(){
        new Invoke<>(this::invoke).scheduleFor(this);
        new Loop<>(new Invoke<>(this::invoke)).scheduleFor(this);
    }
}
