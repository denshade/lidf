package thelaboflieven;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import thelaboflieven.lidf.parser.DocumentTermsParser;
import thelaboflieven.lidf.parser.PdfTermsParser;
import thelaboflieven.lidf.parser.TextDocumentTermsParser;
import thelaboflieven.lidf.parser.WordDocumentTermsParser;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Lieven on 18/10/2015.
 */
public class DocumentSetIdfCalculator
{
    private static final Logger LOGGER = Logger.getLogger(DocumentSetIdfCalculator.class.getName());
    Map<String, IdfCouple> terms = new HashMap<>();

    public class Top3Terms
    {
        public File file;
        public String term1, term2, term3;
    }

    public IdfTfReport getCouples(final Collection<File> files) throws Exception {
        for (File current: files)
        {
            if (current.isDirectory())
                continue;
            String absolutePath = current.getAbsolutePath();
            DocumentTermsParser[] parsers = new DocumentTermsParser[] {
                    new TextDocumentTermsParser(),
                    new WordDocumentTermsParser(),
                    new PdfTermsParser()
            };
            for (DocumentTermsParser parser : parsers)
            {
                if (parser.canProcessFile(current))
                {
                    LOGGER.info("Parsing textfile: " + absolutePath);
                    parser.processDocument(current, terms);
                    break; // as soon as we find the first parser, quit.
                }
            }
        }
        IdfTfReport report = new IdfTfReport();
        report.terms = terms;
        report.nrOfDocuments = files.size();
        return report;
    }

    public Top3Terms[] getTagsForFilesInDirectory(File directory) throws Exception {
        Collection<File> textFiles = FileUtils.listFiles(directory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        LOGGER.info("Found " + textFiles.size() + " files");
        IdfTfReport report = getCouples(textFiles);
        List<Top3Terms> items = new ArrayList<>();
        for (File file : textFiles)
        {
            Top3Terms term = new Top3Terms();
            term.file = file;
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
                System.out.println(term.file.getAbsoluteFile()+";"+term.term1+";"+term.term2+";"+term.term3);
        }
    }
}
