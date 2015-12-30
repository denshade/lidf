package thelaboflieven.lidf.parser;

import org.apache.tika.exception.TikaException;
import thelaboflieven.IdfCouple;
import thelaboflieven.lidf.CommonWords;

import java.io.File;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Lieven on 29-12-2015.
 */
public class WordDocumentTermsParser implements DocumentTermsParser
{

    @Override
    public void processDocument(File file, Map<String, IdfCouple> terms) throws Exception {
        thelaboflieven.lidf.WordExtractor extr = new thelaboflieven.lidf.WordExtractor();
        try {
            extr.process(file.getAbsolutePath());
        } catch(TikaException e)
        {
            return;
        }
        for (String line: extr.getString().split("\n")){
            StringTokenizer tokenizer = new StringTokenizer(line, " ,();[]");

            while(tokenizer.hasMoreTokens() )
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
        }
    }

    @Override
    public boolean canProcessFile(File filename) {
        String absolutePath = filename.getAbsolutePath();
        return absolutePath.endsWith(".docx");
    }

}
