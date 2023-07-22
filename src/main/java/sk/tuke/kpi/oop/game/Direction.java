package sk.tuke.kpi.oop.game;

public enum Direction {
    EAST (1, 0),
    NORTH (0, 1),
    WEST (-1, 0),
    SOUTH (0, -1),
    SOUTHEAST(1, -1),
    NORTHEAST(1, 1),
    NORTHWEST(-1, 1),
    SOUTHWEST(-1, -1),
    NONE(0, 0);

    private float Angle;
    private final int dx;
    private final int dy;

    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }
    public float getAngle() {
        if(dx==1&&dy==0){Angle= 270;}
        if(dx==0&&dy==1){Angle = 0;}
        if(dx==-1&&dy==0){Angle = 90;}
        if(dx==0&&dy==-1){Angle = 180;}
        getAngle1();
        return Angle;
    }
    public void getAngle1(){
        if(dx==1&&dy==-1){Angle = 225;}
        if(dx==1&&dy==1){Angle = 315;}
        if(dx==-1&&dy==1){Angle = 45;}
        if(dx==-1&&dy==-1){Angle = 135;}
    }
    private Direction combineAngle(int dx, int dy){
        if(dx==1&&dy==0) {
            return EAST;
        }else if(dx==0&&dy==1){
            return NORTH;
        }else if(dx==-1&&dy==0){
            return WEST;
        }else if(dx==0&&dy==-1){
            return SOUTH;
        }
       return combineAngle1( dx,  dy);
    }
    private Direction combineAngle1(int dx, int dy){
        if(dx==1&&dy==-1){
            return SOUTHEAST;
        }else if(dx==1&&dy==1){
            return NORTHEAST;
        }
        return NONE;
    }
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
    public static Direction fromAngle(float gradient) {
        if (gradient == 135) return SOUTHWEST;
        if (gradient == 270) return EAST;
        if (gradient == 0) return NORTH;
        if (gradient == 180) return SOUTH;
        if (gradient == 45) return NORTHWEST;
        if (gradient == 90) return WEST;
        if (gradient == 225) return SOUTHEAST;
        return NORTHEAST;
    }


    public Direction combine(Direction side) {
        if (this==side) {
            return this;
        }
        int X,Y;
        if (getDx()==side.getDx()) {
            X = getDx();
        } else {
            X=getDx()+side.getDx();
        }
        if (getDy()==side.getDy()) {
            Y = getDy();
        } else {
            Y=getDy()+side.getDy();
        }
        if(X==-1&&Y==1){
            return NORTHWEST;
        }else if(X==-1&&Y==-1){
            return SOUTHWEST;
        }
        return combineAngle(X,Y);
    }
}
