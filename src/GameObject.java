import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected int x,y,speedX,speedY,angle,dmg;
    protected BufferedImage img;
    protected ID id;
    protected boolean isSolid,isHarmful;

    public GameObject(int x, int y, int angle, ID id, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.id = id;
        this.img = img;
    }

    public abstract void tick();
    public abstract void render(Graphics g);


    public Rectangle getHitbox() {
        return new Rectangle(this.x,this.y,this.img.getHeight(),this.img.getWidth());
    }

    public int getAngle() { return angle; }
    public void setAngle(int angle) { this.angle = angle; }

    public int getWidth() { return img.getWidth(); }
    public int getHeight() { return img.getHeight(); }
    public int getX() { return x; }
    public int getY() { return y; }
    public ID getId() { return id; }
    public boolean isSolid() { return isSolid; }
    public boolean isHarmful() { return isHarmful; }
    public int getDmg() { return dmg; }

    public BufferedImage getImg() { return img; }

}
