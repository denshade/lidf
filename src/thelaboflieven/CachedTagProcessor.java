package thelaboflieven;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

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
        File[] textFiles = TextFileFilter.getTextFiles(directory);
        for (File textFile : textFiles)
            processDocument(textFile);
        if (keyValueMap.exists()) {
            //no try catch nothing here. I don't want to silently ignore the problem.
            FileOutputStream stream = new FileOutputStream(keyValueMap);
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);
            objectStream.writeObject(map);
            objectStream.close();
            stream.close();
        }

    }
    private void processDocument(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while(line !=  null)
        {
            StringTokenizer tokenizer = new StringTokenizer(line, " ,();[]");

            while(tokenizer.hasMoreTokens() )
            {
                String word = tokenizer.nextToken();
                TermFileIdentifier identifier = new TermFileIdentifier(word, file);
                if (map.get(identifier) == null)
                {
                    map.put(identifier, 1L);
                } else {
                    Long l = map.get(identifier);
                    map.put(identifier, l + 1);
                }
            }
            line = reader.readLine();
        }
        reader.close();
    }

}
