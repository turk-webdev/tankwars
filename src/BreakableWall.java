import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BreakableWall extends GameObject {
    private int hp = 30;
    private BufferedImage img2; // image when cracked a bit

    public BreakableWall(int x, int y, int angle, ID id, BufferedImage img, BufferedImage img2) {
        super(x, y, angle, id, img);
        isSolid = true;
        isHarmful = false;
        this.img2 = img2;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x,this.y);
        if (hp < 30) this.img = this.img2;
        rotation.rotate(Math.toRadians(this.angle),this.img.getWidth()/2.0,this.img.getHeight()/2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img,rotation,null);
    }

    public int takeDmg(int dmg) {
        hp -= dmg;
        return hp;
    }
}
