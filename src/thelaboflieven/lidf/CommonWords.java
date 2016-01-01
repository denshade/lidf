package thelaboflieven.lidf;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Lieven on 29-12-2015.
 */
public class CommonWords
{
    private static Set<String> commonWords = null;

    private static void init() throws IOException {
        commonWords = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        BufferedReader reader = new BufferedReader(new FileReader("c:\\cache\\commonwords.txt"));
        String line = reader.readLine();
        while (line != null) {
            commonWords.add(line);
            line = reader.readLine();
        }
        reader.close();
    }
    public static boolean isCommonWord(String word) throws IOException {
        if (commonWords == null)
            init();
        if (!StringUtils.isAlphanumeric(word))
            return true;

        if (commonWords.contains(word))
            return true;

        return false;
    }
}
