package Chapter20.tankgame01;

import java.util.Vector;

/*
 * @date
 * @content
 */
public class Hero extends Tank {
    //定义一个Shoot类,表示一个射击（线程）
    Shoot shoot = null;

    //可以发射多颗子弹
    Vector<Shoot> shoots = new Vector<>();

    public Hero(int x, int y) {
        super(x,y);
    }

    //射击方法
    public void shootEnemyTank(){

        //发多颗子弹怎么班，控制在我们的面板上，最多只有5颗
        if(shoots.size() == 5){
            return ;
        }
        //创建Shot对象，根据当前Hero对象的位置和方向老创建Shoot
        switch(getDirection()){//得到Hero对象方向
            case 0://向上
                shoot = new Shoot(getX() + 18,getY() ,0);
                break;
            case 1://向右
                shoot = new Shoot(getX() + 60,getY() + 18,1);
                break;
            case 2://向下
                shoot = new Shoot(getX() + 18, getY() + 60,2);
                break;
            case 3://向左
                shoot = new Shoot(getX(), getY() + 18,3);
                break;
        }
        //把新创建的shoot放入到集合中
        shoots.add(shoot);
        //启动Shoot线程
        new Thread(shoot).start();
    }
}
