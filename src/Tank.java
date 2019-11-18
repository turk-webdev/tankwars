import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tank extends GameObject {
    GameHandler gh;
    private final int ROTATESPEED=3,ACCEL=2,COOLDOWN=500;
    private int hp=50,numLives=3,ammo=10;
    private long firedTime=0;
    private boolean upPressed,downPressed,leftPressed,rightPressed,firePressed,isAlive=true;


    public Tank(int x, int y, int angle, ID id, BufferedImage img, GameHandler gh) {
        super(x, y, angle, id, img);
        this.gh = gh;
        isSolid = true;
        isHarmful = false;
    }

    public void tick() {
        if (this.upPressed) this.moveForward();
        else if (this.downPressed) this.moveBackward();
        else if (this.leftPressed) this.rotateLeft();
        else if (this.rightPressed) this.rotateRight();
        else if (this.firePressed) this.fire();
        this.checkCollision();
    }

    private void checkCollision() {
        for (int i=0; i<gh.gameObjs.size(); i++) {
            GameObject tmp = gh.gameObjs.get(i);
            if (tmp.getId() == this.id) continue;
            if (this.getHitbox().intersects(tmp.getHitbox())) gh.handleCollision(this,tmp);
        }
    }

//    private void checkCollision() {
//        for (int i=0; i<gh.gameObjs.size(); i++) {
//            GameObject tmp = gh.gameObjs.get(i);
//            if (tmp.getId() != this.id) {
//                if (this.getHitbox().intersects(tmp.getHitbox())) {
//                    if (tmp instanceof Bullet) {
//                        if (((Bullet) tmp).getSource() != this.id) {
//                            checkHealth(((Bullet) tmp).getDmg());
//                            gh.removeGameObject(tmp);
//                        }
//                    } else if (tmp instanceof HealthBoost) {
//                        this.hp += ((HealthBoost) tmp).getHeal();
//                        if (this.hp > 50) this.hp = 50; // no overhealing allowed
//                        gh.removeGameObject(tmp);
//                    } else if (tmp instanceof LifeBoost) {
//                        this.numLives += 1;
//                        if (this.numLives > 3) this.numLives = 3; // max lives is 3
//                        gh.removeGameObject(tmp);
//                    } else if (tmp instanceof AmmoBoost) {
//                        this.ammo = 10;
//                        gh.removeGameObject(tmp);
//                    } else if (tmp instanceof Tank || tmp instanceof Wall || tmp instanceof BreakableWall) {
//                        if (this.upPressed) moveBackward();
//                        if (this.downPressed) moveForward();
//                    }
//                }
//            }
//        }
//    }

    public void render(Graphics g) {
        // Drawing the actual tank
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x,this.y);
        rotation.rotate(Math.toRadians(angle),this.img.getWidth()/2.0,this.img.getHeight()/2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img,rotation,null);

        // Drawing the floating HUD
        // HP Bar
        g2d.setColor(Color.gray);
        g2d.fillRect(this.x,this.y+this.img.getHeight()+5,this.img.getWidth(),8);
        g2d.setColor(Color.green);
        g2d.fillRect(this.x,this.y+this.img.getHeight()+5,this.hp,8);

        // Lives
        int prevY = this.y + this.img.getHeight();
        for (int i=0; i<this.numLives; i++) {
            g2d.setColor(Color.magenta);
            g2d.drawRect(this.x-12,prevY,8,8);
            g2d.setColor(Color.decode("#fc6262"));
            g2d.fillRect(this.x-12,prevY,8,8);
            prevY -= 12;
        }

        // Ammo
        g2d.setColor(Color.gray);
        g2d.fillRect(this.x,this.y+this.img.getHeight()+12,this.img.getWidth(),6);
        g2d.setColor(Color.yellow);
        g2d.fillRect(this.x,this.y+this.img.getHeight()+12,this.ammo*5,6);
    }

    //  -------- BEGIN BULLET METHODS  --------
    private void fire() {
        // Determine if we can shoot again
        if (System.currentTimeMillis() - firedTime >= COOLDOWN) {
            if (ammo > 0) {
                // FIRE!!!
                this.ammo -= 1;
                int bx = this.x + this.img.getWidth()/2 - Assets.bullet.getWidth()/2;
                int by = this.y + this.img.getHeight()/2 - Assets.bullet.getHeight()/2;
                gh.addGameObject(new Bullet(bx,by,this.angle,ID.Bullet,Assets.bullet,this.id,this.gh));
                firedTime = System.currentTimeMillis();
            }
        }
    }
    //  -------- END BULLET METHODS  --------

    // -------- BEGIN MOVEMENT METHODS --------
    public void moveForward() {
        // below trig courtesy of ajsousa from TankRotationExample
        speedX = (int) Math.round(ACCEL * Math.cos(Math.toRadians(angle)));
        speedY = (int) Math.round(ACCEL * Math.sin(Math.toRadians(angle)));
        x += speedX;
        y += speedY;
    }

    public void moveBackward() {
        // below trig courtesy of ajsousa from TankRotationExample
        speedX = (int) Math.round(ACCEL * Math.cos(Math.toRadians(angle)));
        speedY = (int) Math.round(ACCEL * Math.sin(Math.toRadians(angle)));
        x -= speedX;
        y -= speedY;
    }

    public void rotateLeft() { this.angle -= ROTATESPEED; }
    public void rotateRight() { this.angle += ROTATESPEED; }
    // -------- END MOVEMENT METHODS --------

    // -------- BEGIN FLAG TOGGLE METHODS --------
    public void setUpPressed(boolean upPressed) { this.upPressed = upPressed; }
    public void setDownPressed(boolean downPressed) { this.downPressed = downPressed; }
    public void setLeftPressed(boolean leftPressed) { this.leftPressed = leftPressed; }
    public void setRightPressed(boolean rightPressed) { this.rightPressed = rightPressed; }
    public void setFirePressed(boolean firePressed) { this.firePressed = firePressed; }

    public boolean getUpPressed() { return this.upPressed; }
    public boolean getDownPressed() { return this.downPressed; }
    // -------- END FLAG TOGGLE METHODS --------

    public int takeDmg(int dmg) {
        this.hp -= dmg;

        if (this.hp <= 0) {
            this.hp = 50;
            this.numLives -= 1;
        }

        if (this.numLives <= 0) return 0;

        return 1;
    }

    public void addLife() { if (this.numLives<3) this.numLives += 1; }
    public void addHeal(int heal) {
        this.hp += heal;
        if (this.hp > 50) this.hp = 50; // no overhealing
    }
    public void fillAmmo() { this.ammo = 10; }

}
