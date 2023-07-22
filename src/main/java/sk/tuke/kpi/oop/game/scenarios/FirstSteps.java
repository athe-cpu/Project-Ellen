package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;


public class FirstSteps implements SceneListener {
    private final Wrench wrench1 =new Wrench();
    private final Wrench wrench2 =new Wrench();
    private final Wrench wrench3 =new Wrench();
    private final Wrench wrench4 =new Wrench();
    private final Wrench wrench5 =new Wrench();
    private final Wrench wrench6 =new Wrench();
    private final Hammer hammer = new Hammer();
    private final Hammer hammer1 = new Hammer();
    private final Hammer hammer2 = new Hammer();
    private final FireExtinguisher fireExtinguisher = new FireExtinguisher();
    private final FireExtinguisher fireExtinguisher1 = new FireExtinguisher();
    private final FireExtinguisher fireExtinguisher2 = new FireExtinguisher();

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = new Ripley();
        scene.addActor(ripley, 0, 0);
        Energy energy = new Energy();
        scene.addActor(energy, 100, 10);
        Energy energy1 = new Energy();
        scene.addActor(energy1, 100, 200);
        Ammo ammo = new Ammo();
        scene.addActor(ammo, -100, 10);
        Ammo ammo1 = new Ammo();
        scene.addActor(ammo1, -100, 200);
        Ammo ammo2 = new Ammo();
        scene.addActor(ammo2, -200, 10);

        scene.addActor(hammer1, -1, 300);
        scene.addActor(fireExtinguisher1, -1, 250);
        scene.addActor(wrench1, -1, 200);
        ripley.getBackpack().add(hammer);
        ripley.getBackpack().add(fireExtinguisher);
        ripley.getBackpack().add(wrench2);
        ripley.getBackpack().add(hammer2);
        ripley.getBackpack().add(fireExtinguisher2);
        ripley.getBackpack().add(wrench3);
        ripley.getBackpack().add(wrench4);
        ripley.getBackpack().add(wrench5);
        ripley.getBackpack().add(wrench6);
        scene.getGame().pushActorContainer(ripley.getBackpack());
        ripley.getBackpack().shift();
        new When<>(
            () -> ripley.intersects(ammo),
            new Invoke<>(() -> ammo.useWith(ripley))
        ).scheduleFor(ripley);
        new When<>(
            () -> ripley.intersects(ammo1),
            new Invoke<>(() -> ammo1.useWith(ripley))
        ).scheduleFor(ripley);
        new When<>(
            () -> ripley.intersects(ammo2),
            new Invoke<>(() -> ammo2.useWith(ripley))
        ).scheduleFor(ripley);
        new When<>(
            () -> ripley.intersects(energy),
            new Invoke<>(() -> energy.useWith(ripley))
        ).scheduleFor(ripley);
        new When<>(
            () -> ripley.intersects(energy1),
            new Invoke<>(() -> energy1.useWith(ripley))
        ).scheduleFor(ripley);
        new When<>(
            () -> ripley.intersects(energy),
            new Invoke<>(() -> energy.useWith(ripley))
        ).scheduleFor(ripley);
        MovableController movableController = new MovableController(ripley);
        KeeperController keeperController = new KeeperController(ripley);
        scene.getInput().registerListener(movableController);
        scene.getInput().registerListener(keeperController);
    }
    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley= scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        ripley.showRipleyState();
    }
}
