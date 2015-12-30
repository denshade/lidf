package thelaboflieven;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by Lieven on 29-12-2015.
 */
public class Top3CsvWriter
{

    private static final Logger LOGGER = Logger.getLogger(Top3CsvWriter.class.getName());

    public static void write(final File directory, File destinationFile) throws Exception {

        DocumentSetIdfCalculator calculator = new DocumentSetIdfCalculator();
        Writer csvWriter = new BufferedWriter(new FileWriter(destinationFile));
        DocumentSetIdfCalculator.Top3Terms[] terms = calculator.getTagsForFilesInDirectory(directory);

        csvWriter.write("Filename;SizeInBytes;LastModifiedAt;Topterm1;Topterm2;Topterm3\n");
        for (DocumentSetIdfCalculator.Top3Terms term : terms){
            if (term != null)
            {
                File file = new File(term.filename);
                csvWriter.write(term.filename + ";"
                        + file.length() + ";"
                        + file.lastModified() + ";"
                        + nn(term.term1) + ";"
                        + nn(term.term2) + ";"
                        + nn(term.term3) + "\n"
                );
            }
        }
        csvWriter.close();
    }
    private static String nn(String term)
    {
        if (term == null) return "";
        return term;
    }

    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("Usage: <directory> <csvFile>");
            System.exit(2);
        }
        File directory = new File(args[0]);
        File outputfile = new File(args[1]);
        LOGGER.info("Writing to file " + outputfile);
        if (!directory.exists()) {
            System.out.println("File not found "+ directory);
            System.exit(3);
        }
        if (outputfile.exists())
        {
            LOGGER.warning("Output File already exists! " + outputfile);
            //System.exit(4);
        }
        try {
            Top3CsvWriter.write(directory, outputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
