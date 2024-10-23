import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SPLCramerPage extends Page
{
    public SPLCramerPage()
    {
        JLabel image = new JLabel(new ImageIcon("../assets/spl-cramer.png"));
        image.setBounds(0, 0, 700, 925);

        page.add(image);
    }
}
