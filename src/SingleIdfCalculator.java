import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Lieven on 16/10/2015.
 */
public class SingleIdfCalculator {

    public static double calculateTfIdfForDocuments(String s, File[] files, File selectedFile) throws IOException {
        long n = files.length;
        long termFrequency = 0;
        long numberOfDocumentsWithTerm = 0;
        for (File file : files)
        {
            long termInFiles = doesDocumentHaveTerm(s, file);
            if (termInFiles > 0)
            {
                if (file.equals(selectedFile))
                {
                    termFrequency = termInFiles;
                }
                numberOfDocumentsWithTerm++;
            }
        }
        double idf = Math.log10((double)n/(double)numberOfDocumentsWithTerm);
        return termFrequency * idf;
    }

    private static long doesDocumentHaveTerm(String s, File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        long termFrequency = 0;
        String line = reader.readLine();
        while(line !=  null)
        {
            StringTokenizer tokenizer = new StringTokenizer(line);

            while(tokenizer.hasMoreTokens() )
            {
                String word = tokenizer.nextToken();
                if (s.equals(word)){
                    termFrequency++;
                }
            }
            line = reader.readLine();
        }
        reader.close();
        return termFrequency;
    }
}
