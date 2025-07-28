import javax.swing.*;
import java.awt.*;

public class Gui {
    public static void main(String[] args) throws Exception
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        JPanel containerPanel = new JPanel();

        int yrender = 16;
        int xrender = 16;
        buttonPanel.setLayout(new GridLayout(xrender,yrender));
        for (int i=0; i < (yrender*xrender); i++) {
            String sigma = Integer.toString(i);
            buttonPanel.add(new JButton(sigma));
        }
        buttonPanel.setPreferredSize(new Dimension(55*xrender, 55*yrender));
        containerPanel.add(buttonPanel);

        frame.getContentPane().add(containerPanel);
        frame.pack();
        frame.setVisible(true);
    }
}


