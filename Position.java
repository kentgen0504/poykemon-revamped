public class Position {
    private int x;
    private int y;
    private int[] xy;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
        this.xy = new int[]{x,y};
    }

    public Position(int[] xy){
        this.x = xy[0];
        this.y = xy[1];
        this.xy = xy;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getX(){
        return this.x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getY(){
        return this.y;
    }

    public void setXY(int x, int y){
        this.xy = new int[]{x, y};
    }

    public int[] getXY(){
        return this.xy;
    }

    public void moveX(int distance){
        this.x += distance;
    }

    public void moveY(int distance){
        this.y += distance;
    }

    public Position createCopy(){
        return new Position(this.x,this.y);
    }

    public boolean equals(Position position){
        return this.x == position.getX() && this.y == position.getY();
    }
}
