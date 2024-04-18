package Chapter20.tankgame01;

import java.util.Vector;

/*
 * @date
 * @content
 * 敌人的坦克
 */
public class EnemyTank extends Tank implements Runnable{
    //1.在敌人坦克类，使用Vector保存多个shoot
    Vector<Shoot> shoots = new Vector<>();
    //增加一个成员，EnemyTank 可以得到敌人坦克的Vector
    //1.Vector<EnemyTank>
    Vector<EnemyTank> enemyTanks = new Vector<>();

    //这里提供一个方法，可以将MyPanel对象的  Vector<EnemyTank> enemyTanks = new Vector<>();
    //设置到EnemyTank 的成员enemyTanks
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法，判断当前的这个敌人坦克，是否和 enemyTanks中的其他坦克发生重叠或碰撞
    public boolean isTouchEnemyTank(){
        //判断当前敌人tank（this）方向
        switch (this.getDirection()){//上0右1下2左3
            case 0:
                //让当前this敌人坦克和其他所有的敌人坦克进行比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this){
                        //this坦克是上
                        //此时this的坦克的第一坐标 (getX(),getY())
                        // 此时this的坦克的第二坐标(getX() + 40,getY())
                        //分为两种情况，
                        // ①一种是上下
                        //  此时敌人的坦克的坐标范围 (enemyTank.getX(),enemyTank.getX() + 40)
                        //                    (enemyTank.getY() + ,enemyTank.getY() + 60)
                        if(enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //此时this的坦克的第一坐标 (getX(),getY())
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                            // 此时this的坦克的第二坐标(getX() + 40,getY())
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                        }
                        //②一种是左右
                        //  此时敌人的坦克的坐标范围 (enemyTank.getX(),enemyTank.getX() + 60)
                        //                    (enemyTank.getY() + ,enemyTank.getY() + 40)
                        if(enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //此时this的坦克的第一坐标 (getX(),getY())
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                            // 此时this的坦克的第二坐标(getX() + 40,getY())
                            if(this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                        }

                    }
                }
                break;
            case 1:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this){
                        //this坦克是右
                        //此时this的坦克的第一坐标 (getX() + 60,getY())
                        // 此时this的坦克的第二坐标(getX() + 60,getY() + 40)

                        //分为两种情况，
                        // ①一种是上下
                        //  此时敌人的坦克的坐标范围 (enemyTank.getX(),enemyTank.getX() + 40)
                        //                    (enemyTank.getY() + ,enemyTank.getY() + 60)
                        if(enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //此时this的坦克的第一坐标 (getX() + 60,getY())
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60<= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                            if (this.getX() + 60 >= enemyTank.getX()
                                    // 此时this的坦克的第二坐标(getX() + 60,getY() + 40)
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60){
                                return true;
                            }
                        }
                        //②一种是左右
                        //  此时敌人的坦克的坐标范围 (enemyTank.getX(),enemyTank.getX() + 60)
                        //                    (enemyTank.getY() + ,enemyTank.getY() + 40)
                        if(enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //此时this的坦克的第一坐标 (getX() + 60,getY())
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                            // 此时this的坦克的第二坐标(getX() + 60,getY() + 40)
                            if(this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40){
                                return true;
                            }
                        }

                    }
                }
                break;
            case 2:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this){
                        //this坦克是下
                        //此时this的坦克的第一坐标 (getX() + 40,getY() + 60)
                        // 此时this的坦克的第二坐标(getX() ,getY() + 60)

                        //分为两种情况，
                        // ①一种是上下
                        //  此时敌人的坦克的坐标范围 (enemyTank.getX(),enemyTank.getX() + 40)
                        //                    (enemyTank.getY() + ,enemyTank.getY() + 60)
                        if(enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //此时this的坦克的第一坐标 (getX() + 40,getY() + 60)
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40  <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60<= enemyTank.getY() + 60){
                                return true;
                            }
                            // 此时this的坦克的第二坐标(getX() ,getY() + 60)
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60){
                                return true;
                            }
                        }
                        //②一种是左右
                        //  此时敌人的坦克的坐标范围 (enemyTank.getX(),enemyTank.getX() + 60)
                        //                    (enemyTank.getY() + ,enemyTank.getY() + 40)
                        if(enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //此时this的坦克的第一坐标 (getX() + 40,getY() + 60)
                            if(this.getX()+ 40  >= enemyTank.getX()
                                    && this.getX()+ 40  <= enemyTank.getX() + 60
                                    && this.getY() + 60>= enemyTank.getY()
                                    && this.getY() + 60<= enemyTank.getY() + 40){
                                return true;
                            }
                            // 此时this的坦克的第二坐标(getX() ,getY() + 60)
                            if(this.getX()  >= enemyTank.getX()
                                    && this.getX()  <= enemyTank.getX() + 60
                                    && this.getY() + 60>= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40){
                                return true;
                            }
                        }

                    }
                }
                break;
            case 3:
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector中取出一辆敌人的坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比较
                    if (enemyTank != this){
                        //this坦克是左
                        //此时this的坦克的第一坐标 (getX() ,getY() + 40)
                        // 此时this的坦克的第二坐标(getX() ,getY() )

                        //分为两种情况，
                        // ①一种是上下
                        //  此时敌人的坦克的坐标 (enemyTank.getX(),enemyTank.getX() + 40)
                        //                    (enemyTank.getY(),enemyTank.getY() + 60)
                        if(enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2){
                            //此时this的坦克的第一坐标 (getX() ,getY() + 40)
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40<= enemyTank.getY() + 60){
                                return true;
                            }
                            // 此时this的坦克的第二坐标(getX() ,getY() )
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60){
                                return true;
                            }
                        }
                        //②一种是左右
                        //  此时敌人的坦克的坐标 (enemyTank.getX(),enemyTank.getX() + 60)
                        //                    (enemyTank.getY(),enemyTank.getY() + 40)
                        if(enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3){
                            //此时this的坦克的第一坐标 (getX() ,getY() + 40)
                            if(this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40>= enemyTank.getY()
                                    && this.getY() + 40<= enemyTank.getY() + 40){
                                return true;
                            }
                            // 此时this的坦克的第二坐标(getX() ,getY() )
                            if(this.getX()  >= enemyTank.getX()
                                    && this.getX()  <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40){
                                return true;
                            }
                        }

                    }
                }
                break;
        }
        return false;
    }

    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true){
            Shoot s = null;

            //这里我们判断如果shoots size（） == 0,则创建一颗子弹，放入到shoots集合，并启动
            if(isLive && shoots.size() == 0){//控制敌人坦克的子弹数
                //判断坦克的方向，创建对应的子弹
                switch (getDirection()){
                    case 0:
                        s = new Shoot(getX() + 20 ,getY() ,0);
                        break;
                    case 1:
                        s = new Shoot(getX() + 60 ,getY() + 20 ,1);
                        break;
                    case 2:
                        s = new Shoot(getX() + 20 ,getY() + 60,2);
                        break;
                    case 3:
                        s = new Shoot(getX() ,getY() + 20,3);
                        break;
                }

                shoots.add(s);
                new Thread(s).start();
            }


            //根据坦克的方向来继续移动
            switch (getDirection()){
                case 0://向上
                    //让坦克保持一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        if(getY() > 0 && !isTouchEnemyTank()){
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1://向右
                    for (int i = 0; i < 30; i++) {
                        if(getX() + 60 < 1000 && !isTouchEnemyTank()){
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2://向下
                    for (int i = 0; i < 30; i++) {
                        if(getY() + 60 < 750 && !isTouchEnemyTank()){
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3://向左
                    for (int i = 0; i < 30; i++) {
                        if(getX() > 0 && !isTouchEnemyTank()){
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
            //使用休眠来使坦克的移动更加自然
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //然后随机的改变方向
            setDirection((int)(Math.random() * 4));

            //坦克退出线程
            if(!isLive){
                break;
            }
        }
    }
}
