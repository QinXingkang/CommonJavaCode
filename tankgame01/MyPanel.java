package Chapter20.tankgame01;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/*
 * @date
 * @content
 * 坦克大战的绘图区域
 */

//为了监听键盘事件，实现KeyListener
//为了让Panel不停的重绘子弹，需要将MyPanel实现Runnable，当作一个线程使用
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;

    //定义敌人坦克，放入到Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node对象的Vector，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();

    //定义一个Vector，用于存放炸弹
    //当子弹击中坦克时，就加入Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();

    int enemyTankSize = 3;

    //定义三张炸弹图片，用于显式爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    public MyPanel(int key) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        //先判断记录文件是否存在
        //如果存在就正常执行，如果文件不存在，则提示只能开启新游戏，key = 1;
        File file = new File(Recorder.getRecordFile());
        if(file.exists()){
            nodes = Recorder.getNodesAndEnemyTankRec();
        }else {
            System.out.println("文件不存在，只能开启新的游戏");
            key = 1;
        }
        //将MyPanel对象的 enemyTanks 设置给Recorder 的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        hero = new Hero(500, 100);//初始化自己的坦克
        hero.setSpeed(5);

        switch (key){
            case 1 :
                //初始化敌人坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    //创建一个敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);

                    //将enemyTanks 设置给enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirection(2);
                    //启动敌人坦克线程
                    new Thread(enemyTank).start();
                    //给该enemyTank加入一颗子弹
                    Shoot shoot = new Shoot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    //加入enemyTank的Vector成员
                    enemyTank.shoots.add(shoot);
                    //启动shoot对象
                    new Thread(shoot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                }
                break;
            case 2 :
                //初始化敌人坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建一个敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());

                    //将enemyTanks 设置给enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirection(node.getDirect());
                    //启动敌人坦克线程
                    new Thread(enemyTank).start();
                    //给该enemyTank加入一颗子弹
                    Shoot shoot = new Shoot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    //加入enemyTank的Vector成员
                    enemyTank.shoots.add(shoot);
                    //启动shoot对象
                    new Thread(shoot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("输入有误 ");
        }



        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("bomb1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("bomb2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("bomb3.png"));

        //太吵了，需要的时候再开启
//        play("D:\\软件\\Idea\\JavaCode\\HspCode\\src\\Chapter20\\tankgame01\\music.wav");
    }

    //编写方法，显式我方击毁敌方坦克的信息
    public void showInfo(Graphics g){
        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体",Font.BOLD,25);
        g.setFont(font);

        g.drawString("累计击毁敌方坦克",1020,30);
        drawTank(1020,60,g,0,1);
        //重新把画笔设成黑色，因为在drawTank里设置过其他颜色
        g.setColor(Color.black);

        g.drawString(Recorder.getAllEnemyTankNum() + "",1080,100);
    }

    //用于播放.wav音频
    public void play(String string) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream;// 文件流
        AudioFormat audioFormat;// 文件格式
        SourceDataLine sourceDataLine;// 输出设备
        String fileName = string;
        File file = new File(fileName);


        // 取得文件输入流
        audioInputStream = AudioSystem.getAudioInputStream(file);
        audioFormat = audioInputStream.getFormat();
        // 转换文件编码
        if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    audioFormat.getSampleRate(), 16, audioFormat.getChannels(),
                    audioFormat.getChannels() * 2, audioFormat.getSampleRate(),
                    false);
            audioInputStream = AudioSystem.getAudioInputStream(audioFormat,
                    audioInputStream);
        }

        // 打开输出设备
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
                audioFormat, AudioSystem.NOT_SPECIFIED);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat); // 打开具有指定格式的行，这样可以使行获得所有所需的系统资源并变得可操作
        sourceDataLine.start();  // 允许某一数据行执行数据I/O

        byte tempBuffer[] = new byte[320];
        try {
            int cnt;
            // 读取数据到缓存区
            // 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
            // return: 读入缓冲区的总字节数；如果因为已经到达流末尾而不再有更多数据，则返回-1
            while ((cnt = audioInputStream.read(tempBuffer, 0,
                    tempBuffer.length)) != -1) {
                if (cnt > 0) {
                    // 写入缓存数据
                    sourceDataLine.write(tempBuffer, 0, cnt); // 通过此源数据行将音频数据写入混频器
                }
            }
            // Block等待临时数据被输出为空
            // 通过在清空数据行的内部缓冲区之前继续数据I/O，排空数据行中的列队数据
            sourceDataLine.drain();
            // 关闭行，指示可以释放的该行使用的所有系统资源。如果此操作成功，则将行标记为 closed，并给行的侦听器指派一个 CLOSE 事件。
            sourceDataLine.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形，默认黑色
        showInfo(g);

        //只有初始化了hero并且hero还存活的时候才画出我方坦克ssss
        if(hero != null && hero.isLive){
            //画出自己的坦克，封装一个方法
            drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 0);
        }




