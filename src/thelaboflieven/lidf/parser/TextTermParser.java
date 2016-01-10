package thelaboflieven.lidf.parser;

import thelaboflieven.IdfCouple;
import thelaboflieven.lidf.CommonWords;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Lieven on 30-12-2015.
 */
public class TextTermParser {

    public static void process(final File file, String parsedText, Map<String, IdfCouple> terms) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(parsedText, " ,.();[]\n\t");

        while(tokenizer.hasMoreTokens()) {
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
    }
}
