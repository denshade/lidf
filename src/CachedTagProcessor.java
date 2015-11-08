import java.io.*;
import java.util.HashMap;

/**
 * Created by Lieven on 8-11-2015.
 */
public class CachedTagProcessor
{
    private HashMap<TermFileIdentifier, Long> map = new HashMap<TermFileIdentifier, Long>();
    public void buildMapForDirectory(File directory) throws IOException, ClassNotFoundException {
        File keyValueMap = new File("keyValueMap.cache");

        if (keyValueMap.exists()) {
            //no try catch nothing here. I don't want to silently ignore the problem.
            FileInputStream stream = new FileInputStream(keyValueMap);
            ObjectInputStream objectStream = new ObjectInputStream(stream);
            map = (HashMap<TermFileIdentifier, Long>)objectStream.readObject();
            objectStream.close();
            stream.close();
        }

        buildOrUpdate;
        saveMap;

    }
}
