import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import static javax.imageio.ImageIO.read;

/**
 * This class facilitates the control of our players' tanks. We have two separate schema for Player 1 and 2.
 * Rather than doing any of the math to move the tanks here, we simply toggle the boolean flags in each tank
 * so that all of the movement can be handled within the Tank class.
 */

public class KeyInput extends KeyAdapter {

    private GameHandler gh;
    private GameMenu menu;

    public KeyInput(GameHandler gh, GameMenu menu) {
        this.gh = gh;
        this.menu = menu;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();


        if (menu.getGameState() < 0) {
            // Menu keybinds
            switch (key) {
                case KeyEvent.VK_UP:
                    menu.setUpPressed(true);
                case KeyEvent.VK_DOWN:
                    menu.setDownPressed(true);
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    menu.setSelectPressed(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    menu.setEscapePressed(true);
                    break;
            }

        } else {
            // Keybinds for tanks
            for (int i = 0; i < gh.gameObjs.size(); i++) {
                GameObject tmp = gh.gameObjs.get(i);

                if (tmp.getId() == ID.Player1) {
                    // Key events for Player 1
                    switch (key) {
                        case KeyEvent.VK_W:
                            ((Tank) tmp).setUpPressed(true);
                            break;
                        case KeyEvent.VK_S:
                            ((Tank) tmp).setDownPressed(true);
                            break;
                        case KeyEvent.VK_A:
                            ((Tank) tmp).setLeftPressed(true);
                            break;
                        case KeyEvent.VK_D:
                            ((Tank) tmp).setRightPressed(true);
                            break;
                        case KeyEvent.VK_R:
                            ((Tank) tmp).setFirePressed(true);
                            break;
                    }
                } else if (tmp.getId() == ID.Player2) {
                    // Key events for Player 2
                    switch (key) {
                        case KeyEvent.VK_UP:
                            ((Tank) tmp).setUpPressed(true);
                            break;
                        case KeyEvent.VK_DOWN:
                            ((Tank) tmp).setDownPressed(true);
                            break;
                        case KeyEvent.VK_LEFT:
                            ((Tank) tmp).setLeftPressed(true);
                            break;
                        case KeyEvent.VK_RIGHT:
                            ((Tank) tmp).setRightPressed(true);
                            break;
                        case KeyEvent.VK_SPACE:
                            ((Tank) tmp).setFirePressed(true);
                            break;
                    }
                }
            }
        }
    } // end keyPressed

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (menu.getGameState() < 0) {
            // Menu keybinds
            switch (key) {
                case KeyEvent.VK_UP:
                    menu.setUpPressed(false);
                case KeyEvent.VK_DOWN:
                    menu.setDownPressed(false);
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    menu.setSelectPressed(false);
                case KeyEvent.VK_ESCAPE:
                    menu.setEscapePressed(false);
                    break;
            }

        } else {

            for (int i = 0; i < gh.gameObjs.size(); i++) {
                GameObject tmp = gh.gameObjs.get(i);

                if (tmp.getId() == ID.Player1) {
                    // Key events for Player 1
                    switch (key) {
                        case KeyEvent.VK_W:
                            ((Tank) tmp).setUpPressed(false);
                            break;
                        case KeyEvent.VK_S:
                            ((Tank) tmp).setDownPressed(false);
                            break;
                        case KeyEvent.VK_A:
                            ((Tank) tmp).setLeftPressed(false);
                            break;
                        case KeyEvent.VK_D:
                            ((Tank) tmp).setRightPressed(false);
                            break;
                        case KeyEvent.VK_R:
                            ((Tank) tmp).setFirePressed(false);
                            break;
                    }
                } else if (tmp.getId() == ID.Player2) {
                    // Key events for Player 2
                    switch (key) {
                        case KeyEvent.VK_UP:
                            ((Tank) tmp).setUpPressed(false);
                            break;
                        case KeyEvent.VK_DOWN:
                            ((Tank) tmp).setDownPressed(false);
                            break;
                        case KeyEvent.VK_LEFT:
                            ((Tank) tmp).setLeftPressed(false);
                            break;
                        case KeyEvent.VK_RIGHT:
                            ((Tank) tmp).setRightPressed(false);
                            break;
                        case KeyEvent.VK_SPACE:
                            ((Tank) tmp).setFirePressed(false);
                            break;
                    }
                }
            }
        }
    } // end keyReleased();



}
