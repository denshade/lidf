package thelaboflieven.lidf.parser;

import org.junit.Test;
import thelaboflieven.IdfCouple;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by lveeckha on 24/01/2016.
 */
public class TextTermParserTest {

    @Test
    public void testProcess() throws Exception {
        File f = new File("C:\\random wikipages\\6.txt");
        HashMap<String, IdfCouple> map = new HashMap<>();
        TextTermParser.process(f, new String(Files.readAllBytes(Paths.get(f.getAbsolutePath()))), map);
        assertNotNull(map.get("school"));
        assertNull(map.get("School"));

    }
}