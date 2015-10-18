import junit.framework.TestCase;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lieven on 18/10/2015.
 */
public class IdfTfReportTest extends TestCase {

    public void testGetIdfTfForTerm() throws Exception {

    }

    public void testGetPopularTermsForDocument() throws Exception {
        File file1 = File.createTempFile("test", ".txt");
        FileWriter writer = new FileWriter(file1);
        writer.write("this is a sample a");
        writer.close();
        File file2 = File.createTempFile("test", ".txt");
        writer = new FileWriter(file2);
        writer.write("example this is another example another example");
        writer.close();
        DocumentSetIdfCalculator calculator = new DocumentSetIdfCalculator();
        IdfTfReport report = calculator.getCouples(new File[]{file1, file2});
        List<IdfCouple> list = report.getPopularTermsForDocument(file2);
        file1.delete();
        file2.delete();
    }
}