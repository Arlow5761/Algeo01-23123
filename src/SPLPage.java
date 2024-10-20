import javax.swing.*;

public class SPLPage extends Page
{
    public SPLPage(String backpage)
    {
        image = new JLabel(new ImageIcon(backpage));
        image.setBounds(0, 0, 700, 925);

        page.add(image);
    }
    
    protected JLabel image;
}
