package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.items.Hammer;

import java.util.Map;

public class TrainingGameplay implements SceneListener {

      // vytvorenie instancie reaktora

    public void setupPlay(@NotNull Scene scene) {
        Reactor reactor = new Reactor();
        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        MapMarker reactorArea1 = markers.get("reactor-area-1");
        scene.addActor(reactor, reactorArea1.getPosX(), reactorArea1.getPosY());
       // scene.addActor(reactor, 200, 200);  // pridanie reaktora do sceny na poziciu [x=64, y=64]
       // reactor.turnOn();
        Cooler cool = new Cooler(reactor);
        Cooler cool1 = new Cooler(reactor);
        MapMarker coolerArea2 = markers.get("cooler-area-2");
        MapMarker coolerArea3 = markers.get("cooler-area-3");
        scene.addActor(cool, coolerArea2.getPosX(), coolerArea2.getPosY());
        scene.addActor(cool1, coolerArea3.getPosX(), coolerArea3.getPosY());
        //scene.addActor(cool, 100, 100);
        new ActionSequence<>(
            new Wait<>(30),
            new Invoke<>(cool::turnOn)
        ).scheduleFor(cool);
        Hammer hammer = new Hammer(1);
        scene.addActor(hammer, 150, 100);
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(reactor::repair)
        ).scheduleFor(reactor);
        Light light = new Light();
        scene.addActor(light, 200, 250);
        Teleport tel = new Teleport (null);
        scene.addActor(tel, 117, 117);
        Teleport tel1 = new Teleport (tel);
        scene.addActor(tel1, 250, 106);
        ChainBomb a = new ChainBomb(10);
        scene.addActor(a, 50, 50);
        ChainBomb a1 = new ChainBomb(5);
        scene.addActor(a1, 100, 50);
        ChainBomb a2 = new ChainBomb(7);
        scene.addActor(a2, 150, 50);
    }

}
