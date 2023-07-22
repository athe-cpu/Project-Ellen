package sk.tuke.kpi.oop.game.characters;

import java.util.LinkedList;
import java.util.List;

public class Health {
    private int st;
    private final int max;
    private final List<ExhaustionEffect> effect;

    public Health(int st, int max){
        this.st=st;
        this.max=max;
        effect = new LinkedList<>();
    }
    public Health(int st){
        this.st=st;
        this.max=st;
        effect = new LinkedList<>();
    }
    public int getMax(){
        return this.max;
    }
    public int getValue(){
        return this.st;
    }
    public void setValue(int s){
        this.st=s;
    }
    public void refill(int amount){
        st += amount;
        if(st>max) {
            restore();
        }
    }
    public void restore(){
        st=max;
    }

    public void exhaust(){
        if (st != 0) {
            st = 0;
            for(ExhaustionEffect fg : effect){
                fg.apply();
            }
        }
    }
    public void drain(int amount){
        if (st !=0) {
                st -= amount;
            if(st<=0) {
                st = 0;
                for(ExhaustionEffect fg : effect){
                    fg.apply();
                }
            }
        }
    }
    public void onExhaustion(ExhaustionEffect effect){
            this.effect.add(effect);
    }
    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }
}
