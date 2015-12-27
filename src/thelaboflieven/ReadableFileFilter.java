package thelaboflieven;

import java.io.File;
import java.io.FileFilter;

public class ReadableFileFilter
{
    public static File[] getTextFiles(File directory)
    {
        return directory.listFiles(pathname -> pathname.toString().endsWith(".txt") || pathname.toString().endsWith(".docx"));
    }
}
