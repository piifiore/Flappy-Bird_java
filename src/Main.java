import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;



public class Main {
    public static void main(String[] args)
    {
        //Frame Dimension
        int boardWidth = 360;
        int boardHeight = 640;

        //FRAME
        JFrame frame = new JFrame("FlappyBird");

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        FlappyBIrd flappyBIrd = new FlappyBIrd();
        frame.add(flappyBIrd);
        frame.pack(); // does not take the title bar into account in the frame's pixel count
        flappyBIrd.requestFocus();
        frame.setVisible(true);

    }
}