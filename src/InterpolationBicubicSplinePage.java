import javax.swing.*;

public class InterpolationBicubicSplinePage extends Page
{
    public InterpolationBicubicSplinePage()
    {
        image = new JLabel(new ImageIcon("../assets/interpolation-bicubicspline.png"));
        image.setBounds(0, 0, 700, 925);

        page.add(image);
    }

    private JLabel image;
}
