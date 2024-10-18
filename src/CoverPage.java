import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CoverPage extends Page
{    
    public CoverPage()
    {
        image = new JLabel(new ImageIcon("../assets/cover.png"));
        image.setLocation(0, 0);
        image.setSize(image.getPreferredSize());

        nextButton = new JButton(">");
        nextButton.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        nextButton.setBounds(650, 0, 50, 925);
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Unload();
                Page.Load(new MenuPage());
            }
        });

        page.add(nextButton);
        page.add(image);
    }

    private JButton nextButton;
    private JLabel image;
}
