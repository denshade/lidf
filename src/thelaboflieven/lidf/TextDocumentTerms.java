package thelaboflieven.lidf;

import thelaboflieven.IdfCouple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Lieven on 29-12-2015.
 */
public class TextDocumentTerms
{
    public static void processTextDocument(final File file, Map<String, IdfCouple> terms) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while(line !=  null)
        {
            StringTokenizer tokenizer = new StringTokenizer(line, " ,();[]");

            while(tokenizer.hasMoreTokens())
            {
                String word = tokenizer.nextToken();
                if (CommonWords.isCommonWord(word)) continue;

                if (terms.containsKey(word)) {
                    terms.get(word).bumpFrequency(file);
                } else {
                    IdfCouple couple = new IdfCouple();
                    couple.word = word;
                    couple.bumpFrequency(file);
                    terms.put(word, couple);
                }
            }
            line = reader.readLine();
        }
        reader.close();
    }
}
