package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
  private final Set<Input.Key> keys = new HashSet<>();
    private final Movable actor;
    private Disposable dx;
    private final Set<Input.Key> set = new HashSet<>();
    private Move<Movable> step = null;

    private final Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.DOWN, Direction.SOUTH)
    );
    public void checkDirection(Direction direction){
        if (direction!=null) {
            step = new Move<>(direction, Float.MAX_VALUE);
            dx =  step.scheduleFor(actor);
        }
    }
    public boolean check(Input.Key key){
        if(key==Input.Key.S||key==Input.Key.F||key==Input.Key.B||key==Input.Key.BACKSPACE||key==Input.Key.ENTER ||key==Input.Key.U||key==Input.Key.D||key==Input.Key.SPACE){
            return true;
        }
        return false;
    }
    @Override
    public void keyPressed(@NotNull Input.Key key) {

       if( check(key)){
           return;
       }
        Direction Path = null;
        int a = 0;
            keys.add(key);
            set.add(key);
        for (Input.Key lock:keys) {
                if (a==0 ){
                    Path = pressedA(lock);
                } else if (a==1){
                    Path = pressed(Path,lock);
                }
                a++;
            }
            if(step != null){
                step.stop();
                dx.dispose();
                step=null;
            }
            checkDirection(Path);
    }
    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if( check(key)){
            return;
        }
        Direction Path = null;
        int a = 0;
        keys.remove(key);
        set.remove(key);
            for (Input.Key lock:keys) {
                if (a==0 ){
                    Path = releasedA(lock);
                } else if (a==1){
                    Path = released(Path,lock);
                }
                a++;
            }
            if(step != null){
                step.stop();
                dx.dispose();
                step=null;
            }
            checkDirection(Path);
    }

    public MovableController(Movable actor) {
        this.actor = actor;
    }
    public Direction releasedA(Input.Key lock) {
        if(keyDirectionMap.get(lock)==Direction.NORTH){
            return Direction.NORTH;
        }
        if(keyDirectionMap.get(lock)==Direction.SOUTH){
            return Direction.SOUTH;        }
        if(keyDirectionMap.get(lock)==Direction.EAST){
            return Direction.EAST;
        }
        if(keyDirectionMap.get(lock)==Direction.WEST){
            return Direction.WEST;
        }
        return null;
    }
    public Direction released(Direction Path,Input.Key lock) {
        if(keyDirectionMap.get(lock)==Direction.NORTH){
            assert Path != null;
            return Path.combine(Direction.NORTH);
        }
        if(keyDirectionMap.get(lock)==Direction.SOUTH){
            assert Path != null;
            return Path.combine(Direction.SOUTH);       }
        if(keyDirectionMap.get(lock)==Direction.EAST){
            assert Path != null;
            return Path.combine(Direction.EAST);
        }
        if(keyDirectionMap.get(lock)==Direction.WEST){
            assert Path != null;
            return Path.combine(Direction.WEST);
        }
        return null;
    }
    public Direction pressedA(Input.Key lock) {
        if(keyDirectionMap.get(lock)==Direction.NORTH){
            return Direction.NORTH;
        }
        if(keyDirectionMap.get(lock)==Direction.SOUTH){
            return Direction.SOUTH;        }
        if(keyDirectionMap.get(lock)==Direction.EAST){
            return Direction.EAST;
        }
        if(keyDirectionMap.get(lock)==Direction.WEST){
            return Direction.WEST;
        }
        return null;
    }
    public Direction pressed(Direction Path,Input.Key lock) {
        if(keyDirectionMap.get(lock)==Direction.NORTH){
            assert Path != null;
            return Path.combine(Direction.NORTH);
        }
        if(keyDirectionMap.get(lock)==Direction.SOUTH){
            assert Path != null;
            return Path.combine(Direction.SOUTH);       }
        if(keyDirectionMap.get(lock)==Direction.EAST){
            assert Path != null;
            return Path.combine(Direction.EAST);
        }
        if(keyDirectionMap.get(lock)==Direction.WEST){
            assert Path != null;
            return Path.combine(Direction.WEST);
        }
        return null;
    }
}
