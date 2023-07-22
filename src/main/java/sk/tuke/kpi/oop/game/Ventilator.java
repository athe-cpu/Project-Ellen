package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.Objects;


public class Ventilator extends AbstractActor implements Repairable {
    private boolean is_repair;
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);

    public Ventilator() {
        setAnimation(new Animation("sprites/ventilator.png", 32, 32,  0.1f, Animation.PlayMode.LOOP_PINGPONG));
        damageVentilator();
    }

    private void damageVentilator() {
        is_repair = true;
        getAnimation().stop();
    }

    @Override
    public boolean repair() {
        if (is_repair) {
            getAnimation().play();
            is_repair = false;
            Objects.requireNonNull(getScene()).getMessageBus().publish(VENTILATOR_REPAIRED,this);
            return true;
        }
            return false;
    }

}
