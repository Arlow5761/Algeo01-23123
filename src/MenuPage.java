import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

public class MenuPage extends Page
{
    public MenuPage()
    {
        page.setLayout(null);

        title = new JLabel("Table of Contents", JLabel.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 48));
        title.setBounds(0, 200, 700, 50);

        Font font = new Font("Comic Sans MS", Font.PLAIN, 18);

        spl = new JButton("Linear System of Equations");
        spl.setFont(font);
        spl.setBounds(0, 300, 700, 50);
        spl.setContentAreaFilled(false);
        spl.setBorderPainted(false);

        determinant = new JButton("Determinant of a Matrix");
        determinant.setFont(font);
        determinant.setBounds(0, 350, 700, 50);
        determinant.setContentAreaFilled(false);
        determinant.setBorderPainted(false);

        inverse = new JButton("Inverse of a Matrix");
        inverse.setFont(font);
        inverse.setBounds(0, 410, 700, 50);
        inverse.setContentAreaFilled(false);
        inverse.setBorderPainted(false);

        interpolation = new JButton("Interpolation of Points in 2D & 3D space");
        interpolation.setFont(font);
        interpolation.setBounds(0, 470, 700, 50);
        interpolation.setContentAreaFilled(false);
        interpolation.setBorderPainted(false);

        regression = new JButton("Regression of Points in N-Dimensional Space");
        regression.setFont(font);
        regression.setBounds(0, 530, 700, 50);
        regression.setContentAreaFilled(false);
        regression.setBorderPainted(false);

        woawgirl = new JLabel(new ImageIcon(new ImageIcon("../assets/woaw.png").getImage().getScaledInstance(150, 315, Image.SCALE_SMOOTH)));
        woawgirl.setBounds(500, 600, 150, 300);

        page.add(title);
        page.add(spl);
        page.add(determinant);
        page.add(inverse);
        page.add(interpolation);
        page.add(regression);
        page.add(woawgirl);
        page.repaint();
    }

    private JLabel title;
    private JButton spl;
    private JButton determinant;
    private JButton inverse;
    private JButton interpolation;
    private JButton regression;
    private JLabel woawgirl;
}
