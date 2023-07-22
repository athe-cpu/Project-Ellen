package sk.tuke.kpi.oop.game.Strategy;

public class AlienAction {
    private Activity activity;
    public void setActivity(Activity activity){
        this.activity=activity;
    }
    public void exucuteActivity(){
        activity.doIt();
    }
}
