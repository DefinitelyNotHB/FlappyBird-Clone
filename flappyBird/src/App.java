import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class App{
    public static void main(String[] args) throws Exception {
    JFrame frame = new JFrame("Flappy Bird");   
    flappybird fb = new flappybird();   
    frame.add(fb);
    frame.pack();
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fb.requestFocus();
    frame.setVisible(true);
    }    
}
