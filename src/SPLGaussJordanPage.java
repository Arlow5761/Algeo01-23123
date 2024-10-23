import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SPLGaussJordanPage extends Page
{
    public SPLGaussJordanPage()
    {
        JLabel image = new JLabel(new ImageIcon("../assets/spl-gaussjordan.png"));
        image.setBounds(0, 0, 700, 925);

        page.add(image);
    }    
}
