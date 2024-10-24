import javax.swing.*;

public class ImageResizePage extends Page
{
    public ImageResizePage()
    {
        JLabel image = new JLabel(new ImageIcon("../assets/imageresizing.png"));
        image.setBounds(0, 0, 700, 925);

        page.add(image);

        page.revalidate();
        page.repaint();
    }    
}
