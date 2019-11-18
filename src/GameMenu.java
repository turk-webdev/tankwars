import java.awt.*;

public class GameMenu {
    private static int selection = 0;
    private int winner;
    private long lastInput;
    private final int COOLDOWN=150;
    private boolean upPressed, downPressed, selectPressed, escapePressed, showHelp=false, gameOver=false;
    Game game;

    public GameMenu(Game game) { this.game = game; }

    public void render(Graphics g) {
        g.setFont(new Font("Calibri", Font.BOLD, 34));
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(Assets.title, Game.WIDTH / 2 - Assets.title.getWidth() / 2, 100, null);

        if (this.showHelp) {
            g.setColor(Color.white);
            g.drawString("Player 1:",Game.WIDTH / 2 - Assets.title.getWidth() / 2, 100 + Assets.title.getHeight() + 50);
            g.drawString("Movement: WASD, Fire: R",Game.WIDTH / 2 - Assets.title.getWidth() / 2, 100 + Assets.title.getHeight() + 80);
            g.drawString("Player 2:",Game.WIDTH / 2 - Assets.title.getWidth() / 2, 100 + Assets.title.getHeight() + 140);
            g.drawString("Movement: Arrow keys, Fire: Spacebar",Game.WIDTH / 2 - Assets.title.getWidth() / 2, 100 + Assets.title.getHeight() + 170);
            g.drawString("Press enter or spacebar to go back",Game.WIDTH / 2 - Assets.title.getWidth() / 2, 100 + Assets.title.getHeight() + 250);

        } else {
            if (gameOver) {
                g.setColor(Color.red);
                String str = "GAME OVER! Player " + winner + " wins.";
                g.drawString(str,Game.WIDTH / 2 - Assets.title.getWidth() / 2, 100 + Assets.title.getHeight() + 50);
                g.drawString("Press Escape to exit, Space or Enter to return to main menu.",Game.WIDTH / 2 - 100, 100 + Assets.title.getHeight() + 100);

            } else {
                if (this.selection == 0) {
                    g.setColor(Color.white);
                } else {
                    g.setColor(Color.decode("#421d03"));
                }
                g.drawString("Play", Game.WIDTH / 2 - 100, 100 + Assets.title.getHeight() + 50);

                if (this.selection == 1) {
                    g.setColor(Color.white);
                } else {
                    g.setColor(Color.decode("#421d03"));
                }
                g.drawString("Controls", Game.WIDTH / 2 - 100, 100 + Assets.title.getHeight() + 100);

                if (this.selection == 2) {
                    g.setColor(Color.white);
                } else {
                    g.setColor(Color.decode("#421d03"));
                }
                g.drawString("Quit", Game.WIDTH / 2 - 100, 100 + Assets.title.getHeight() + 150);
            }
        }


    }

    public void up() {
        if (this.selection > 0 & !this.showHelp) --this.selection;
        this.lastInput = System.currentTimeMillis();
    }

    public void down() {
        if (this.selection < 2 & !this.showHelp) ++this.selection;
        this.lastInput = System.currentTimeMillis();
    }

    public void select() {
        this.lastInput = System.currentTimeMillis();
        if (this.showHelp) {
            this.selection = 0;
            this.showHelp = false;
        } else {
            switch (this.selection) {
                case 0:
                    this.game.toggleGameState();
                    break;
                case 1:
                    this.showHelp = true;
                    break;
                case 2:
                    this.game.stop();
                    break;

            }
        }
    }

    public void exit() {
        this.lastInput = System.currentTimeMillis();
        game.stop();
    }

    public void setUpPressed(boolean set) { this.upPressed = set; }
    public void setDownPressed(boolean set) { this.downPressed = set; }
    public void setSelectPressed(boolean set) { this.selectPressed = set; }
    public void setEscapePressed(boolean set) { this.escapePressed = set;}

    public void tick() {
        if (System.currentTimeMillis() - lastInput >= COOLDOWN) {
            if (this.upPressed) this.up();
            else if (this.downPressed) this.down();
            else if (this.selectPressed) this.select();
            else if (this.escapePressed) exit();
        }
    }

    public int getGameState() { return game.getGameState(); }
}
