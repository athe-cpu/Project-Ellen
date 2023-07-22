package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Objects;

    public class Teleport extends AbstractActor{
    private Teleport jump;
    private boolean ready_to_teleport;

    public Teleport(Teleport teleport) {
        this.jump = teleport;
        setAnimation(new Animation("sprites/lift.png"));
    }
    public Teleport getDestination(){
        return jump;
    }
    public void setDestination(Teleport destinationTeleport) {
        if(destinationTeleport==null){
            jump =null;
            return;
        }
        if(destinationTeleport==this){
            return;
        }
        jump = destinationTeleport;
    }

    public void teleportation() {
        Ripley player = (Ripley) getScene().getFirstActorByName("Ellen");
        if(!this.ready_to_teleport && !this.intersects(player)) this.ready_to_teleport = true;
        Ellipse2D.Float Divide = new Ellipse2D.Float(this.getPosX(), this.getPosY(), 24, 24);
        Rectangle2D.Float coordinate =new Rectangle2D.Float(player.getPosX(), player.getPosY(), 16,16);
        if(Divide.intersects(coordinate) && this.ready_to_teleport  && jump != null){
                ready_to_teleport=false;
                List<Actor> list = Objects.requireNonNull(getScene()).getActors();
                for (Actor actor: list) {
                    actor.getPosX();
                    jump.ready_to_teleport=false;
                }
                    //player.setPosition(jump.getPosX() + jump.getWidth() / 2 - player.getWidth() / 2, jump.getPosY() + jump.getHeight() / 2 - player.getHeight() / 2);
           // teleportPlayer(player);
            jump.teleportPlayer(player);

        }
    }
    public void teleportPlayer(Ripley player) {
        if(!this.ready_to_teleport && !this.intersects(player)) {
          player.setPosition(this.getPosX() + this.getWidth() / 2 - player.getWidth() / 2, this.getPosY() + this.getHeight() / 2 - player.getHeight() / 2);
         }
        if(this.ready_to_teleport && !this.intersects(player)) {
            this.ready_to_teleport = false;
            player.setPosition(this.getPosX() + this.getWidth() / 2 - player.getWidth() / 2, this.getPosY() + this.getHeight() / 2 - player.getHeight() / 2);
        }

    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::teleportation)
        ).scheduleOn(scene);
    }
}
