package Chapter20.tankgame01;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
 * @date
 * @content
 *  1.防止敌人坦克重叠运动
 *  2.记录玩家的成绩，存盘退出
 *  3.记录当时的敌人坦克坐标，存盘退出
 *  4.玩游戏时，可以选择时重新开始游戏还是继续上局游戏
 */
public class HspTankGame01 extends JFrame {


    //定义MyPanel
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {


        HspTankGame01 hspTankGame01 = new HspTankGame01();

    }



    public HspTankGame01() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("请输入选择 1：新游戏 2：继续上局");
        Integer key = scanner.nextInt();
        mp = new MyPanel(key);
        //将mp放入到Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);

        this.setSize(1300,750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //JFrame中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Recorder.keepRecord();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
    }
}
