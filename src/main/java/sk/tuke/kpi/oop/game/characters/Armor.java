package sk.tuke.kpi.oop.game.characters;

public class Armor {
    private int st;
    private final int max;

    public Armor(int st, int max){
        this.st=st;
        this.max=max;
    }
    public Armor(int st){
        this.st=st;
        this.max=st;
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
        }
    }
    public void drain(int amount){
        if (st !=0) {
            st -= amount;
            if(st<=0) {
                st = 0;
            }
        }
    }
}
