
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class playfield  extends JPanel implements ActionListener{
    // Creating game parameters 
    private final int WIDTH = 300;
    private final int HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 50;
    private final int RAND_POS = 29;
    private final int DELAY = 140;
    
    private final int x[ ] = new int[ALL_DOTS];
    private final int y[ ] = new int[ALL_DOTS];
    
    private int dots;
    private int apple_x;
    private int apple_y;
    
    private boolean leftDirect = false;
    private boolean rightDirect = true;
    private boolean upDirect = false;
    private boolean downDirect = false;
    private boolean inGame = true;
    
    private Timer timer;
    private Image body;
    private Image apple;
    private Image head;
    
    public playfield() {
        initBoard();
    }

    private void initBoard() {
        
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {
        
        ImageIcon img_d = new ImageIcon("src/imgResources/body.png");
        body = img_d.getImage();
        
        ImageIcon img_a = new ImageIcon("src/imgResources/apple.png");
        apple = img_a.getImage();
        
        ImageIcon img_h = new ImageIcon("src/imgResources/head.png");
        head = img_h.getImage();
    }
    
    private void initGame() {
        
        dots = 3 ;
        for(int i =0; i < dots; i++) {
            x[i] = 50 - i * 10 ;
            y[i] = 50 ; 
        }
        
        locateApple( ) ;
        
        timer = new Timer(DELAY, this) ;
        timer.start( ) ;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g) ;
        
        doDrawing(g) ;
    }
    
    private void doDrawing(Graphics g) {
        if (inGame) {
            
            g.drawImage(apple, apple_x, apple_y, this) ;
            
            for (int j = 0 ; j < dots ; j++) {
                if (j == 0) {
                    
                    g.drawImage(head, x[j], y[j], this) ;
                    
                } else {
                    
                    g.drawImage(body, x[j], y[j], this) ;
                }
            }
            
            Toolkit.getDefaultToolkit( ).sync( ) ;
        } else {
            
            gameOver(g) ;
        }
    }
    
    private void gameOver(Graphics g) {
        
        String msg = "Game Over" ;
        Font small = new Font("Comic Sans" , Font.BOLD , 18) ;
        FontMetrics metr = getFontMetrics(small) ;
        
        g.setColor(Color.white) ;
        g.setFont(small) ;
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2) ;
    }
    
    private void checkApple ( ) {
        
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            
            dots++ ;
            locateApple( ) ;
        }
    }
    
    private void move( ) {
        
        for (int k = dots ; k > 0 ; k--) {
            x[k] = x[(k - 1)] ;
            y[k] = y[(k - 1)] ;
         }
        
        if (leftDirect) {
            x[0] -= DOT_SIZE ;
        }
        
        if (rightDirect) {
            x[0] += DOT_SIZE ;        
        }
        
        if (upDirect) {
        y[0] -= DOT_SIZE ;
       }
        
       if (downDirect) {
           y[0] += DOT_SIZE ;
       } 
    }
    
    private void checkCollision( ) {
        
        for (int w = dots ; w > 0 ; w--) {
            
            if ((w > 4) && (x[0] == x[w]) && (y[0] == y[w])) {
                inGame = false ;
            }
        }
        
        if (y[0] >= HEIGHT) {
            inGame = false;
        }
        
        if (y[0] < 0) {
            inGame = false;
        }
        
        if (x[0] >= WIDTH) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        
        if (!inGame) {
            timer.stop( ) ;
        }
    }
    
    private void locateApple( ) {
        
        int r = (int) (Math.random( ) * RAND_POS) ;
        apple_x = ((r * DOT_SIZE)) ;
        
        r = (int) (Math.random( ) * RAND_POS) ;
        apple_y = ((r * DOT_SIZE)) ;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (inGame) {
            
            checkApple( ) ;
            checkCollision( ) ;
            move( ) ;
        }
        
        repaint( ) ;
    }
    
    private class TAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e ) {
            
            int key = e.getKeyCode( ) ;
            
            if ((key == KeyEvent.VK_LEFT) && (!rightDirect)) {
                leftDirect = true ;
                upDirect = false ;
                downDirect = false ;
            }
            
            if ((key == KeyEvent.VK_RIGHT) && (!leftDirect)) {
                rightDirect = true ;
                upDirect = false ;
                downDirect = false ;      
            }
            
            if ((key == KeyEvent.VK_UP) && (!downDirect)) {
                upDirect = true ;
                rightDirect = false ;
                leftDirect = false ;
            }
            
            if ((key == KeyEvent.VK_DOWN) && (!upDirect)) {
                downDirect = true ;
                rightDirect = false ;
                leftDirect = false ;
                        
                       
            }
        }
    }
    
    }
    

