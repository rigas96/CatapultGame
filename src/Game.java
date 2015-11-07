
import java.io.*;
import java.util.*;

public class Game {
    
    private final int G = 10;
    private int timesLost;
    private int tempTL;
    private int highScore;
    private int score;
    private int[] xCone = new int[2];
    private int yCone;
    private int fullX, fullY;
    
    public Game(){
        try{
            BufferedReader b = new BufferedReader(new FileReader("init.txt"));
            b.readLine();
            timesLost = Integer.parseInt(b.readLine());
            tempTL = timesLost;
            b.readLine();
            highScore = Integer.parseInt(b.readLine());
            b.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        score = 0;
        yCone = 255;
    }
    
    public void equationX(double a,double u){
        double sin = Math.sin(Math.toRadians(a));
        double cos = Math.cos(Math.toRadians(a));
        double t = ((2*u*sin)/G);
        double x = ((u*cos*t)*74); // Pollaplasiasoume x74 gia na erthei stin analogia tou parathirou mas
        fullX = (int)x;
    }
    
    public void equationY(int a, int u){
        int t = (int)((2*u*Math.sin(a))/G);
        int y = (int)(((Math.pow(u, 2))/(2*G))*69); // Pollaplasiasoume x69 gia na erthei stin analogia tou parathirou mas
        fullY = 360-y;
    }
    
    public int getX(){
        return fullX;
    }
    
    public int getY(){
        return fullY;
    }
    
    public void setScore(){
        score += 10;
    }
    
    public int getScore(){
        return score;
    }
    
    public void setXCone(){
        Random rand = new Random();
        xCone[0] = rand.nextInt(400)+200;
        xCone[1] = xCone[0]+32;
    }
    
    public int getXCone1(){
        return xCone[0];
    }
    
    public int getXCone2(){
        return xCone[1];
    }
    
    public int getYCone(){
        return yCone;
    }
    
    public int getTimesLost(){
        return timesLost;
    }
    
    public void setTimesLost(){
        timesLost--;
    }
    
    public int getHighScore(){
        return highScore;
    }
    
    public boolean checkIfLost(){
        if(timesLost == 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void setHighScore(){
        highScore = score;
        try{
            BufferedWriter b = new BufferedWriter(new FileWriter("init.txt"));
            b.write("Times Lost\r\n");
            b.write(tempTL+"\r\n");
            b.write("High Score\r\n");
            b.write(highScore+"\r\n");
            b.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
}
