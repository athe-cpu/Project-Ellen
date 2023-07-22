package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.function.Predicate;

public class Observing <C extends Actor, X> implements Behaviour<C> {

    private final Topic<X> topic;
    private final Behaviour<C> delegate;
    private final Predicate<X> predicate;

    @Override
    public void setUp(C user) {
        if(isFailed(user)){
            return;
        }

        Scene scene = user.getScene();

        assert scene != null;
        scene.getMessageBus().subscribe(topic, action -> {if (predicate.test(action)) {delegate.setUp(user);}});
    }
    public Disposable stop(){
return null;
    }
   public boolean isFailed(C user){
       if (user ==null) {
           return true;
       }
       return false;
    }
    public Observing(Topic<X> topic, Predicate<X> predicate, Behaviour<C> delegate) {
        this.topic=topic;
        this.predicate=predicate;
        this.delegate=delegate;
    }

}
