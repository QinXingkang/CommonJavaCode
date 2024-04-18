package Chapter20.tankgame01;

/*
 * @date
 * @content
 */
public class Tank {

    private int x;//坦克的横坐标
    private int y;//坦克的纵坐标
    private int direction;//坦克方向 0上 1右 2下 3左

    private int speed = 1;
    boolean isLive = true;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //上右下左移动方法
    public void moveUp(){
        y -= speed;
    }

    public void moveRight(){
        x += speed;
    }

    public void moveDown(){
        y += speed;
    }

    public void moveLeft(){
        x -= speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
