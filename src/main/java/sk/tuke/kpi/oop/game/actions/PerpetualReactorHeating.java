package sk.tuke.kpi.oop.game.actions;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    private final int high;
    public PerpetualReactorHeating(int hig) {
        this.high=hig;
    }
    @Override
    public void execute(float deltaTime) {
        Reactor reactor = getActor();
        if (reactor!=null) {
            reactor.increaseTemperature(this.high);
        }
    }
}
