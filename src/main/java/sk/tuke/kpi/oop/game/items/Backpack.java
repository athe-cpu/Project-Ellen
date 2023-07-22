package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {
    private final String name;
    private final int capacity;
    private final List<Collectible> list = new ArrayList<>();
    public Backpack(String name, int capacity){
        this.name = name;
        this.capacity=capacity;
    }
    public int getCapacity(){
        return capacity;
    }
    public @NotNull List<Collectible> getContent(){
        return List.copyOf(list);
    }
    public @NotNull String getName(){
        return name;
    }
    public int getSize(){
        return list.size();
    }
    public void add(@NotNull Collectible item){
        if(capacity==list.size()){
            throw new IllegalStateException();
        }
        list.add(item);
    }
    public boolean isFailed(){
        if(list.isEmpty()){
            return true;
        }
        return false;
    }
    public void remove(@NotNull Collectible item){
        if(isFailed()){
           return;
        }
        list.remove(item);
    }

    public Iterator<Collectible> iterator(){
        return list.iterator();
    }
    public Collectible peek(){
        if(isFailed()){
            return null;
        }
            return list.get(list.size()-1);
    }
    public void shift() {
        if (!isFailed()) {
            Collections.rotate(list, 1);
        }
    }
}
