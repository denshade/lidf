import java.io.File;
import java.util.*;

/**
 * Created by Lieven on 18/10/2015.
 */
public class IdfTfReport
{
    Map<String, IdfCouple> terms = new HashMap<String, IdfCouple>();
    public int nrOfDocuments;

    public double getIdfTfForTerm(String term)
    {
        return terms.get(term).getIdf(nrOfDocuments);
    }

    public List<IdfCouple> getPopularTermsForDocument(File file)
    {
        List<IdfCouple> termsList = new ArrayList<IdfCouple>(terms.values());
        final int nrOfDocs = nrOfDocuments;
        Collections.sort(termsList, new Comparator<IdfCouple>() {

            @Override
            public int compare(IdfCouple o1, IdfCouple o2) {
                double doc1 = o1.getTfIdf(nrOfDocs);
                double doc2 = o2.getTfIdf(nrOfDocs);
                if (doc1 < doc2) {
                    return -1;
                } else if(doc1 == doc2) {
                    return 0;
                }
                return 1;
            }

        });
        return termsList;
    }
}
