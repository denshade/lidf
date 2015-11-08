import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.*;

public class CachedTagProcessorTest {

    @Test
    public void testBuildMapForDirectory() throws Exception {
        String home = System.getProperty("user.home");
        File directory = new File(home+"\\testDir");
        directory.mkdir();
        File file1 = new File(home + "\\testDir\\file1.txt");
        FileWriter writer = new FileWriter(file1);
        writer.write("this is a sample a");
        writer.close();
        File file2 = new File(home + "\\testDir\\file2.txt");
        writer = new FileWriter(file2);
        writer.write("example this is another example another example");
        writer.close();
        CachedTagProcessor calc = new CachedTagProcessor();
        calc.buildMapForDirectory(file2.getParentFile());
        file1.delete();
        file2.delete();
        directory.delete();
    }
}