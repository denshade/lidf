package thelaboflieven.lidf.parser;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import thelaboflieven.IdfCouple;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

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

            TextTermParser.process(file, parsedText, terms);
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
