package Chapter20.tankgame01;

/*
 * @date
 * @content
 *  射击子弹
 */
public class Shoot implements Runnable{
    int x;//子弹的x坐标
    int y;//子弹的y坐标
    int direction = 0;//子弹的方向
    int speed = 5;//子弹的速度
    boolean isLive = true;//子弹是否存活

    public Shoot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void run() {//射击行为，不断改变并且重绘子弹的坐标
        while (true){
            //为了出现子弹移动的效果，需要子弹有休眠的时间
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //根据方向改变x，y的坐标
            switch(direction){// 0:向上 1：向右 2：向下 3：向左
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            //测试命令
//            System.out.println("子弹 x=" + x + " y=" + y);
            //当子弹移动到面板的边界时，就应该销毁（把启动的子弹的线程销毁）
            //增加条件，当子弹击中敌方坦克的时候也销毁
            if(!(x >= 0 && x <= 1000 && y >= 0 && y <=750 && isLive)){
                isLive = false;
                break;
            }
        }

    }
}
