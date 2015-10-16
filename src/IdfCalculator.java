import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Lieven on 16/10/2015.
 */
public class IdfCalculator {

    public double calculateTfIdfForDocuments(String s, File[] files) throws IOException {
        for (File file : files)
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine();
            while(line !=  null)
            {
                line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line);

                while(tokenizer.hasMoreTokens() )
                {
                    String word = tokenizer.nextToken();
                    if (s.equals(word)){
                        
                    }
                }
            }

            reader.close();
        }

    }
}
