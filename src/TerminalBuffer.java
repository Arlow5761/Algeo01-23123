import java.io.*;

import javax.swing.*;

public class TerminalBuffer
{
    static public short SAVE_SUCCESS = 1;
    static public short SAVE_ERROR = -1;
    static public short SAVE_CANCELED = 0;

    static public void ClearBuffer()
    {
        buffer = null;
    }

    static public void WriteBuffer(String string)
    {
        buffer = string;
    }

    static public String ReadBuffer()
    {
        return buffer;
    }

    static public short SaveBuffer()
    {
        JFileChooser fileChooser = new JFileChooser("../test");

        int res = fileChooser.showSaveDialog(null);

        if (res == JFileChooser.CANCEL_OPTION) return SAVE_CANCELED;

        if (res == JFileChooser.ERROR_OPTION) return SAVE_ERROR;

        try
        {
            File file = fileChooser.getSelectedFile();

            FileWriter writer = new FileWriter(file);
            writer.write(buffer);
            writer.close();
        }
        catch(Exception e)
        {
            return SAVE_ERROR;
        }

        return SAVE_SUCCESS;
    }

    private static String buffer = null;
}
