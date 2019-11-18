import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * This class constructs the JFrame within which our game will be held
 */



public class GameWindow extends Canvas {
    JFrame frame;

    public GameWindow(int width, int height, String title, Game game) {
        frame = new JFrame(title);
        frame.setLayout(new BorderLayout());

        JLabel background = new JLabel(new ImageIcon("Resources/Background.bmp"));
        frame.add(background);

        background.setLayout(new FlowLayout());

        // Define our desired dimensions for our game window.
        // For sake of ease, window should only ever be one size so we can use static coordinates
        frame.setPreferredSize(new Dimension(width,height));
        frame.setMaximumSize(new Dimension(width,height));
        frame.setMinimumSize(new Dimension(width,height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }

    public void close() {
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

}
