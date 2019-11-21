import java.awt.EventQueue;
import javax.swing.JFrame;
        
public class snake_game extends JFrame {
    
    public snake_game( ) {
        
        initUI( ) ;
    }
    
    private void initUI( ) {
        
        add(new playfield( )) ;
        
        setResizable(false) ;
        pack( ) ;
        
        setTitle("The Snake Game") ;
        setLocationRelativeTo(null) ;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
    }


    public static void main(String[] args) {
      
        EventQueue.invokeLater(() -> {
            JFrame ex = new snake_game( ) ;
            ex.setVisible(true);
        } ) ;
    }
    
}
