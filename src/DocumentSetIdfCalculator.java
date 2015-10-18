import java.io.*;
import java.util.*;

/**
 * Created by Lieven on 18/10/2015.
 */
public class DocumentSetIdfCalculator
{
    Map<String, IdfCouple> terms = new HashMap<String, IdfCouple>();

    public IdfTfReport getCouples(File[] files) throws IOException {
        for (File current: files)
        {
            processDocument(current);
        }
        IdfTfReport report = new IdfTfReport();
        report.terms = terms;
        report.nrOfDocuments = files.length;
        return report;
    }

    private void processDocument(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while(line !=  null)
        {
            StringTokenizer tokenizer = new StringTokenizer(line);

            while(tokenizer.hasMoreTokens() )
            {
                String word = tokenizer.nextToken();
                if (terms.containsKey(word)) {
                    terms.get(word).usedInDocuments.add(file);
                    terms.get(word).frequency++;
                } else {
                    IdfCouple couple = new IdfCouple();
                    couple.word = word;
                    couple.frequency = 1;
                    couple.usedInDocuments.add(file);
                    terms.put(word, couple);
                }
            }
            line = reader.readLine();
        }
        reader.close();
    }
}
