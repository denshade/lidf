import java.io.File;
import java.util.*;

/**
 * Created by Lieven on 18/10/2015.
 */
public class IdfTfReport {
    Map<String, IdfCouple> terms = new HashMap<String, IdfCouple>();
    public int nrOfDocuments;

    public List<IdfCouple> getPopularTermsForDocument(File file) {
        List<IdfCouple> termsList = new ArrayList<IdfCouple>(terms.values());
        final int nrOfDocs = nrOfDocuments;
        final File currentFile = file;
        Collections.sort(termsList, new Comparator<IdfCouple>() {

            @Override
            public int compare(IdfCouple o1, IdfCouple o2) {
                double doc1 = o1.getTfIdf(currentFile, nrOfDocs);
                double doc2 = o2.getTfIdf(currentFile, nrOfDocs);
                if (doc1 < doc2) {
                    return -1;
                } else if (doc1 == doc2) {
                    return 0;
                }
                return 1;
            }

        });
        List<IdfCouple> selectedCouples = new ArrayList<IdfCouple>();
        for (IdfCouple couple : termsList) {
            if (couple.usedInDocuments.contains(file))
                selectedCouples.add(couple);
        }
        return selectedCouples;
    }

    /**
     *
     * @param file The selected file.
     * @param count The count of the amount of parameters.
     * @return String[] most popular terms sorted. Most important last.
     */
    public String[] getTopTermsForDocument(File file, int count) {
        List<IdfCouple> couples = getPopularTermsForDocument(file);
        List<String> tags = new ArrayList<String>();
        for (IdfCouple couple : couples) {
            tags.add(couple.word);
        }
        int startTag = tags.size() - count < 0 ? 0 : tags.size() - count;
        tags = tags.subList(startTag, tags.size());
        String[] a = new String[tags.size()];
        return tags.toArray(a);
    }

    public String toString(File currentFile)
    {
        String result = "";
        for (String word : terms.keySet())
        {
            double tfIdf = terms.get(word).getTfIdf(currentFile, nrOfDocuments);
            if (tfIdf != 0.0)
               result += word + " " + tfIdf + "\n";
        }
        return result;
    }
}
