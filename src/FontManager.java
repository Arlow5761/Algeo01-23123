import java.awt.Font;
import java.io.File;

public class FontManager
{
    public Font GetMeanwhile(float size)
    {
        if (meanwhile == null)
        {
            SetMeanwhileFont();
        }

        return meanwhile.deriveFont(size);
    }

    private void SetMeanwhileFont()
    {
        File fontFile = new File("../assets/NDAUMO-CCMeanwhile.ttf");
        try
        {
            meanwhile = Font.createFont(0, fontFile);
        }
        catch (Exception e)
        {
            meanwhile = new Font("Comic Sans MS", 0, 12);
        }
    }

    private Font meanwhile;    
}
