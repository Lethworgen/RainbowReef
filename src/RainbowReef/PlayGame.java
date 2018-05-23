package RainbowReef;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.text.AttributedString;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.embed.swing.JFXPanel;

public class PlayGame extends JPanel{ 
    private int lives;
    private int score;
    private int level;
    private int Octos;
    private int BossHealth;
    //These vals are used to set the ampunt of octos to be killed on the level to
    //unlock a wall
    private int SlayKey, KeyAmount, LockAmount;
    private final int FPS =60;
    private ArrayList<Brick> Bricks;
    private ArrayList<Star> Stars;
    private Shell Player;
    private JFrame window;
    private Graphics2D g2d;
    private BufferedImage buf;
    private final int ScreenHeight = 500, ScreenWidth = 680;
    private Image background, WallImg, ShellImg, StarImg, StarBrickImg, LifeBrickImg, 
                Coral1Img, Coral2Img, Coral3Img, Coral4Img, Coral5Img, Coral6Img,
                Coral7Img, OctoImg, BigOctoImg, MiniStarImg;
    private GameEvents PlayerEvent;
    private int LeftBounds = 20, RightBounds = ScreenWidth -20, UpperBounds = 20;
    Media gameMusic;
    private ArrayList<Integer> score_int;
    private ArrayList<String> score_string;
    private boolean newgame;

    
    //initalize resources here
    private void ResourcesInit()
    {
        //load resources
        try{
            background = ImageIO.read(new File("resources/Background.png"));
            WallImg = ImageIO.read(new File("resources/Wall.png"));
            ShellImg = ImageIO.read(new File("resources/Shell.png"));
            StarImg = ImageIO.read(new File("resources/Pop.png"));
            OctoImg = ImageIO.read(new File("resources/Bigleg_small.png"));
            BigOctoImg = ImageIO.read(new File("resources/Bigleg.png"));
            StarBrickImg = ImageIO.read(new File("resources/Block_split.png"));
            LifeBrickImg = ImageIO.read(new File("resources/Block_life.png"));
            Coral1Img = ImageIO.read(new File("resources/Block1.png"));
            Coral2Img = ImageIO.read(new File("resources/Block2.png"));
            Coral3Img = ImageIO.read(new File("resources/Block3.png"));
            Coral4Img = ImageIO.read(new File("resources/Block4.png"));
            Coral5Img = ImageIO.read(new File("resources/Block5.png"));
            Coral6Img = ImageIO.read(new File("resources/Block6.png"));
            Coral7Img = ImageIO.read(new File("resources/Block7.png"));
            MiniStarImg = ImageIO.read(new File("resources/PopMini.png"));
            
            //initialize music
            final JFXPanel fxPanel = new JFXPanel();
            gameMusic = new Media(new File("resources/Music.mp3").toURI().toString());
        } catch (Exception e) {
            System.out.print(e.getStackTrace() + " Error loading resources");
        }
        
        //initialized arrayList for scoreboard
        score_int = new ArrayList<Integer>();
        score_string = new ArrayList<String>();
        
        for(int i = 0; i < 8; i++){
            score_int.add(0);
            score_string.add("N/A");
        }
        
        //create the window we use
        window = new JFrame();
        window.addWindowListener(new WindowAdapter() {
        });
        window.add(this);
        window.pack();
        window.setVisible(true);
        window.setTitle("Rainbow Reef!");
        window.setSize(ScreenWidth + 6, ScreenHeight);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //enable keylistening
        window.getContentPane().setFocusable(true);
    }
    
