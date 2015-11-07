
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener,Runnable{
    private JPanel gameSettings = new JPanel();
    private JPanel gameScreen = new JPanel();
    private JTextArea speedArea = new JTextArea(1,3);
    private JTextArea angleArea = new JTextArea(1,3);
    private JLabel speedInfo = new JLabel("Add speed(0-10)");
    private JLabel angleInfo = new JLabel("Add angle(0-90)");
    private JButton shootButton = new JButton("Shoot!");
    private JLabel catapultImageLabel = new JLabel();
    private JLabel scoreLabel = new JLabel();
    private JLabel coneImageLabel = new JLabel();
    private JLabel ballImageLabel = new JLabel();
    private JLabel timesLostLabel = new JLabel();
    private JLabel messageLabel = new JLabel();
    private Sprite icon = new Sprite();
    private Game game = new Game();
    private int speed;
    private int angle;
    private int maxX, maxY;
    private Thread frame;
    private boolean catFrameBegin = false, ballFrameBegin = false;
    
    private final int C_HEIGHT = icon.getCatapultSpriteHeight();
    private final int C_WIDTH = icon.getCatapultSpriteWidth();
    private final int B_HEIGHT = icon.getBallSpriteHeight();
    private final int B_WIDTH = icon.getBallSpriteWidth();
    private final int CO_HEIGHT = icon.getConeSpriteHeight();
    private final int CO_WIDTH = icon.getConeSpriteWidth();
    
    public GameWindow(){
        super("Catapult");
        setSize(640,360);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        gameSettings.setLayout(new FlowLayout());
        gameScreen.setLayout(null);
        add(gameSettings, BorderLayout.NORTH);
        add(gameScreen, BorderLayout.CENTER);
        gameSettings.add(speedArea);
        gameSettings.add(speedInfo);
        gameSettings.add(angleArea);
        gameSettings.add(angleInfo);
        gameSettings.add(shootButton);
        shootButton.addActionListener(this);
        catapultImageLabel.setIcon(icon.getSpriteCatapultStart());
        gameScreen.add(catapultImageLabel);
        gameScreen.add(scoreLabel);
        gameScreen.add(timesLostLabel);
        ballImageLabel.setIcon(icon.getSpriteBall());
        coneImageLabel.setIcon(icon.getSpriteCone());
        gameScreen.add(coneImageLabel);
        game.setXCone();
        coneImageLabel.setBounds(game.getXCone1(), 255, CO_WIDTH, CO_HEIGHT);
        catapultImageLabel.setBounds(10, 200, C_WIDTH, C_HEIGHT);
        setResizable(false);    // Gia logous grafikwn
        setVisible(true);
        scoreLabel.setText("Score: "+game.getScore());
        scoreLabel.setBounds(gameScreen.getWidth()-70, 10, 80, 20);
        timesLostLabel.setText("Lives: "+game.getTimesLost());
        timesLostLabel.setBounds(10, 10, 80, 20);
        gameScreen.add(messageLabel);
        messageLabel.setText("Begin!");
        messageLabel.setBounds((gameScreen.getWidth()-50)/2, 20, 80, 20);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        gameScreen.remove(messageLabel);
        speed = Integer.parseInt(speedArea.getText());
        angle = Integer.parseInt(angleArea.getText());
        if(speed <= 10 && speed >= 0 && angle >= 0 && angle <= 90){
            game.equationX(angle, speed);
            game.equationY(angle, speed);
            startAnimating();
        }
        else{
            gameScreen.add(messageLabel);
            messageLabel.setText("Wrong attributes!");
            messageLabel.setBounds((gameScreen.getWidth()-50)/2, 20, 150, 20);
        }
    }
    
    @Override
    public void run(){
        if(catFrameBegin){
            try{
                catapultImageLabel.setIcon(icon.getSpriteCatapultMiddle());
                Thread.sleep(100);
                catapultImageLabel.setIcon(icon.getSpriteCatapultEnd());
                ballFrameBegin = true;
                catFrameBegin = false;
            }catch(InterruptedException e){
                System.out.println("Thread interrupted!");
            }
        }
        if(ballFrameBegin){
            int minX=35, minY=250;
            boolean ballFall = false;
            gameScreen.add(ballImageLabel);
            for(;;){
                try{
                    Rectangle ballBounds = ballImageLabel.getBounds();
                    Rectangle coneBounds = coneImageLabel.getBounds();
                    if(minX < maxX){
                        ballImageLabel.setBounds(minX, minY, B_WIDTH, B_HEIGHT);
                        minX++;
                    }
                    if((minY > maxY) && ballFall == false){
                        ballImageLabel.setBounds(minX, minY, B_WIDTH, B_HEIGHT);
                        minY--;
                    }
                    if(minY == maxY){
                        ballFall = true;
                        ballImageLabel.setBounds(minX, minY, B_WIDTH, B_HEIGHT);
                        minY++;
                    }
                    if((minY < 360) && ballFall == true){
                        ballImageLabel.setBounds(minX, minY, B_WIDTH, B_HEIGHT);
                        minY++;
                    }
                    if((minX >= game.getXCone1() || minX+10 >= game.getXCone1()) && (minX <= game.getXCone2() || minX+10 <= game.getXCone1()) && (minY >= game.getYCone() || minY+10 >= game.getYCone())){
                        System.out.println("HIT!");
                        setScoreLabel();
                        restart();
                        break;
                    }
                    if((minX == maxX) || (minY == 270)){
                        System.out.println("LOST!");
                        setTimesLostLabel();
                        restart();
                        break;
                    }
                    Thread.sleep(10);
                }catch(InterruptedException e){
                    System.out.println("Thread interrupted!");
                }
            }
        }
    }
    
    private void startAnimating(){
        if(frame == null){
            maxX = game.getX();
            maxY = game.getY();
            frame = new Thread(this, "frame");
            catFrameBegin = true;
            frame.start();
        }
    }
    
    private void setScoreLabel(){
        game.setScore();
        scoreLabel.setText("Score: "+game.getScore());
    }
    
    private void setTimesLostLabel(){
        game.setTimesLost();
        timesLostLabel.setText("Lives: "+game.getTimesLost());
    }
    
    public void restart(){
        gameScreen.remove(ballImageLabel);
        gameScreen.remove(coneImageLabel);
        ballFrameBegin = false;
        frame = null;
        catapultImageLabel.setIcon(icon.getSpriteCatapultStart());
        if(!game.checkIfLost()){
            gameScreen.add(coneImageLabel);
            game.setXCone();
            coneImageLabel.setBounds(game.getXCone1(), 255, CO_WIDTH, CO_HEIGHT);
        }
        else{
            if(game.getHighScore() < game.getScore()){
                game.setHighScore();
                gameScreen.add(messageLabel);
                messageLabel.setText("You lost but you set a new Highscore!");
                messageLabel.setBounds((gameScreen.getWidth()-100)/2, 20, 300, 20);
            }
            else{
                gameScreen.add(messageLabel);
                messageLabel.setText("You Lost!");
                messageLabel.setBounds((gameScreen.getWidth()-20)/2, 20, 80, 20);
            }
        }
    }
}
