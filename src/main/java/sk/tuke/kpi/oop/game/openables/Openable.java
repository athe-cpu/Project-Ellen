package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public interface Openable extends Actor {
    /**
     *vráti true, ak je predmet (aktér) otvorený, v opačnom prípade vráti false
     */
    boolean isOpen();
    /**
     * zatvorenie predmetu (aktéra)
     */
    void close();
    /**
    *otvorenie predmetu (aktéra)
     */
    void open();
}
