package thelaboflieven.lidf.parser;

import thelaboflieven.IdfCouple;
import thelaboflieven.lidf.CommonWords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Lieven on 29-12-2015.
 */
public class TextDocumentTermsParser implements DocumentTermsParser {
    @Override
    public void processDocument(final File file, Map<String, IdfCouple> terms) throws Exception {
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

    @Override
    public boolean canProcessFile(File filename) {
        String absolutePath = filename.getAbsolutePath();
        return (absolutePath.endsWith(".txt") || absolutePath.endsWith(".xml"));
    }
}
