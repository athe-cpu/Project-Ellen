package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Teleport;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class DevicesofLifeandDeath implements SceneListener {
    public static class Factory implements ActorFactory {
        Teleport a ;
        Door front;
        public Actor create(@Nullable String type, @Nullable String name){

            if(Objects.equals(name, "front door")){
                front =new Door("front door", Door.Orientation.VERTICAL);
                return front;
             //   return new Door();
            }
            if(Objects.equals(name, "back door")){
                return new Door("back door", Door.Orientation.HORIZONTAL);

            }
            if(Objects.equals(name, "Teleport")){
                return new Teleport(a);
            }
            if(Objects.equals(name, "Teleport1")){
                a = new Teleport(null);;
                return a;
            }
            if(Objects.equals(name, "exit door")){
                return new Door("exit door", Door.Orientation.VERTICAL);
            }
            if(Objects.equals(name, "alien mother")){
            //   return new AlienMother( new RandomlyMoving());
            }
            if(Objects.equals(name, "wall")){
             //  return new Wall();
            }
            if(Objects.equals(name, "energy")){
                return new Energy();
            }
            if(Objects.equals(name, "ammo")){
                return new Ammo();
            }
            if(Objects.equals(name, "alien")){

            //    return new Alien(100, new RandomlyMoving());
            }
            if(Objects.equals(name, "ellen")){
                return new Ripley();
            }
            if(Objects.equals(name, "locker")){
                return new Locker();
            }
            if(Objects.equals(name, "ventilator")){
                return new Ventilator();
            }
            return null;
        }
    }
    @Override
    public void sceneCreated(@NotNull Scene scene) {
        final Topic<Alien> ACTOR_ADDED_TOPIC = Topic.create("alien", Alien.class);
    }
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);
        Disposable w = scene.getInput().registerListener(new MovableController(ellen));
        Disposable a = scene.getInput().registerListener(new KeeperController(ellen));
        Disposable c = scene.getInput().registerListener(new ShooterController(ellen));

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> w.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> a.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> c.dispose());

    }
    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley= scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        ripley.showRipleyState();
    }

}
