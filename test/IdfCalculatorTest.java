import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.*;

public class IdfCalculatorTest {

    @Test
    public void testCalculateTfIdfForDocuments() throws Exception {
        File file1 = File.createTempFile("test", ".txt");
        FileWriter writer = new FileWriter(file1);
        writer.write("this is a sample a");
        writer.close();
        File file2 = File.createTempFile("test", ".txt");
        writer = new FileWriter(file2);
        writer.write("example this is another example another example");
        writer.close();

        double result = IdfCalculator.calculateTfIdfForDocuments("example", new File[]{file1, file2}, file2);
        assertEquals(0.9030, result, 0.001);
    }
}