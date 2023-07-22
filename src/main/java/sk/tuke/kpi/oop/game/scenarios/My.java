package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Strategy.AlienAction;
import sk.tuke.kpi.oop.game.Teleport;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.Wall;
import sk.tuke.kpi.oop.game.behaviours.ChaseRipley;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.GuardAlien;
import sk.tuke.kpi.oop.game.characters.Monster;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.observer.AlienObservable;
import sk.tuke.kpi.oop.game.observer.Effect;

import sk.tuke.kpi.oop.game.openables.ClosedDoor;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.WinDoor;

import java.util.Objects;

public class My implements SceneListener {
    public static class Factory implements ActorFactory {
        private final Effect first = new Effect();

        Teleport teleport1;
        Teleport teleport2;
        Teleport teleport3;
        Door front;
        public Actor create(@Nullable String type, @Nullable String name){
            if(Objects.equals(name, "ellen")){
                return new Ripley();
            }
            if(Objects.equals(name, "front door")){
                front =new Door("front door", Door.Orientation.VERTICAL);
                return front;
            }
            if(Objects.equals(name, "door in")){
                return new ClosedDoor("door in",ClosedDoor.Orientation.HORIZONTAL);
            }
            if(Objects.equals(name, "door in V")){
                return new ClosedDoor("door in V",ClosedDoor.Orientation.VERTICAL);
            }
            if(Objects.equals(name, "back door")){
                return new Door("back door", Door.Orientation.HORIZONTAL);
            }

            if(Objects.equals(name, "Green card")){
                return new AccessCardGreen();
            }
            if(Objects.equals(name, "Teleport")){
                teleport3 = new Teleport(teleport1);
                return teleport3;
            }
            if(Objects.equals(name, "Teleport1")){
                teleport1 = new Teleport(teleport3);;
                return teleport1;
            }
            if(Objects.equals(name, "Teleport2")){
                teleport2 = new Teleport(teleport3);;
                return teleport2;
            }
            if(Objects.equals(name, "Card")){
                return new AccessCard();
            }
            if(Objects.equals(name, "exit door")){
                return new Door("exit door", Door.Orientation.VERTICAL);
            }
            if(Objects.equals(name, "alien mother")){
                return new Monster( 1000,new RandomlyMoving(),first);
               // return new AlienMother( 100,new RandomlyMoving());
            }
            if(Objects.equals(name, "wall")){
                return new Wall("wall", Wall.Orientation.VERTICAL);
            }
            if(Objects.equals(name, "wall horizontal")){
                return new Wall("wall horizontal", Wall.Orientation.HORIZONTAL);
            }
            if(Objects.equals(name, "energy")){
                return new Energy();
            }
            if(Objects.equals(name, "ammo")){
                return new Ammo();
            }
            if(Objects.equals(name, "alien")){
                return new AlienObservable(new RandomlyMoving(), 100,first);
            }
            if(Objects.equals(name, "alien1")){
                return new Alien( new ChaseRipley(),100);
            }
            if(Objects.equals(name, "locker")){
                return new Locker();
            }
            if(Objects.equals(name, "ventilator")){
                return new Ventilator();
            }
            if(Objects.equals(name, "win door")){
                return new WinDoor("win door", WinDoor.Orientation.HORIZONTAL);
            }
            if(Objects.equals(name, "Win card")){
                return new WinCard();
            }
            if(Objects.equals(name, "Guard")){
                return new GuardAlien(100, new RandomlyMoving()) {
                };
            }
            return null;
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        AlienAction change = new AlienAction();
        change.setActivity(scene.getFirstActorByType(Monster.class));

        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);
        scene.getGame().pushActorContainer(ellen.getBackpack());
        Disposable w = scene.getInput().registerListener(new MovableController(ellen));
        Disposable a = scene.getInput().registerListener(new KeeperController(ellen));
        Disposable c = scene.getInput().registerListener(new ShooterController(ellen));
        ellen.printText();
        Alien alien = scene.getFirstActorByType(Alien.class);
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> w.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> a.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> c.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> ellen.printDefeat());
        scene.getMessageBus().subscribe(ClosedDoor.DOOR_OPENED, (ClosedDoor) -> ellen.decreaseEnergy());

        scene.getMessageBus().subscribe(Monster.MONSTER_DIED, (Monster) -> ellen.stopPrintingText().dispose());
        scene.getMessageBus().subscribe(Monster.MONSTER_DIED, (Monster) -> w.dispose());
        scene.getMessageBus().subscribe(Monster.MONSTER_DIED, (Monster) -> a.dispose());
        scene.getMessageBus().subscribe(Monster.MONSTER_DIED, (Monster) -> c.dispose());
        scene.getMessageBus().subscribe(Monster.MONSTER_DIED, (Monster) -> ellen.printWictory());
        scene.getMessageBus().subscribe(WinDoor.DOOR_OPENED, (WinDoor) -> ellen.printWictory());
        scene.getMessageBus().subscribe(WinDoor.DOOR_OPENED, (WinDoor) -> w.dispose());
        scene.getMessageBus().subscribe(WinDoor.DOOR_OPENED, (WinDoor) -> a.dispose());
        scene.getMessageBus().subscribe(WinDoor.DOOR_OPENED, (WinDoor) -> c.dispose());
        scene.getMessageBus().subscribe(GuardAlien.GUARD_DIED, (GuardAlien) -> change.exucuteActivity());

        scene.getMessageBus().subscribe(WinDoor.DOOR_OPENED, (WinDoor) -> ellen.stopPrintingText().dispose());
        scene.getMessageBus().subscribe(WinDoor.DOOR_OPENED, (WinDoor) -> alien.stopDecreasingEnergy().dispose());

    }
    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley= scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        ripley.showRipleyState();
        Monster m=scene.getFirstActorByType(Monster.class);
        if(m!=null){
            m.showMonsterState();
        }
    }
}
