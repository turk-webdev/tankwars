import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class LifeBoost extends GameObject {
    private final int MAX_W=this.img.getWidth()+5, MAX_H=this.img.getHeight()+5;

    public LifeBoost(int x, int y, int angle, ID id, BufferedImage img) {
        super(x, y, angle, id, img);
        isSolid = false;
        isHarmful = false;
    }

    public void tick() {
        // TODO: Have an animation where it pulses maybe?
    }

    public void render(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x,this.y);
        rotation.rotate(Math.toRadians(angle),this.img.getWidth()/2.0,this.img.getHeight()/2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img,rotation,null);
    }
}
