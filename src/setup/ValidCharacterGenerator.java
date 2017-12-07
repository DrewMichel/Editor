package setup;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Andrew Michel on 12/6/2017.
 */
public class ValidCharacterGenerator
{
    public static final String VALID_CHARACTER_FILE_NAME = "Valid_Characters.txt";

    public static final int BASIC_ASCII_START_VALUE = 0, BASIC_ASCII_END_VALUE = 127;
    public static final int EXTENDED_ASCII_START_VALUE = 128, EXTENDED_ASCII_END_VALUE = 255;

    public static void main(String[] args)
    {
        PrintWriter pw = null;

        try
        {
            pw = new PrintWriter(VALID_CHARACTER_FILE_NAME);

            for(int i = BASIC_ASCII_START_VALUE; i <= BASIC_ASCII_END_VALUE; i++)
            {
                pw.printf("%-5c %7d%n", (char)i, i);
            }

            pw.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
