package Chapter20.tankgame01;

import java.io.*;
import java.util.Vector;

public class Recorder {
    private static int allEnemyTankNum = 0;
    //定义IO对象
    private static BufferedWriter bufferedWriter = null;
    private static BufferedReader bufferedReader = null;

    //把记录文件保存到src下

    private static String recordFile = "D:\\JavaCode\\JavaCode_idea\\JavaCode\\HspCode\\src\\myRecord.txt";
    //定义Vector，指向MyPanel对象的敌人坦克Vector
    private static Vector<EnemyTank> enemyTanks = null;



    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //定义一个Node的Vector，用于保存敌人的信息
    private static Vector<Node> nodes = new Vector<>();

    //返回记录文件的路径
    public static String getRecordFile(){
        return recordFile;
    }

    //增加一个方法，用于读取文件recordFile恢复相关的信息
    //该方法，再继续上局的时候调用即可
    public static Vector<Node> getNodesAndEnemyTankRec() throws IOException {
        bufferedReader = new BufferedReader(new FileReader(recordFile));

        //先读取第一行的击毁数
        allEnemyTankNum = Integer.parseInt( bufferedReader.readLine());

        //再循环读取存活坦克的信息，生成nodes集合
        String line = "";
        while((line = bufferedReader.readLine()) != null){
            String[] s = line.split(" ");
            Node node = new Node(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
            nodes.add(node);//放入到nodes Vector
        }
        bufferedReader.close();
        return nodes;
    }
    
    //增加一个方法，当游戏退出时，我们将allEnemyTankNum保存到recordFile
    //对keepRecord进行升级，退出时保存敌人坦克的坐标和方向
    

    public static void keepRecord() throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(recordFile),"utf-8");
        bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(allEnemyTankNum  +"\r\n");
        //遍历敌人坦克的Vector，然后根据存活情况进行保存即可
        //OOP,定义一个属性，然后通过setXxx得到敌人坦克的Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人坦克
            //这里的判断可以省略，因为在Vector中都是存活的，被击中的坦克都remove了
            //但为了保险还是可以判断一下
            EnemyTank enemyTank = enemyTanks.get(i);
            if(enemyTank.isLive){
                //保存该enemyTank信息
                String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirection();
                //写入到文件中
                bufferedWriter.write(record + "\r\n");
            }
        }
        bufferedWriter.close();
    }


    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //当我方坦克击毁一个敌方坦克，就让allEnemyTankNum ++
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }
}
