import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * This class is the nucleus of our entire game. Everything the user or game does
 * is captured here and sent to be handled by the proper classes.
 */

public class Game extends Canvas implements Runnable {

    // We want our window to always have a 16:9 aspect ratio because it's 2019 and we want widescreen up in this mfer
    public static final int WIDTH=1240, HEIGHT=WIDTH/16*12;


    private Thread thread;
    private boolean isRunning = false;
    private int currState = -1; // -1 = menu, +1 = game
    GameMenu menu;
    GameWindow window;

    private GameHandler gh;

    public Game() {
        gh = new GameHandler(this);
        menu = new GameMenu(this);

        this.addKeyListener(new KeyInput(gh,menu));

        window = new GameWindow(WIDTH,HEIGHT,"Tank Wars",this);

        // TODO: Initialize all default objects in game
        // Spawn players
        gh.addGameObject(new Tank(150,140,0,ID.Player1,Assets.tank1,this.gh));
        gh.addGameObject(new Tank(1100,780,180,ID.Player2,Assets.tank2,this.gh));

        // Build solid walls as border
        // Top row
        for (int i=0; i<=WIDTH/Assets.wall.getWidth(); i++) {
            gh.addGameObject(new Wall(Assets.wall.getWidth()*i,0,0,ID.Wall,Assets.wall));
        }
        // Bottom row NOTE: 8 is a hardcode value to adjust pixels so art lines up
        for (int i=0; i<=WIDTH/Assets.wall.getWidth(); i++) {
            gh.addGameObject(new Wall(Assets.wall.getWidth()*i,HEIGHT-Assets.wall.getHeight()+8,0,ID.Wall,Assets.wall));
        }
        // Left column
        for (int i=0; i<=HEIGHT/Assets.wall.getHeight(); i++) {
            gh.addGameObject(new Wall(0,Assets.wall.getHeight()*i,0,ID.Wall,Assets.wall));
        }
        // Right column
        for (int i=0; i<=HEIGHT/Assets.wall.getHeight(); i++) {
            gh.addGameObject(new Wall(WIDTH-Assets.wall.getWidth(),Assets.wall.getHeight()*i,0,ID.Wall,Assets.wall));
        }

        // Build powerups
        gh.addGameObject(new HealthBoost(620,245,0,ID.HealthBoost,Assets.hpPack));
        gh.addGameObject(new HealthBoost(620,670,0,ID.HealthBoost,Assets.hpPack));

        gh.addGameObject(new AmmoBoost(833,472,0,ID.AmmoBoost,Assets.ammoPack));
        gh.addGameObject(new AmmoBoost(386,472,0,ID.AmmoBoost,Assets.ammoPack));

        gh.addGameObject(new AmmoBoost(150,785,0,ID.AmmoBoost,Assets.ammoPack));
        gh.addGameObject(new AmmoBoost(1100,140,0,ID.AmmoBoost,Assets.ammoPack));

        gh.addGameObject(new LifeBoost(613,466,0,ID.LifeBoost,Assets.lifePack));

        // Build walls
        int x1=833-Assets.breakable.getWidth()/4,y1=472-Assets.breakable.getHeight()/4;
        gh.addGameObject(new BreakableWall(x1,y1,0,ID.BreakableWall,Assets.breakable,Assets.broken));
        gh.addGameObject(new BreakableWall(x1,y1-Assets.breakable.getHeight(),0,ID.BreakableWall,Assets.breakable,Assets.broken));
        gh.addGameObject(new BreakableWall(x1,y1+Assets.breakable.getHeight(),0,ID.BreakableWall,Assets.breakable,Assets.broken));

        gh.addGameObject(new Wall(x1,y1+(2*Assets.wall.getHeight()),0,ID.Wall,Assets.wall));
        gh.addGameObject(new Wall(x1+Assets.wall.getWidth(),y1+(3*Assets.wall.getHeight()),0,ID.Wall,Assets.wall));
        gh.addGameObject(new Wall(x1,y1-(2*Assets.wall.getHeight()),0,ID.Wall,Assets.wall));
        gh.addGameObject(new Wall(x1+Assets.wall.getWidth(),y1-(3*Assets.wall.getHeight()),0,ID.Wall,Assets.wall));

        x1=386-Assets.breakable.getWidth()/4;
        gh.addGameObject(new BreakableWall(x1,y1,0,ID.BreakableWall,Assets.breakable,Assets.broken));
        gh.addGameObject(new BreakableWall(x1,y1-Assets.breakable.getHeight(),0,ID.BreakableWall,Assets.breakable,Assets.broken));
        gh.addGameObject(new BreakableWall(x1,y1+Assets.breakable.getHeight(),0,ID.BreakableWall,Assets.breakable,Assets.broken));

        gh.addGameObject(new Wall(x1,y1+(2*Assets.wall.getHeight()),0,ID.Wall,Assets.wall));
        gh.addGameObject(new Wall(x1-Assets.wall.getWidth(),y1+(3*Assets.wall.getHeight()),0,ID.Wall,Assets.wall));
        gh.addGameObject(new Wall(x1,y1-(2*Assets.wall.getHeight()),0,ID.Wall,Assets.wall));
        gh.addGameObject(new Wall(x1-Assets.wall.getWidth(),y1-(3*Assets.wall.getHeight()),0,ID.Wall,Assets.wall));




    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    public synchronized void stop() {
        window.close();
        try {
            thread.join();
            isRunning = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Game loop copied from RealTutsGML's video about making games
     * Video Source URL: https://www.youtube.com/watch?v=1gir2R7G9ws
     */
    public void run() {
        this.requestFocus(); // so we don't have to click on screen to get controls
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >=1) {
                tick();
                delta--;
            }

            if(isRunning) render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        if (currState > 0) {
            gh.tick();
        } else {
            menu.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        /** This is a solution to stretch background image over entire frame window
         *  Source: https://stackoverflow.com/questions/26887758/image-not-scaling-with-graphics-drawimage
         */
        double scaleX = (double)WIDTH/Assets.world.getWidth();
        double scaleY = (double)HEIGHT/Assets.world.getHeight();

        AffineTransform scale = AffineTransform.getScaleInstance(scaleX,scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scale,AffineTransformOp.TYPE_BILINEAR);
        Assets.world = bilinearScaleOp.filter(Assets.world, new BufferedImage(WIDTH,HEIGHT,Assets.world.getType()));
        g.drawImage(Assets.world,0,0,null);



        if (currState > 0) {
            gh.render(g);
        } else {
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }

    public void toggleGameState() { this.currState *= -1; }
    public int getGameState() { return this.currState; }

    public static void main(String[] args) {
        new Game();
    }

}
