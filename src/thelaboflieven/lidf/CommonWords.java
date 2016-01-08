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

    static {
        try {
            File file = new File("c:\\cache\\commonwords.txt");
            if (file.exists())
            {
                commonWords = new TreeSet<>(Files.readAllLines(Paths.get("c:\\cache\\commonwords.txt"), StandardCharsets.UTF_8));
            } else
            {
                commonWords = new TreeSet<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isCommonWord(String word) throws IOException {
        return (!StringUtils.isAlphanumeric(word) || commonWords.contains(word));
    }
}
