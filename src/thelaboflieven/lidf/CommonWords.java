package thelaboflieven.lidf;

import org.apache.commons.lang.StringUtils;

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
            commonWords = new TreeSet<>(Files.readAllLines(Paths.get("commonwords.txt"), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isCommonWord(String word) throws IOException {
        return (!StringUtils.isAlphanumeric(word) || commonWords.contains(word));
    }
}
