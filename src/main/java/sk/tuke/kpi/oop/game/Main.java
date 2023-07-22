package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.gamelib.inspector.InspectableScene;
import sk.tuke.kpi.oop.game.scenarios.DevicesofLifeandDeath;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;
import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;
import sk.tuke.kpi.oop.game.scenarios.My;

import java.util.List;

public class Main {
    public static void main (String[] args){
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());

        //Scene scene = new World("world","maps/mission-impossible.tmx");
       // Scene missionImpossible = new World("mission-impossible", "maps/escape-room.tmx", new DevicesofLifeandDeath.Factory());
        Scene missionImpossible = new World("my", "maps/map.tmx", new My.Factory());
      //  Scene missionImpossible = new World("mission-impossible", "maps/mission-impossible.tmx", new MissionImpossible.Factory());

        //Scene scene = new InspectableScene(new World("mission-impossible"), List.of("sk.tuke.kpi"));

        game.addScene(missionImpossible);
       // MissionImpossible missionImpossibl = new MissionImpossible();
        My my = new My();
        //FirstSteps Step = new FirstSteps();
        missionImpossible.addListener(my);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();

    }

}

