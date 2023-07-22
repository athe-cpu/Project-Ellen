package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm {
    public Gun(int st, int max) {
        super(st,max);
    }
    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}
