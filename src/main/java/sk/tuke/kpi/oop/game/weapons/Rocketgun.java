package sk.tuke.kpi.oop.game.weapons;

public class Rocketgun extends Firearm {
    public Rocketgun(int st, int max) {
        super(st, max);
    }

    @Override
    protected Fireable createBullet() {
        return new Rocket();
    }

}
