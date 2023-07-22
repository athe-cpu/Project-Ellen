package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class MissionImpossible implements SceneListener {
    public static class Factory implements ActorFactory {
       public Actor create(@Nullable String type, @Nullable String name){
           if(Objects.equals(name, "front door")){
            //   return new Door();
           }
           if(Objects.equals(name, "back door")){
             //  return new Door("back door", Door.Orientation.HORIZONTAL);
           }
           if(Objects.equals(name, "exit door")){
          //     return new Door();
           }
           if(Objects.equals(name, "alien mother")){
              // return new AlienMother( new RandomlyMoving());
           }
           if(Objects.equals(name, "energy")){
               return new Energy();
           }
           if(Objects.equals(name, "ammo")){
               return new Ammo();
           }
           if(Objects.equals(name, "alien")){

               //return new Alien( new RandomlyMoving());
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

        Ripley ellen= scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        scene.follow(ellen);

        Disposable movableCon=scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperCon=scene.getInput().registerListener(new KeeperController(ellen));
        Disposable c = scene.getInput().registerListener(new ShooterController(ellen));
        Hammer hammer= new Hammer();
        ellen.getBackpack().add(hammer);
        scene.getGame().pushActorContainer(ellen.getBackpack());


       // scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) -> ellen.decreaseEnergy());

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movableCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeperCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> c.dispose());

        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ellen.stopDecreasingEnergy().dispose());

    }
    @Override
    public void sceneUpdating(@NotNull Scene scene) {
       // Ripley ripley= scene.getFirstActorByType(Ripley.class);
        //assert ripley != null;
       // ripley.showRipleyState();
    }

}

