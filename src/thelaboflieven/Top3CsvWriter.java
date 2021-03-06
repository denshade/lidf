package thelaboflieven;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Lieven on 29-12-2015.
 */
public class Top3CsvWriter {

    private static final Logger LOGGER = Logger.getLogger(Top3CsvWriter.class.getName());

    public static void write(final List<File> directories, File destinationFile) throws Exception {

        DocumentSetIdfCalculator calculator = new DocumentSetIdfCalculator();
        Writer csvWriter = new BufferedWriter(new FileWriter(destinationFile));

        csvWriter.write("Filename;SizeInBytes;LastModifiedAt;Topterm1;Topterm2;Topterm3\n");
        for (File directory : directories) {
            DocumentSetIdfCalculator.Top3Terms[] terms = calculator.getTagsForFilesInDirectory(directory);
            for (DocumentSetIdfCalculator.Top3Terms term : terms) {
                if (term != null) {
                    String tags = nn(term.term1) + ";"
                            + nn(term.term2) + ";"
                            + nn(term.term3);
                    csvWriter.write(term.file.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\") + ";"
                            + term.file.length() + ";"
                            + term.file.lastModified() + ";"
                            + tags + "\n"
                    );
                }
            }
        }
        csvWriter.close();
    }

    private static String nn(String term) {
        if (term == null) return "";
        return term;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: <directory> <csvFile>");
            System.exit(2);
        }
        File directory = new File(args[0]);
        File outputfile = new File(args[1]);
        LOGGER.info("Writing to file " + outputfile);
        if (!directory.exists()) {
            System.out.println("File not found " + directory);
            System.exit(3);
        }
        if (outputfile.exists()) {
            LOGGER.warning("Output File already exists! " + outputfile);
            //System.exit(4);
        }
        try {
            Top3CsvWriter.write(Arrays.asList(directory), outputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
