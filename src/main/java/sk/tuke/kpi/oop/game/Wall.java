package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.oop.game.openables.Openable;

import java.util.Objects;

public class Wall extends AbstractActor implements Usable<Actor>, Openable {
    private  boolean is_open;

    public enum  Orientation {VERTICAL, HORIZONTAL }
    private Animation wallVertical= new Animation("sprites/wall.png",16,32);
    private Animation wallHorizontal= new Animation("sprites/wall1.png",32,16);
    // public Wall(){
     //   is_open = false;
     //  setAnimation(wallVertical);
     //}
    public Wall(String name, Wall.Orientation orientation){
        //super(name);
        is_open = false;
        if (orientation == Wall.Orientation.VERTICAL) {
            setAnimation(wallVertical);
        }
        if (orientation == Wall.Orientation.HORIZONTAL) {
            setAnimation(wallHorizontal);
        }
    }
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(this.getWidth()==32) {
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() /16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16+1, getPosY() / 16).setType(MapTile.Type.WALL);

        }else {
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.WALL);
        }
    }

    @Override
    public void useWith(Actor actor) {
       // if(is_open){
     //       return;
     //   }
     //   for(Collectible coll : ripley.getBackpack() )
     //   if(ripley.getBackpack().peek()==){
     //       return;
    //    }
    //    is_open=true;
    //       open();
    }
    @Override
    public boolean isOpen() {
        return is_open;
    }
    @Override
    public void close() {}

    @Override
    public void open() {
            is_open=true;
        if(this.getWidth()==32) {
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16+1, getPosY() / 16).setType(MapTile.Type.CLEAR);
            getScene().removeActor(this);
        }else {
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16 + 1).setType(MapTile.Type.CLEAR);
            getScene().removeActor(this);
        }
    }
    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
