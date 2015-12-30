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
        TextTermParser.process(file, extr.getString(), terms);
    }

    @Override
    public boolean canProcessFile(File filename) {
        String absolutePath = filename.getAbsolutePath();
        return absolutePath.endsWith(".docx");
    }

}
