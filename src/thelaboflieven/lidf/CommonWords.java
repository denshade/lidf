package thelaboflieven.lidf;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Lieven on 29-12-2015.
 */
public class CommonWords
{
    private static Set<String> commonWords;

    public static final String CACHE_COMMONWORDS_LOCATION = "c:\\cache\\commonwords.txt";

    static {
        try {
            File file = new File(CACHE_COMMONWORDS_LOCATION);
            if (file.exists())
            {
                commonWords = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
                commonWords.addAll(Files.readAllLines(Paths.get(CACHE_COMMONWORDS_LOCATION), StandardCharsets.UTF_8));
            } else
            {
                commonWords = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isCommonWord(String word) throws IOException {
        return (StringUtils.isNumeric(word) || !StringUtils.isAlphanumeric(word) || commonWords.contains(word));
    }
}
