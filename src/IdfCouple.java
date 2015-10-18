import java.io.File;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Lieven on 18/10/2015.
 */
public class IdfCouple
{
    public String word;
    public long frequency;
    public Set<File> usedInDocuments = new TreeSet<File>();

    public double getIdf(int totalNrDocuments)
    {
        return Math.log10((double)totalNrDocuments/(double)usedInDocuments.size());
    }

    public double getTfIdf(int totalNrDocuments)
    {
        return frequency * getIdf(totalNrDocuments);
    }

}
