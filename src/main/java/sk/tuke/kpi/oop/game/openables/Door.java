package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private  boolean is_open;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    private final Animation doorAnimation3 =new Animation("sprites/vdoor.png", 16, 32);
    private  Animation doorAnimation= new Animation("sprites/vdoor.png",16,32,0.1f, Animation.PlayMode.ONCE_REVERSED);
    private  Animation doorAnimation1 = new Animation("sprites/vdoor.png",16,32, 0.1f,Animation.PlayMode.ONCE );;
    private  Animation doorH = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE);
    private  Animation doorH1 =new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
    private  Animation doorH12 =new Animation("sprites/hdoor.png", 32, 16);

    public Door(){
         is_open=false;
         setAnimation(doorAnimation3);
         getAnimation().stop();
    }
    public enum  Orientation {VERTICAL, HORIZONTAL }
    public Door (String name, Orientation orientation){
        super(name);
        is_open = false;
        if (orientation == Orientation.VERTICAL) {
            setAnimation(doorAnimation3);
            getAnimation().stop();
        }
        if (orientation == Orientation.HORIZONTAL) {
            setAnimation(doorH12);
            getAnimation().stop();
        }
    }
    @Override
    public void useWith(Actor actor) {
        if (is_open) {
            close();
            return;
        }
        open();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public boolean isOpen() {
        return is_open;
    }
    public void change(){
        getAnimation().play();
        getAnimation().stop();
    }
    @Override
    public void close() {
       if(!isOpen()) {
       return;
       }
        if(this.getWidth()==32) {
            is_open=false;
            setAnimation(doorH);
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16+1, getPosY() / 16 ).setType(MapTile.Type.WALL);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() /16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            change();
            getScene().getMessageBus().publish(DOOR_CLOSED, this);
        }else {
            is_open=false;
            setAnimation(doorAnimation1);
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16+1 ).setType(MapTile.Type.WALL);
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.WALL);
            change();
            getScene().getMessageBus().publish(DOOR_CLOSED, this);
        }
    }

    @Override
    public void open() {
        if(isOpen()) {
            return;
        }
        if(this.getWidth()==32) {
            is_open=true;
            setAnimation(doorH1);
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16+1, getPosY() / 16 ).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() /16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            change();
            getScene().getMessageBus().publish(DOOR_OPENED, this);
        }else{
            is_open=true;
            setAnimation(doorAnimation);
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16+1 ).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16).setType(MapTile.Type.CLEAR);
            change();
            getScene().getMessageBus().publish(DOOR_OPENED, this);
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(this.getWidth()==32){
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16+1, getPosY() / 16 ).setType(MapTile.Type.WALL);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() /16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        }else{
            Objects.requireNonNull(getScene()).getMap().getTile(getPosX() / 16, getPosY() / 16+1 ).setType(MapTile.Type.WALL);
            Objects.requireNonNull(getScene()).getMap().getTile(this.getPosX() /16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        }
        }
}
