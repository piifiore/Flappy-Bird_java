
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBIrd extends JPanel implements ActionListener {

    int boardWidth = 360;
    int boardHeight = 640;

    KeyHendler keyH = new KeyHendler();




    //Image
    Image backgroundImage;
    Image birdImage;
    Image topPipeImage;
    Image bottomPipeImage;

    //Bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;


    class Bird {

        int x = birdX;

        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;
        Bird(Image img) {
                this.img = img;
            }

    }


    //pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe
    {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        boolean passed = false;
        Image img;


        Pipe(Image img)
        {
            this.img = img;
        }
    }




    //Game Logic
    Bird bird;
    int velocityX = -4; // moves the Pipe
    int velocityY = 0;
    int gravity = 1;
    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    double score = 0.0;

    ArrayList<Pipe> pipes;
    Random rand = new Random();

     public FlappyBIrd()
     {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(keyH);

        // load image
        backgroundImage = new ImageIcon(getClass().getResource("file/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("file/flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("file/toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("file/bottompipe.png")).getImage();


        //Bird
        bird = new Bird(birdImage);
        pipes = new ArrayList<Pipe>();
        //Pipes Timer
         placePipesTimer = new Timer(1500, new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 placePipes();
             }
         });

        placePipesTimer.start();

        //Timer
         gameLoop = new Timer(1000/60, this);
         gameLoop.start();


     }

     public void placePipes()
     {
         int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
         int openSpace = boardHeight/4;


         Pipe topPipe = new Pipe(topPipeImage);
         topPipe.y = randomPipeY;
         pipes.add(topPipe);

         Pipe bottomPipe = new Pipe(bottomPipeImage);
         bottomPipe.y = topPipe.y + pipeHeight + openSpace;
         pipes.add(bottomPipe);

     }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
     {

         //background
        g.drawImage(backgroundImage, 0, 0, this.boardWidth, this.boardHeight, null);

         //Bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        //pieps
         for (int i = 0; i < pipes.size(); i++) {
             Pipe pipe = pipes.get(i);
             g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);

             //score
             g.setColor(Color.white);
             g.setFont(new Font("Ariel", Font.BOLD, 27));
                if (gameOver)
                {
                    g.drawString("GameOver:" + String.valueOf((int) score), 10, 35);

                }else {
                    g.drawString("Score:" + String.valueOf((int) score), 10, 35);
                }


         }
     }


     public void move() {
         //bird
         velocityY += gravity;
         bird.y += velocityY;
         bird.y = Math.max(bird.y, 0);

         if (keyH.keyPressed) {
             velocityY = -9;

         }


         //pipes
         for (int i = 0; i < pipes.size(); i++)
         {

             Pipe pipe = pipes.get(i);
             pipe.x += velocityX;

             if (bird.y > boardHeight) {
                 gameOver = true;
             }

             if(!pipe.passed && bird.x > pipe.x + pipe.width)
             {
                 pipe.passed = true;
                 score += 0.5;
             }

             if (collison(bird, pipe)) {
                 gameOver = true;
             }
         }

     }

     public boolean collison(Bird a, Pipe b)
     {
         return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
                 a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
                 a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
                 a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
     }


    @Override
    public void actionPerformed(ActionEvent e) {
         move();
         repaint();
         if (gameOver)
         {
             placePipesTimer.stop();
             gameLoop.stop();
         }


    }


}
