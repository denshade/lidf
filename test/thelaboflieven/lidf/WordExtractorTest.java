package thelaboflieven.lidf;

import org.junit.Test;

import java.io.File;
import java.util.Collections;

/**
 * Created by lveeckha on 27/12/2015.
 */
public class WordExtractorTest {

    @Test
    public void testProcessDocument() throws Exception {
        File file = new File("test.docx");
        WordExtractor extractor = new WordExtractor();
        extractor.process(file.getAbsolutePath());
        extractor.getString();
    }
}