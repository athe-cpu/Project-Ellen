package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class KeeperController implements KeyboardListener {
    private final Keeper a;
    private final Set<Input.Key> actionKeys = new HashSet<>();
    public KeeperController(Keeper keeper){
       this.a=keeper;
    }

    private final Map<Input.Key, Object> keyActionMap;
    {
        assert false;
        keyActionMap = Map.ofEntries(
            Map.entry(Input.Key.BACKSPACE, Input.Key.BACKSPACE),
            Map.entry(Input.Key.ENTER, Input.Key.ENTER),
            Map.entry(Input.Key.S, Input.Key.S),
            Map.entry(Input.Key.U, Input.Key.U),
            Map.entry(Input.Key.B, Input.Key.B)
        );
    }
    public boolean check(Input.Key key){
        if(key==Input.Key.SPACE){
            return true;
        }
        return false;
    }
    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if( check(key)){
            return;
        }
        actionKeys.add(key);
        for (Input.Key lock:actionKeys) {
            if(keyActionMap.get(lock)==Input.Key.BACKSPACE){
                new Drop<>().scheduleFor(a);
            }
            if(keyActionMap.get(lock)==Input.Key.ENTER){
                new Take<>().scheduleFor(a);
            }
            if(keyActionMap.get(lock)==Input.Key.S){
                new Shift<>().scheduleFor(a);
            }
            if(keyActionMap.get(lock)==Input.Key.U){
                useU();
            }
            if(keyActionMap.get(lock)==Input.Key.B){
                useB();
            }
        }
    }
    public void useU() {
        Usable<?> usable=null;
        for(Actor b : Objects.requireNonNull(a.getScene()).getActors()){
            if ((a.intersects(b)) && (b instanceof  Usable)){
                usable = (Usable) b;
            }
        }
        if (usable != null) {
            new Use<>(usable).scheduleForIntersectingWith(a);
        }
    }
    public void useB() {
        if(a.getBackpack().peek() == null){
            return;
        }
        if (a.getBackpack().peek() instanceof Usable) {
            Use<?> use = new Use<>((Usable<?>) a.getBackpack().peek());
            use.scheduleForIntersectingWith(a);
        }
    }
    @Override
    public void keyReleased(@NotNull Input.Key key) {
        actionKeys.remove(key);
    }
}
