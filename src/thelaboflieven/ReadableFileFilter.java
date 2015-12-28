package thelaboflieven;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadableFileFilter
{
    public static List<File> getFilesRecursively(File directory)
    {
        File[] files = directory.listFiles(pathname -> pathname.toString().endsWith(".txt") || pathname.toString().endsWith(".docx"));
        if (files == null)
        {
            return new ArrayList<>();
        }
        List<File> results = new ArrayList<>();
        results.addAll(Arrays.asList(files));
        for (File file : directory.listFiles(pathname -> pathname.isDirectory()))
        {
             results.addAll(getFilesRecursively(file));
        }
        return results;
    }
}
