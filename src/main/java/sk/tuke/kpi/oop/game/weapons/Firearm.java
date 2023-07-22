package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int ammo;
    private final int max;
    public Firearm(int st, int max) {
        this.ammo = st;
        this.max=max;
    }

    public Firearm(int st) {
        this.ammo = st;
        this.max = st;
    }
    public int getMax(){
        return max;
    }
    public int getAmmo(){
        return ammo;
    }
    public void setAmmo(int Ammo){
       this.ammo=Ammo;
    }
    public void reload(int newAmmo){
        this.ammo+=newAmmo;
        if(ammo>max){
            this.ammo=max;
        }
    }
    public Fireable fire() {
        if(ammo==0){
            return null;
        }
        if(ammo>0){
            ammo--;
        }
        return createBullet();
    }
    protected abstract Fireable createBullet();


}