//        //画出hero射击的子弹
//        if (hero.shoot != null && hero.shoot.isLive == true) {
//            g.fill3DRect(hero.shoot.x, hero.shoot.y, 3, 3, false);
//        }

        //升级版，将hero的子弹集合shoots遍历取出再绘制
        for (int i = 0; i < hero.shoots.size(); i++) {
            Shoot shoot = hero.shoots.get(i);
            if(shoot != null && shoot.isLive == true){
                g.draw3DRect(shoot.x ,shoot.y, 3, 3,false);
            }else{
                //如果该shoot对象已经无效，就从shoots集合中拿掉
                hero.shoots.remove(shoot);
            }
        }

        //如果bombs集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if(bomb.life > 6){
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
            }else if(bomb.life > 3){
                g.drawImage(image2,bomb.x,bomb.y,60,60,this);
            }else {
                g.drawImage(image3,bomb.x,bomb.y,60,60,this);
            }
            //让这个炸弹的生命值减少
            bomb.lifeDown();
            //如果bomb life 为 0，就从bombs的集合中删除
            if(bomb.life == 0){
                bombs.remove(bomb);
            }
        }

        //画出敌人的坦克，遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //从Vector取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //当前坦克是否还存活
            if (enemyTank.isLive) {//因为要考虑敌方坦克是否被击中，而被击中的坦克不需要再被画出来
                //所以开画之前，需要判断一下坦克是否存活
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
                //画出enemyTank所有子弹
                for (int j = 0; j < enemyTank.shoots.size(); j++) {
                    //取出子弹
                    Shoot shoot = enemyTank.shoots.get(j);
                    //绘制
                    if (shoot.isLive) {
                        g.draw3DRect(shoot.x, shoot.y, 3, 3, false);
                    } else {
                        //从Vector移除
                        enemyTank.shoots.remove(shoot);
                    }
                }
            }
