package thelaboflieven;

import java.io.*;
import java.util.*;

/**
 * Created by Lieven on 18/10/2015.
 */
public class DocumentSetIdfCalculator
{

    Map<String, IdfCouple> terms = new HashMap<String, IdfCouple>();

    private class Top3Terms
    {
        String filename;
        String term1;
        String term2;
        String term3;
    }

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

    public Top3Terms[] getTagsForFilesInDirectory(File directory) throws IOException {
        File[] textFiles = TextFileFilter.getTextFiles(directory);
        IdfTfReport report = getCouples(textFiles);
        List<Top3Terms> items = new ArrayList<Top3Terms>();
        for (File file : textFiles)
        {
            Top3Terms term = new Top3Terms();
            term.filename = file.getCanonicalPath();
            String[] top3Terms = report.getTopTermsForDocument(file, 3);
            if (top3Terms.length > 0)
                term.term1 = top3Terms[0];
            if (top3Terms.length > 1)
                term.term2 = top3Terms[1];
            if (top3Terms.length > 2)
                term.term3 = top3Terms[2];
            items.add(term);
        }
        return items.toArray(new Top3Terms[items.size()]);
    }

    public String[] getTagsForFileInDirectory(File file) throws IOException {
        File directory = file.getParentFile();
        File[] textFiles = TextFileFilter.getTextFiles(directory);
        IdfTfReport report = getCouples(textFiles);
        return report.getTopTermsForDocument(file, 10);
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
                if (terms.containsKey(word)) {
                    terms.get(word).bumpFrequency(file);
                } else {
                    IdfCouple couple = new IdfCouple();
                    couple.word = word;
                    couple.bumpFrequency(file);
                    terms.put(word, couple);
                }
            }
            line = reader.readLine();
        }
        reader.close();
    }

    public static void main(String[] argv) throws IOException {
        if (argv.length != 1)
        {
            System.out.println("Usage: <directory>");
            System.exit(2);
        }
        DocumentSetIdfCalculator calculator = new DocumentSetIdfCalculator();
        File file = new File(argv[0]);
        if (!file.exists()) {
            System.out.println("File not found "+ file);
            System.exit(3);
        }
        Top3Terms[] terms = calculator.getTagsForFilesInDirectory(file);
        System.out.println("filename;topterm1;topterm2;topterm3");
        for (Top3Terms term : terms){
            if (term != null)
                System.out.println(term.filename+";"+term.term1+";"+term.term2+";"+term.term3);
        }
    }
}
