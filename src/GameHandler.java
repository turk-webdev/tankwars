import java.util.LinkedList;
import java.awt.Graphics;

public class GameHandler {

    LinkedList<GameObject> gameObjs = new LinkedList<>();
    Game game;

    public GameHandler(Game game) { this.game = game; }

    public void tick() {
        for (int i=0; i<gameObjs.size(); i++) {
            // Go through gameObjs List and update each object
            GameObject obj = gameObjs.get(i);
            obj.tick();
        }

    }

    public void render(Graphics g) {
        for (int i=0; i<gameObjs.size(); i++) {
            // Go through gameObjs List and render each object
            GameObject obj = gameObjs.get(i);
            obj.render(g);
        }

    }

    /**
     * There will be instances where a Bullet and Tank object will call this at the same time.
     * Because we don't want to double dip our damage, we only listen to damage coming from bullets.
     * @param obj1 the object that is making the call
     * @param obj2 the object that <obj1> is colliding with
     */
    public void handleCollision(GameObject obj1, GameObject obj2) {
        if (obj1 instanceof Tank) {
            if (obj2.isSolid()) {
                if (((Tank)obj1).getUpPressed()) ((Tank)obj1).moveBackward();
                if (((Tank)obj1).getDownPressed()) ((Tank)obj1).moveForward();
                return;
            }
            if (obj2 instanceof HealthBoost) {
                ((Tank)obj1).addHeal(((HealthBoost) obj2).getHeal());
                removeGameObject(obj2);
            } else if (obj2 instanceof AmmoBoost) {
                ((Tank) obj1).fillAmmo();
                removeGameObject(obj2);
            } else if (obj2 instanceof LifeBoost) {
                ((Tank) obj1).addLife();
                removeGameObject(obj2);
            }
        } else if (obj1 instanceof Bullet) {
            if (obj2 instanceof Tank) {
                if (((Bullet) obj1).getSource() != obj2.getId()) {
                    int tmp = ((Tank) obj2).takeDmg(obj1.getDmg());
                    if (tmp <= 0) removeGameObject(obj2);
                }
                removeGameObject(obj1);
            } else if (obj2 instanceof BreakableWall) {
                int tmp = ((BreakableWall) obj2).takeDmg(obj1.getDmg());
                if (tmp <= 0) removeGameObject(obj2);
                removeGameObject(obj1);
            } else if (obj2 instanceof Wall) {
                removeGameObject(obj1);
            }
        }
    }

    public void addGameObject(GameObject obj) { this.gameObjs.add(obj); }
    public void removeGameObject(GameObject obj) { this.gameObjs.remove(obj); }

}
