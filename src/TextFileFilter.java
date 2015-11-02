import java.io.File;
import java.io.FileFilter;

public class TextFileFilter
{
    public static File[] getTextFiles(File directory)
    {
        return directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.toString().endsWith(".txt");
            }
        });
    }
}
