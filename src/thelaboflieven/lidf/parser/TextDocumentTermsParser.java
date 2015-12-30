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
            TextTermParser.process(file, line, terms);
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
