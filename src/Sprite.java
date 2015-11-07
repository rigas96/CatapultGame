
import javax.swing.*;

public class Sprite{
    private ImageIcon[] sprites;
    
    public Sprite(){
        sprites = new ImageIcon[5];
        sprites[0] = new ImageIcon("sprites\\sprite_catapult1.png");
        sprites[1] = new ImageIcon("sprites\\sprite_catapult2.png");
        sprites[2] = new ImageIcon("sprites\\sprite_catapult3.png");
        sprites[3] = new ImageIcon("sprites\\sprite_ball.png");
        sprites[4] = new ImageIcon("sprites\\sprite_cone.png");
    }
    
    public ImageIcon getSpriteCatapultStart(){
        return sprites[0];
    }
    
    public ImageIcon getSpriteCatapultMiddle(){
        return sprites[1];
    }
    
    public ImageIcon getSpriteCatapultEnd(){
        return sprites[2];
    }
    
    public ImageIcon getSpriteBall(){
        return sprites[3];
    }
    
    public ImageIcon getSpriteCone(){
        return sprites[4];
    }
    
    public int getCatapultSpriteWidth(){
        return sprites[0].getIconWidth();
    }
    
    public int getCatapultSpriteHeight(){
        return sprites[0].getIconHeight();
    }
    
    public int getBallSpriteWidth(){
        return sprites[3].getIconWidth();
    }
    
    public int getBallSpriteHeight(){
        return sprites[3].getIconHeight();
    }
    
    public int getConeSpriteWidth(){
        return sprites[4].getIconWidth();
    }
    
    public int getConeSpriteHeight(){
        return sprites[4].getIconHeight();
    }
}
