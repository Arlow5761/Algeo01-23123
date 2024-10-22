import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import java.text.*;
import Algeo.*;
import Algeo.Inverse.InverseOBESolver;

public class InverseNormalPage extends Page
{
    public InverseNormalPage()
    {
        page.setLayout(null);

        JLabel backImage = new JLabel(new ImageIcon("../assets/inverse-normal.png"));
        backImage.setBounds(0, 0, 700, 925);
        
        page.add(backImage);

        page.revalidate();
        page.repaint();
    }
}