//        drawTank(hero.getX(),hero.getY(),g,0,0);
//        g.setColor(Color.blue);
//        g.fillRect(100,100,10,60);
//        g.fillRect(110,110,20,40);
//        g.fillRect(130,100,10,60);
//        g.setColor(Color.red);
//        g.fillOval(113,125,15,15);
//        g.fillRect(119,105,4,25);
        }
    }

    /*
        x 坦克左上角x坐标
        y 坦克的左上角y坐标
        g 画笔
        direction 坦克方向
        type 坦克类型（敌我）
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        //根据不同类型坦克，设置不同颜色
        switch (type) {
            case 0://我们的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }


        //根据坦克的方向来绘制对应形状的坦克
        /*
            0:向上
            1：向右
            2：向下
            3：向左
         */
        switch (direction) {
            case 0://0 表示向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出中间的身子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fillOval(x + 12, y + 25, 15, 15);//画出坦克的炮台
                g.fill3DRect(x + 18, y + 5, 4, 25, false);//画出坦克的炮管

                break;
            case 1://1 表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出中间的身子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克右边的轮子
                g.fillOval(x + 23, y + 12, 15, 15);//画出坦克的炮台
                g.fill3DRect(x + 30, y + 18, 25, 4, false);//画出坦克的炮管
                break;

            case 2://2 表示向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出中间的身子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fillOval(x + 12, y + 20, 15, 15);//画出坦克的炮台
                g.fill3DRect(x + 18, y + 30, 4, 25, false);//画出坦克的炮管
                break;

            case 3://3 表示向左
                g.fill3DRect(x, y, 60, 10, false);//画出坦克左边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出中间的身子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克右边的轮子
                g.fillOval(x + 20, y + 12, 15, 15);//画出坦克的炮台
                g.fill3DRect(x + 5, y + 18, 25, 4, false);//画出坦克的炮管
            default:
//                System.out.println("等待处理");
        }
    }

    //如果我们的坦克可以发射多颗子弹
    //在判断我方子弹是否击中敌人坦克时，就需要把我们的子弹集合中所有的子弹，都取出和敌人的所有坦克进行判断
    public void hitEnemyTank(){
        //遍历我们的子弹
        for (int j = 0; j < hero.shoots.size(); j++) {
            Shoot shoot = hero.shoots.get(j);
            //判断是否击中了敌人坦克
            if (shoot != null && shoot.isLive) {//当前子弹是否还存活
                //遍历敌人所有的坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);//判断此坦克是否被击中
                    hitTank(shoot, enemyTank);
                }
            }
        }


    }

    //编写方法，判断敌方子弹是否击中我方坦克
    public void hitHero(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemyTank 对象的所有子弹
            for (int j = 0; j < enemyTank.shoots.size(); j++) {
                //取出坦克子弹
                Shoot shoot = enemyTank.shoots .get(j);
                //判断shoot是否击中我方坦克
                if(hero.isLive && shoot.isLive){
                    hitTank(shoot,hero);
                }
            }
        }
    }


    //编写方法，判断我方子弹是否击中敌方坦克
    //什么时候判断 我方的子弹是否集中敌人坦克？ 因为不知道确切的时间，所以得用循环一直判断 run()；
    public void hitTank(Shoot s, Tank tank) {
        //判断s击中坦克
        switch (tank.getDirection()) {
            //坦克向上和向下情况相同
            case 0://向上
            case 2://向下
                if (s.x > tank.getX() && s.x < tank.getX() + 40
                        && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isLive = false;
                    tank.isLive = false;
                    //被击中的坦克，从集合中删去
                    enemyTanks.remove(tank);
                    //创建Bomb对象，加入到bombs集合中
                    //当我方击毁一个敌方坦克时，就对allEnemyTankNum++
                    //要判断一下，被击毁的坦克是否是敌方坦克
                    //因为tank可以是Hero也可以时EnemyTank
                    if(tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                //坦克向左和向右情况相同
            case 1://向右
            case 3://向左
                if (s.x > tank.getX() && s.x < tank.getX() + 60
                        && s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isLive = false;
                    tank.isLive = false;
                    //被击中的坦克，从集合中删去
                    enemyTanks.remove(tank);
                    //创建Bomb对象，加入到bombs集合中
                    if(tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理wasd 键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下W，改变坦克的方向
            hero.setDirection(0);
            //修改坦克的坐标 y -= 1;
            if(hero.getY() > 0){
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//按下A，改变坦克的方向
            hero.setDirection(3);
            if(hero.getX() > 0){
                hero.moveLeft();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//按下S，改变坦克的方向
            hero.setDirection(2);
            if(hero.getY() + 60 < 750){
                hero.moveDown();

            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//按下D，改变坦克的方向
            hero.setDirection(1);

            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        }
        //如果用户按下的时J，就发射炮弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //版本一：发射一颗子弹
            //第一次发射时，shoot为空 可以发射子弹
            //第二次发射子弹时，就不能单靠是否为空来发射，因为当第一次发射的子弹线程销毁后，shoot对象并不会再为null
            //这个时候就需要通过shoot.isLive 来判断是否能再次发射子弹

//            if(hero.shoot == null || !hero.shoot.isLive){
//                hero.shootEnemyTank();
//            }

            //版本二：发射多颗子弹
            hero.shootEnemyTank();

        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每个100毫秒重绘区域
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

//            for (int j = 0; j < hero.shoots.size(); j++) {
//                Shoot shoot = hero.shoots.get(j);
//                //判断是否击中了敌人坦克
//                if (shoot != null && shoot.isLive) {//当前子弹是否还存活
//                    //遍历敌人所有的坦克
//                    for (int i = 0; i < enemyTanks.size(); i++) {
//                        EnemyTank enemyTank = enemyTanks.get(i);//判断此坦克是否被集中
//                        hitTank(shoot, enemyTank);
//                    }
//                }
//            }

            //判断我方坦克是否击中对方坦克
            hitEnemyTank();

            //判断敌人坦克是否击中我方坦克
            hitHero();
            this.repaint();
        }

    }
}