    //initialize objects for level one
    private void LevelOneInit()
    {
        this.score = 0;
        this.lives = 3;
        this.level = 1;
        this.Octos = 2;
        this.BossHealth = 0;
        this.SlayKey = 0;
        this.KeyAmount = 10;
        this.LockAmount = 0;
        
        //Initialize the shell first
        Player = new Shell(ShellImg, 280, 400, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, LeftBounds, RightBounds);
        PlayerEvent = new GameEvents();
        PlayerEvent.addObserver(Player);
        KeyControl playerKeys = new KeyControl(PlayerEvent);
        
        window.getContentPane().requestFocusInWindow();
        window.getContentPane().addKeyListener(playerKeys);
        
        Bricks = new ArrayList<Brick>();
        Stars = new ArrayList<Star>();
        
        
        Bricks.add(new Brick(OctoImg, 140, 60, 4, 1000));
        Bricks.add(new Brick(OctoImg, 500, 60, 4, 1000));
        
        Bricks.add(new Brick(LifeBrickImg, 260, 20, 3, 0));
        Bricks.add(new Brick(LifeBrickImg, 380, 20, 3, 0));
        
        for(int i = 0; i < 16; i ++)
        {
            Bricks.add(new Brick(Coral2Img, 20 + 40 * i, 180, 1, 100));
        }
        
        for(int i = 0; i < 7; i ++)
        {
            Bricks.add(new Brick(Coral1Img, 20 + 40 * i, 160, 1, 200));
        }
        
        for(int i = 0; i < 7; i ++)
        {
            Bricks.add(new Brick(Coral1Img, 380 + 40 * i, 160, 1, 200));
        }
        
        for(int i = 0; i < 7; i ++)
        {
            Bricks.add(new Brick(Coral3Img, 20 + 40 * i, 140, 1, 300));
        }
        
        for(int i = 0; i < 7; i ++)
        {
            Bricks.add(new Brick(Coral3Img, 380 + 40 * i, 140, 1, 300));
        }
        
        for(int i = 0; i < 8; i ++)
        {
            Bricks.add(new Brick(WallImg, 340, 20 + 20*i, 0, 0));
        }
        
        for(int i = 0; i < 8; i ++)
        {
            Bricks.add(new Brick(WallImg, 300, 20 + 20*i, 0, 0));
        }
        
        Stars.add(new Star(StarImg, 320, 240, this.level, LeftBounds, RightBounds, UpperBounds));
    }
    
    private void LevelTwoInit()
    {
        this.level = 2;
        this.Octos = 3;
        
        //Initialize the shell first
        Player = new Shell(ShellImg, 280, 400, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, LeftBounds, RightBounds);
        PlayerEvent = new GameEvents();
        PlayerEvent.addObserver(Player);
        KeyControl playerKeys = new KeyControl(PlayerEvent);
        
        window.getContentPane().requestFocusInWindow();
        window.getContentPane().addKeyListener(playerKeys);
        
        Bricks = new ArrayList<Brick>();
        Stars = new ArrayList<Star>();
        
        Bricks.add(new Brick(OctoImg, 100, 60, 4, 1000));
        Bricks.add(new Brick(OctoImg, 540, 60, 4, 1000));
        Bricks.add(new Brick(OctoImg, 320, 60, 4, 1000));
        
        Bricks.add(new Brick(LifeBrickImg, 20, 20, 3, 0));
        Bricks.add(new Brick(LifeBrickImg, ScreenWidth - 60, 20, 3, 0));
        
        for(int i = 0; i < 5; i ++)
        {
            Bricks.add(new Brick(Coral3Img, 20 + 40 * i, 180, 1, 300));
        }
        
        for(int i = 0; i < 4; i ++)
        {
            Bricks.add(new Brick(Coral3Img, 260 + 40 * i, 180, 1, 300));
        }
        
        for(int i = 0; i < 5; i ++)
        {
            Bricks.add(new Brick(Coral3Img, 460 + 40 * i, 180, 1, 300));
        }
        
        Bricks.add(new Brick(Coral4Img, 20, 160, 1, 400));
        Bricks.add(new Brick(Coral4Img, 60, 160, 1, 400));
        Bricks.add(new Brick(StarBrickImg, 100, 160, 2, 0));
        Bricks.add(new Brick(Coral4Img, 140, 160, 1, 400));
        Bricks.add(new Brick(Coral4Img, 180, 160, 1, 400));
        
        for(int i = 0; i < 4; i ++)
        {
            Bricks.add(new Brick(Coral4Img, 260 + 40 * i, 160, 1, 400));
        }
        
        Bricks.add(new Brick(Coral4Img, 460, 160, 1, 400));
        Bricks.add(new Brick(Coral4Img, 500, 160, 1, 400));
        Bricks.add(new Brick(StarBrickImg, 540, 160, 2, 0));
        Bricks.add(new Brick(Coral4Img, 580, 160, 1, 400));
        Bricks.add(new Brick(Coral4Img, 620, 160, 1, 400));
        
        for(int i = 0; i < 5; i ++)
        {
            Bricks.add(new Brick(Coral5Img, 20 + 40 * i, 140, 1, 500));
        }
        
        for(int i = 0; i < 4; i ++)
        {
            Bricks.add(new Brick(Coral5Img, 260 + 40 * i, 140, 1, 500));
        }
        
        for(int i = 0; i < 5; i ++)
        {
            Bricks.add(new Brick(Coral5Img, 460 + 40 * i, 140, 1, 500));
        }
        
        for(int i = 0; i < 9; i ++)
        {
            Bricks.add(new Brick(WallImg, 220, 20 + 20*i, 0, 0));
        }
        
        for(int i = 0; i < 9; i ++)
        {
            Bricks.add(new Brick(WallImg, 420, 20 + 20*i, 0, 0));
        }
        
        Stars.add(new Star(StarImg, 320, 240, this.level, LeftBounds, RightBounds, UpperBounds));
    }
    
