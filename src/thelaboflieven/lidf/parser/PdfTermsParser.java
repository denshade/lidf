package thelaboflieven.lidf.parser;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import thelaboflieven.IdfCouple;
import thelaboflieven.lidf.CommonWords;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Lieven on 30-12-2015.
 */
public class PdfTermsParser implements DocumentTermsParser {
    @Override
    public void processDocument(final File file, Map<String, IdfCouple> terms) throws Exception {
        PDFParser parser;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;

        String parsedText;

        try {
            FileInputStream input = new FileInputStream(file);
            parser = new PDFParser(input);
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);

            StringTokenizer tokenizer = new StringTokenizer(parsedText, " ,();[]");

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
            pdDoc.close();
            cosDoc.close();
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (pdDoc != null)
                    pdDoc.close();
            } catch (Exception e1) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean canProcessFile(File filename) {
        return filename.getAbsolutePath().endsWith(".pdf");
    }
}
