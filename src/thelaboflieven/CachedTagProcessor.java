package thelaboflieven;

import thelaboflieven.lidf.TextExtractor;

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
        File[] textFiles = ReadableFileFilter.getTextFiles(directory);
        for (File textFile : textFiles)
            TextExtractor.processDocument(textFile, map);
        if (keyValueMap.exists()) {
            //no try catch nothing here. I don't want to silently ignore the problem.
            FileOutputStream stream = new FileOutputStream(keyValueMap);
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);
            objectStream.writeObject(map);
            objectStream.close();
            stream.close();
        }

    }
}
