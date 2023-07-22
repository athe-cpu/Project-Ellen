package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;

import java.util.Objects;

public class Bullet extends AbstractActor implements Fireable {
    private final int move;
    public Bullet(){
        Animation bulletPicture = new Animation("sprites/bullet.png", 16, 16);
        setAnimation(bulletPicture);
        move = 8;
    }
    public void startedMoving(Direction direction) {
        if (direction != null) {
            return;
        }
        assert false;
        this.getAnimation().setRotation(direction.getAngle());
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::releaseBullet)
        ).scheduleFor(this);
    }

    private void releaseBullet() {
        for (Actor A : Objects.requireNonNull(getScene()).getActors()) {
            if (this.intersects(A) && (A instanceof Alive)) {
                if(((Alive) A).getArmor().getValue()>0) {
                    ((Alive) A).getHealth().drain(1);
                }else{
                    ((Alive) A).getHealth().drain(100);
                }
                if(getScene().getMap().intersectsWithWall(this)) {
                    collidedWithWall();
                    getScene().removeActor(this);
                }
                collidedWithWall();
                getScene().removeActor(this);
            }
        }
    }
    @Override
    public void collidedWithWall() {
        Objects.requireNonNull(getScene()).removeActor(this);
    }

    @Override
    public int getSpeed() {
        return move;
    }
}
