
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
public class flappybird extends JPanel implements ActionListener, KeyListener{
    int boardW= 360, boardH= 640;
    Image backgroundImg;
    Image bird;
    Image TopPipe;
    Image BottomPipe;
    int birdX=boardW/8;
    int birdY=boardH/2;
    int birdW=34; int birdH=24;
    Timer gameloop;
    Bird bird1;
    int gravity=+1;
    int velocityY;
    
    // button
    JButton startButton;
    JButton retryButton= new JButton("RETRY");
    JButton quitButton= new JButton("QUIT");

    int pipeX= boardW;
    int pipeY=0; 
    int pipeW= 64;
    int pipeH=620;
    int velocityX= -4;

    int score=0;
    boolean gameOver= false;
    ArrayList<Pipe> pipes= new ArrayList<>();
    Timer pipeLoop;
    Random rand= new Random();
    JTextField scoreCount;
    JLabel gameOverLabel;
    class Bird{
        int x,y;
        int width, height;
        Image birdIMG;
        Bird(Image img, int x, int y, int width, int height){
            birdIMG= img;
            this.x= x; 
            this.y= y; 
            this.width= width; 
            this.height= height;
        }
    }
    class Pipe{
        int x,y;
        int width, height;
        Image pipeIMG;
        boolean passed= false;
        Pipe(Image img, int x, int y, int width, int height){
            pipeIMG= img;
            this.x= x; 
            this.y= y; 
            this.width= width; 
            this.height= height;
        }
    }
    flappybird(){
        setPreferredSize(new Dimension(boardW,boardH));
        setLayout(null); // important for manual positioning

        gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(Color.WHITE);          
        gameOverLabel.setBackground(Color.BLACK);          
        gameOverLabel.setOpaque(true);                    
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setBounds(60, boardH / 2 - 40, 240, 60);
        gameOverLabel.setVisible(false);
        add(gameOverLabel);
    
        scoreCount= new JTextField();
        scoreCount.setFont(new Font("Arial", Font.BOLD, 36));
        scoreCount.setForeground(Color.WHITE);          
        scoreCount.setBackground(Color.BLACK);          
        scoreCount.setOpaque(true);                    
        scoreCount.setHorizontalAlignment(SwingConstants.CENTER);
        scoreCount.setBounds(0,0, 50, 50);
        scoreCount.setVisible(true);
        add(scoreCount);
        startButton= new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 28));
        startButton.setBounds(100, boardH / 2 - 40, 160, 60);
        retryButton= new JButton("RETRY");
        retryButton.setFont(new Font("Arial", Font.BOLD, 28));
        retryButton.setBounds(100, 400, 160, 60);
        retryButton.setVisible(false);
        quitButton.setFont(new Font("Arial", Font.BOLD, 28));
        quitButton.setBounds(100, 480, 160, 60);
        quitButton.setVisible(false);
        scoreCount.setEditable(false);
        setFocusable(true);
        addKeyListener(this);
        backgroundImg= new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        bird= new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        TopPipe= new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        BottomPipe= new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        bird1= new Bird(bird, birdX, birdY, birdW, birdH);
        gameloop= new Timer(1000/50, this);
        pipeLoop= new Timer(1500, new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                    placePipes();
            }
        });
        add(startButton);
        add(retryButton);
        add(quitButton);
        startButton.addActionListener(this);
        retryButton.addActionListener(this);
        quitButton.addActionListener(this);
    }  
    public void resetGame() {
    score = 0;
    scoreCount.setText("0");

    bird1.y = boardH / 2;
    velocityY = 0;

    pipes.clear();

    gameOver = false;
    gameOverLabel.setVisible(false);
}
 
    public void startGame(){
        startButton.setVisible(false);
        gameloop.start();
        pipeLoop.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void placePipes(){
        int openingSpace= boardH/4;
        int randomPipeY= (int)(pipeY- pipeH/4 - Math.random()*(pipeH/2));
        Pipe topPipes= new Pipe(TopPipe,pipeX, randomPipeY, pipeW, pipeH);
        pipes.add(topPipes);
        Pipe bottomPipes= new Pipe(BottomPipe,pipeX,(topPipes.y+pipeH+openingSpace),pipeW,pipeH);
        pipes.add(bottomPipes);
    }
    public void move(){
        velocityY+=gravity;
        bird1.y= Math.max(bird1.y, 0);
        bird1.y += velocityY;
        if(bird1.y>boardH){
            gameOver= true;
        }
       for(int i=0; i< pipes.size(); i++){
            Pipe p= pipes.get(i);
            p.x += velocityX;
              if (
        bird1.x < p.x + p.width &&
        bird1.x + bird1.width > p.x &&
        bird1.y < p.y + p.height &&
        bird1.y + bird1.height > p.y
    ) {
        gameOver = true;
    }else if (!p.passed && bird1.x > p.x + p.width) {
        p.passed = true;
        score += 1; 
        scoreCount.setText(String.valueOf(score / 2)); 
    }
    }
    }
    public void draw(Graphics g){
        g.drawImage(backgroundImg, 0,0, boardW,boardH,null);
        g.drawImage(bird1.birdIMG, bird1.x, bird1.y, bird1.width,bird1.height,null);
        for(int i=0; i<pipes.size(); i++){
            Pipe pipe1= pipes.get(i);
            g.drawImage(pipe1.pipeIMG, pipe1.x, pipe1.y, pipe1.width, pipe1.height, null);
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== startButton){
            startGame();
            return;
        }
        if(e.getSource() == retryButton){
    resetGame();
    retryButton.setVisible(false);
    quitButton.setVisible(false);
    gameloop.start();
    pipeLoop.start();
    return;
}
    if(e.getSource()== quitButton){
        System.exit(0);
    }

        move();
        repaint();
        if(gameOver){
            pipeLoop.stop();
            gameloop.stop();
            gameOverLabel.setVisible(true);
            retryButton.setVisible(true);
            quitButton.setVisible(true);
        }
    }
    public void keyTyped(KeyEvent e) {  }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_SPACE){
            velocityY+=-20;
        }
    }
    public void keyReleased(KeyEvent e) {  }

}