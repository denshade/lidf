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

    public String[] getTagsForFileInDirectory(File file) throws IOException {
        File directory = file.getParentFile();
        File[] textfiles = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.toString().endsWith(".txt");
            }
        });
        IdfTfReport report = getCouples(textfiles);
        List<IdfCouple> couples = report.getPopularTermsForDocument(file);
        List<String> tags = new ArrayList<String>();
        for (IdfCouple couple : couples)
        {
            tags.add(couple.word);
        }
        int startTag = tags.size() - 10 < 0 ? 0 : tags.size() - 10;
        tags = tags.subList(startTag, tags.size());
        String[] a = new String[0];
        return tags.toArray(a);
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
                    Long l = terms.get(word).frequency.get(file);

                    if (terms.get(word).frequency != null)
                    {
                        if (l == null) {
                            l = 0L;
                        }
                        terms.get(word).frequency.put(file, l + 1);
                    } else {
                        System.out.println("d");
                    }

                } else {
                    IdfCouple couple = new IdfCouple();
                    couple.word = word;
                    couple.frequency.put(file, 1L);
                    couple.usedInDocuments.add(file);
                    terms.put(word, couple);
                }
            }
            line = reader.readLine();
        }
        reader.close();
    }
}
