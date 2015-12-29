package thelaboflieven;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.exception.TikaException;
import thelaboflieven.lidf.TextDocumentTerms;
import thelaboflieven.lidf.WordDocumentTerms;

import java.io.*;
import java.util.*;

/**
 * Created by Lieven on 18/10/2015.
 */
public class DocumentSetIdfCalculator
{

    Map<String, IdfCouple> terms = new HashMap<>();

    public class Top3Terms
    {
        public String filename, term1, term2, term3;
    }

    public IdfTfReport getCouples(final Collection<File> files) throws Exception {
        for (File current: files)
        {
            if (current.getAbsolutePath().endsWith(".txt")){
                TextDocumentTerms.processTextDocument(current, terms);
            } else if (current.getAbsolutePath().endsWith(".docx")) {
                WordDocumentTerms.processWordDocument(current, terms);
            }
        }
        IdfTfReport report = new IdfTfReport();
        report.terms = terms;
        report.nrOfDocuments = files.size();
        return report;
    }

    public Top3Terms[] getTagsForFilesInDirectory(File directory) throws Exception {
        Collection<File> textFiles = FileUtils.listFiles(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        IdfTfReport report = getCouples(textFiles);
        List<Top3Terms> items = new ArrayList<>();
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

    public String[] getTagsForFileInDirectory(File file) throws Exception {
        File directory = file.getParentFile();
        Collection<File> textFiles = FileUtils.listFiles(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        IdfTfReport report = getCouples(textFiles);
        return report.getTopTermsForDocument(file, 10);
    }

    public static void main(String[] argv) throws Exception {
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
