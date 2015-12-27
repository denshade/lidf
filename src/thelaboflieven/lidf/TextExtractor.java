package thelaboflieven.lidf;

import thelaboflieven.TermFileIdentifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by lveeckha on 24/12/2015.
 */
public class TextExtractor
{
    public static void processDocument(final File file, Map<TermFileIdentifier, Long> map) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while(line !=  null)
        {
            StringTokenizer tokenizer = new StringTokenizer(line, " ,();[]");

            while(tokenizer.hasMoreTokens() )
            {
                String word = tokenizer.nextToken();
                TermFileIdentifier identifier = new TermFileIdentifier(word, file);
                if (map.get(identifier) == null)
                {
                    map.put(identifier, 1L);
                } else {
                    Long l = map.get(identifier);
                    map.put(identifier, l + 1);
                }
            }
            line = reader.readLine();
        }
        reader.close();
    }
}