    private void LevelThreeInit()
    {
        this.level = 3;
        this.Octos = 9;
        this.BossHealth = 3;
        this.SlayKey = 0;
        this.KeyAmount = 6;
        this.LockAmount = 16;
        
        //Initialize the shell first
        Player = new Shell(ShellImg, 280, 400, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, LeftBounds, RightBounds);
        PlayerEvent = new GameEvents();
        PlayerEvent.addObserver(Player);
        KeyControl playerKeys = new KeyControl(PlayerEvent);
        
        window.getContentPane().requestFocusInWindow();
        window.getContentPane().addKeyListener(playerKeys);
        
        Bricks = new ArrayList<Brick>();
        Stars = new ArrayList<Star>();
        
        for(int i = 0; i < 7; i ++)
        {
            Bricks.add(new Brick(WallImg, 260, 20 + 20*i, 0, 0));
        }
        
        Bricks.add(new Brick(WallImg, 300, 140, 0, 0));
        Bricks.add(new Brick(WallImg, 340, 140, 0, 0));
        
        for(int i = 0; i < 7; i ++)
        {
            Bricks.add(new Brick(WallImg, 380, 20 + 20*i, 0, 0));
        }
        
        Bricks.add(new Brick(BigOctoImg, 300, 20, 5, 5000));
        Bricks.add(new Brick(OctoImg, 300, 100, 4, 1000));
        Bricks.add(new Brick(OctoImg, 340, 100, 4, 1000));
        
        Bricks.add(new Brick(OctoImg, 40, 60, 4, 1000));
        Bricks.add(new Brick(OctoImg, 120, 60, 4, 1000));
        Bricks.add(new Brick(OctoImg, 200, 60, 4, 1000));
        
        Bricks.add(new Brick(OctoImg, 440, 60, 4, 1000));
        Bricks.add(new Brick(OctoImg, 520, 60, 4, 1000));
        Bricks.add(new Brick(OctoImg, 600, 60, 4, 1000));
        
        for(int i = 0; i < 16; i ++)
        {
            Bricks.add(new Brick(Coral5Img, 20 + 40 * i, 180, 1, 500));
        }
        
        for(int i = 0; i < 3; i ++)
        {
            Bricks.add(new Brick(Coral6Img, 20 + 40 * i, 160, 1, 600));
        }
        
        Bricks.add(new Brick(StarBrickImg, 140, 160, 2, 0));
        
        for(int i = 0; i < 8; i ++)
        {
            Bricks.add(new Brick(Coral6Img, 180 + 40 * i, 160, 1, 600));
        }
        
        for(int i = 0; i < 3; i ++)
        {
            Bricks.add(new Brick(Coral6Img, 540 + 40 * i, 160, 1, 600));
        }
        
        Bricks.add(new Brick(StarBrickImg, 500, 160, 2, 0));
        
        Bricks.add(new Brick(LifeBrickImg, 220, 140, 3, 0));
        Bricks.add(new Brick(LifeBrickImg, 420, 140, 3, 0));
        
        for(int i = 0; i < 5; i ++)
        {
            Bricks.add(new Brick(Coral7Img, 20 + 40 * i, 140, 1, 700));
        }
        
        for(int i = 0; i < 5; i ++)
        {
            Bricks.add(new Brick(Coral7Img, 460 + 40 * i, 140, 1, 700));
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (buf == null) {
            buf = (BufferedImage) createImage(ScreenWidth, ScreenHeight);
        }
        Graphics2D gtemp = (Graphics2D) g;
        g2d = buf.createGraphics();
        super.paintComponent(gtemp);
        
        g2d.drawImage(background, 20, 20, this);
        
        //draw borders
        for(int i = 0; i < ScreenWidth/WallImg.getWidth(this); i++)
        {
            g2d.drawImage(WallImg, 40*i, 0, this);
        }
        for(int i = 0; i < ScreenHeight/WallImg.getHeight(this) - 1; i++)
        {
            g2d.drawImage(WallImg, -20, 20 + 20*i, this);
        }
        for(int i = 0; i < ScreenHeight/WallImg.getHeight(this) - 1; i++)
        {
            g2d.drawImage(WallImg, RightBounds, 20 + 20*i, this);
        }
        
        //draw bricks/octos;
        for(int i = 0; i < Bricks.size(); i ++)
        {
            g2d.drawImage(Bricks.get(i).getImg(), Bricks.get(i).getX(), Bricks.get(i).getY(), this);
        }
        //paint stars
        for(int i = 0; i < Stars.size(); i++)
        {
            paintRotatedImg(Stars.get(i).getImg(), Stars.get(i).getAngle(), Stars.get(i).getX(), Stars.get(i).getY());
        }
        //add the shell
        g2d.drawImage(Player.getImg(), Player.getX(), Player.getY(), this);
        
        g2d.drawImage(MiniStarImg, 450, -2, this);
        
        gtemp.drawImage(buf, 0, 0, this);
        String r;
        int result;
        String s = "x" + this.lives;
        gtemp.setColor(Color.ORANGE);
        gtemp.setFont((new Font("Arial Black", Font.BOLD, 16)));
        gtemp.drawString(s, 474, 16);
        s = "Score: " + this.score;
        gtemp.setColor(Color.CYAN);
        gtemp.drawString(s, 520, 16);
        
        //Boss's healthBar
        if(BossHealth > 0)
        {
            gtemp.setColor(Color.DARK_GRAY);
            gtemp.fillRect(300, 26, 80, 8);
            gtemp.setColor(Color.RED);
            gtemp.fillRect(301, 27, (78 / 3) * BossHealth, 6);
        }
        
        if(lives == 0){ //Print Game Over Screen
            gtemp.setFont((new Font("Arial Black", Font.PLAIN, 25)));
            gtemp.setColor(Color.RED);
            gtemp.drawString("GAME OVER!", 250, 75);
        }
        
        if(this.BossHealth == 0 && this.Octos ==0){ //Print Game Over Screen
            gtemp.setFont((new Font("Arial Black", Font.PLAIN, 25)));
            gtemp.setColor(Color.RED);
            gtemp.drawString("YOU SAVED THE REEF!", 250, 75);
        }
        gtemp.dispose();
    }
    
    
    public void paintRotatedImg(Image img, double angle, int x, int y) {
        double rAngle = Math.toRadians(angle);
        int h = (int) (img.getWidth(null) * Math.abs(Math.sin(rAngle)) + img.getHeight(null) * Math.abs(Math.cos(rAngle)));
        int w = (int) (img.getHeight(null) * Math.abs(Math.sin(rAngle)) + img.getWidth(null) * Math.abs(Math.cos(rAngle)));

        BufferedImage bimgTemp = bufferedImageConverter(img);

        AffineTransform old = g2d.getTransform();

        AffineTransform at = AffineTransform.getRotateInstance(rAngle, x + img.getWidth(this) / 2, y + img.getHeight(this) / 2);
        g2d.setTransform(at);
        g2d.drawImage(bimgTemp, x, y, null);
        g2d.setTransform(old);
    }
    
    public BufferedImage bufferedImageConverter(Image img) {
        BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bimg.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return bimg;
    }
    
    private void musicThreadLoop() {
        Thread loop = new Thread() {
            @Override
            public void run()
            {
                MediaPlayer mp = new MediaPlayer(gameMusic);
                mp.play();
            }
        };
        loop.start();
    }
    
    private void TimerLoop()
    {
        //previous time previous loop started
        long currTime;
        //target time hoping to aim for each loop
        long targetTime = 1000000000 / FPS;

        while ((lives > 0) && Octos > 0) {
            currTime = System.nanoTime();

            UpdateGame();
            repaint();

            //sleep for any remaining time
            //if statement for potential negative time, then don't sleep
            if ((currTime - System.nanoTime() + targetTime) > 0) {
                try {
                    Thread.sleep((currTime - System.nanoTime() + targetTime) / 1000000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    //update stars/shell here
    private void UpdateGame()
    {
        Player.MoveUpdate();
        
        if(Stars.isEmpty())
        {
            Stars.add(new Star(StarImg, 320, 240, this.level, LeftBounds, RightBounds, UpperBounds));
        }
        
        CollisionDetector col = new CollisionDetector();
        boolean north, east, south, west, hit;
        int eastnum, southnum, westnum, northnum;
        for(int i = 0; i < Stars.size(); i++)
        {
            north = false;
            east = false;
            south = false;
            west = false;
            for(int j = 0; j < Bricks.size(); j ++)
            {
                hit = false;
                //commented section  is old code;  testing collision  changes in 
                //uncommented section below this
                northnum = col.NorthCol(Stars.get(i), Bricks.get(j));
                southnum = col.SouthCol(Stars.get(i), Bricks.get(j));
                westnum = col.WestCol(Stars.get(i), Bricks.get(j));
                eastnum = col.EastCol(Stars.get(i), Bricks.get(j));
                
                //test to find the biggest collision edge. it should be the side
                //of the collision we want
                if(northnum + southnum + westnum + eastnum != 0)
                {
                    hit = true;
                    if((northnum >= southnum) && (northnum >= eastnum) && (northnum >= westnum))
                    {
                        if(westnum == eastnum)
                            north = true;
                        else if(northnum == eastnum)
                        {
                            north = true;
                            east = true;
                        }
                        else if(northnum == westnum)
                        {
                            north = true;
                            east = true;
                        }
                        else
                            north = true;
                    }
                    else if((southnum >= northnum) && (southnum >= eastnum) && (southnum >= westnum))
                    {
                        if(westnum == eastnum)
                            south = true;
                        else if(southnum == eastnum)
                        {
                            south = true;
                            east = true;
                        }
                        else if(southnum == westnum)
                        {
                            south = true;
                            east = true;
                        }
                        else
                            south = true;
                    }
                    else if((westnum >= eastnum) && (westnum >= northnum) && (westnum >= southnum))
                    {
                        if(southnum == northnum)
                        {
                            west = true;
                        }
                        else if (westnum == northnum)
                        {
                            west = true;
                            north = true;
                        }
                        else if (westnum == southnum)
                        {
                            west = true;
                            south = true;
                        }
                        else
                            west = true;
                    }
                    else if((eastnum >= westnum) && (eastnum >= northnum) && (eastnum >= southnum))
                    {
                        if(southnum == northnum)
                        {
                            east = true;
                        }
                        else if (eastnum == northnum)
                        {
                            east = true;
                            north = true;
                        }
                        else if (eastnum == southnum)
                        {
                            east = true;
                            south = true;
                        }
                        else
                            east = true;
                    }
                }
                
                if(hit)
                {
                    if(Bricks.get(j).getType() == 1)
                    {
                        this.score += Bricks.get(j).getPoints() * Stars.size();
                        Bricks.remove(j);
                        j--;
                    }
                    else if(Bricks.get(j).getType() == 2)
                    {
                        Stars.add(new Star(StarImg, Stars.get(i).getX(), Stars.get(i).getY(), this.level, LeftBounds, RightBounds, UpperBounds));
                        Bricks.remove(j);
                        j--;
                    }
                    else if(Bricks.get(j).getType() == 3)
                    {
                        this.lives++;
                        Bricks.remove(j);
                        j--;
                    }
                    else if(Bricks.get(j).getType() == 4)
                    {
                        this.Octos--;
                        this.score += Bricks.get(j).getPoints();
                        Bricks.remove(j);
                        j--;
                        SlayKey++;
                        
                        if(SlayKey == KeyAmount)
                        {
                            for(int k = 0; k < LockAmount; k++)
                            {
                                Bricks.remove(0);
                            }
                            SlayKey = 0;
                        }
                    }
                    else if(Bricks.get(j).getType() == 5)
                    {
                        this.BossHealth--;
                        if(this.BossHealth <= 0)
                        {
                            this.Octos--;
                            this.score += Bricks.get(j).getPoints();
                            Bricks.remove(j);
                            j--;
                        }
                    }
                }
            }
            Stars.get(i).update(north, east, south, west);
            
            if(col.ShellCol(Stars.get(i), Player))
            {
                double starMid = Stars.get(i).getX() + (1/2)*Stars.get(i).getWidth();
                double playerMid = Player.getX() + (1/2)*Player.getWidth();
                double adjust = starMid - playerMid - 20;
                if(Stars.get(i).getY() < Player.getY())
                {
                    Stars.get(i).hitShellTop(adjust);
                }
                else if(adjust > 0)
                {
                    Stars.get(i).hitShellRight();
                }
                else
                {
                    Stars.get(i).hitShellLeft();
                }
            }
            
            if(Stars.get(i).getY() > ScreenHeight)
            {
                Stars.remove(i);
                i--;
                if(Stars.isEmpty())
                    lives--;
            }
            
        }
    }
    
    private void ScoreTable(){
        JTextField enterName;
        enterName = new JTextField(10);
        boolean high = false;
        int position = 0;
        
        JFrame frame = new JFrame("High Scores");
        frame.setLocation(ScreenWidth/4, ScreenHeight/4);
        frame.setSize(200, 400);
        frame.setLayout(new GridLayout(0,2));
        frame.setVisible(true);
        
        for(int i = 0; i< score_int.size(); i++)
        {
            if(score_int.get(i) < this.score)
            {
                break;
            }
            position++;
        }
        
        for(int j = 0; j < 8; j++){
            if(j == position){
                enterName = new JTextField(20);
                enterName.setEditable(true);
                enterName.setFocusable(true);
                frame.add(enterName);

                JTextField scoreT = new JTextField(6);
                scoreT.setEditable(false);
                scoreT.setText("" + score);
                frame.add(scoreT);
                
                high = true;
                position = j;
            }else if(!high){
                JTextField name = new JTextField(20);
                name.setEditable(false);
                name.setText("" + score_string.get(j));
                frame.add(name);

                JTextField scoreT = new JTextField(6);
                scoreT.setEditable(false);
                scoreT.setText("" + score_int.get(j));
                frame.add(scoreT);
            }else{
                JTextField name = new JTextField(20);
                name.setEditable(false);
                name.setText("" + score_string.get(j-1));
                frame.add(name);

                JTextField scoreT = new JTextField(6);
                scoreT.setEditable(false);
                scoreT.setText("" + score_int.get(j-1));
                frame.add(scoreT);
            }
        }
        frame.addWindowListener( new WindowAdapter(){
            
        });
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JButton one = new JButton("Play Again");
        frame.add(one);
        frame.pack();
        frame.setResizable(false);
        
        
        frame.setVisible(true);
        
        one.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                newgame = true;
            }
        });
        
        String s = new String();
        
        while(!newgame)
        {
            s = enterName.getText();
        }
        
        score_int.add(position, score);
        this.score_string.add(position, s);
        
        frame.dispose();
    }
    
    //starts the game from the beggining 
    private void NewGame()
    {
        newgame = false;
        this.LevelOneInit();
        this.TimerLoop();
        //go to next level if more lives remain, after level one;
        if(lives > 0)
        {
            this.LevelTwoInit();
            this.TimerLoop();
        }
        if(lives > 0)
        {
            this.LevelThreeInit();
            this.TimerLoop();
        }
        //print scoreboard screen w/ replay button that runs this fxn again.
        this.ScoreTable();
        
        this.NewGame();
    }
    
    public static void main(String[] args) {
        PlayGame game = new PlayGame();
        game.ResourcesInit();
        game.musicThreadLoop();
        game.NewGame();
    }
}
