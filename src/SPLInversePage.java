import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SPLInversePage extends Page
{
    public SPLInversePage()
    {
        JLabel image = new JLabel(new ImageIcon("../assets/spl-inverse.png"));
        image.setBounds(0, 0, 700, 925);

        page.add(image);
    }
}
