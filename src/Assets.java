import static javax.imageio.ImageIO.read;
import java.awt.image.BufferedImage;
import java.io.File;

public class Assets {
    public static BufferedImage world,tank1,tank2,bullet,ammoPack,hpPack,lifePack,wall,breakable,broken,title;
    public static File music;
    static {
        // TODO: Fix ALL path definitions from file
        try {

            world = read(new File("Resources/Background.bmp"));
            tank1 = read(new File("Resources/tank1.png"));
            tank2 = read(new File("Resources/tank2.png"));
            bullet = read(new File("Resources/Shell.png"));
            ammoPack = read(new File("Resources/ammo.png"));
            hpPack = read(new File("Resources/health.png"));
            lifePack = read(new File("Resources/life.png"));
            wall = read(new File("Resources/wall.png"));
            breakable = read(new File("Resources/breakable.png"));
            broken = read(new File("Resources/broken.png"));
            title = read(new File("Resources/Title.bmp"));


            music = new File("Resources/Music.mid");

        } catch (Exception e) {

            e.printStackTrace();
            System.exit(1);

        }
    }
}
