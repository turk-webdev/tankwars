import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    private ID source;
    private final int ACCEL = 7;
    private boolean show;
    GameHandler gh;

    public Bullet(int x, int y, int angle, ID id, BufferedImage img, ID source, GameHandler gh) {
        super(x, y, angle, id, img);
        this.show = true;
        this.source = source;
        this.dmg = 15;
        this.gh = gh;
        isSolid = false;
        isHarmful = true;
    }

    public void tick() {
        this.speedX = (int) Math.round(this.ACCEL * Math.cos(Math.toRadians(this.angle)));
        this.speedY = (int) Math.round(this.ACCEL * Math.sin(Math.toRadians(this.angle)));
        this.x += this.speedX;
        this.y += this.speedY;
        checkCollision();
    }

    private void checkCollision() {
        for (int i=0; i<gh.gameObjs.size(); i++) {
            GameObject tmp = gh.gameObjs.get(i);
            if (tmp.getId() == this.source) continue;
            if (this.getHitbox().intersects(tmp.getHitbox())) gh.handleCollision(this,tmp);
        }
    }

    public void render(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x,this.y);
        rotation.rotate(Math.toRadians(this.angle),this.img.getWidth()/2.0,this.img.getHeight()/2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img,rotation,null);
    }

    public ID getSource() { return this.source; }
}
