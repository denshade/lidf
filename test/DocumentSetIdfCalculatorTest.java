import junit.framework.TestCase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lieven on 18/10/2015.
 */
public class DocumentSetIdfCalculatorTest extends TestCase {

    public void testGetCouples() throws Exception {
        File file1 = File.createTempFile("test", ".txt");
        FileWriter writer = new FileWriter(file1);
        writer.write("this is a sample a");
        writer.close();
        File file2 = File.createTempFile("test", ".txt");
        writer = new FileWriter(file2);
        writer.write("example this is another example another example");
        writer.close();
        DocumentSetIdfCalculator calc = new DocumentSetIdfCalculator();
        Map<String, IdfCouple> map = calc.getCouples(new File[]{file1, file2}).terms;
        assertEquals(0.9030, map.get("example").getTfIdf(file2, 2), 0.001);
        file1.delete();
        file2.delete();
    }

    public void testDocumentRetrieval() throws IOException {
        File file1 = File.createTempFile("test", ".txt");
        FileWriter writer = new FileWriter(file1);
        writer.write("this is a sample a");
        writer.close();
        File file2 = File.createTempFile("test", ".txt");
        writer = new FileWriter(file2);
        writer.write("example this is another example another example");
        writer.close();
        DocumentSetIdfCalculator calc = new DocumentSetIdfCalculator();
        String[] str = calc.getTagsForFileInDirectory(file2);
        assertEquals("example", str[0]);
        file1.delete();
        file2.delete();
    }
}