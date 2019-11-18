import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Wall extends GameObject {

    public Wall(int x, int y, int angle, ID id, BufferedImage img) {
        super(x, y, angle, id, img);
        isSolid = true;
        isHarmful = false;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x,this.y);
        rotation.rotate(Math.toRadians(this.angle),this.img.getWidth()/2.0,this.img.getHeight()/2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img,rotation,null);
    }
}
