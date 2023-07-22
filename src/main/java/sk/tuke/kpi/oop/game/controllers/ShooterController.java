package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.actions.FireRocket;
import sk.tuke.kpi.oop.game.characters.Armed;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShooterController implements KeyboardListener {
    private final Armed rifle;
    private final Set<Input.Key> fireKey = new HashSet<>();
    public ShooterController(Armed rifle) {
        this.rifle = rifle;
    }

    private final Map<Input.Key, Object> keyActionMap = Map.ofEntries(
            Map.entry(Input.Key.SPACE, Input.Key.SPACE),
            Map.entry(Input.Key.F, Input.Key.F)
        );

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if(key==Input.Key.S||key==Input.Key.BACKSPACE||key==Input.Key.ENTER ||key==Input.Key.U||key==Input.Key.D){
            return;
        }
        fireKey.add(key);
        for (Input.Key lock:fireKey) {
            if(keyActionMap.get(lock)==Input.Key.SPACE){
                new Fire<>().scheduleFor(rifle);
            }
            if(keyActionMap.get(lock)==Input.Key.F){
                new FireRocket<>().scheduleFor(rifle);
            }
        }
    }
    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if(key==Input.Key.S||key==Input.Key.BACKSPACE||key==Input.Key.ENTER ||key==Input.Key.U||key==Input.Key.D){
            return;
        }

        fireKey.remove(key);
    }
}
