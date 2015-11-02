import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Lieven on 18/10/2015.
 */
public class IdfCouple
{
    public String word;
    private Map<File,Long> frequency = new HashMap<File, Long>();
    public Set<File> usedInDocuments = new TreeSet<File>();

    public double getIdf(int totalNrDocuments)
    {
        return Math.log10((double)totalNrDocuments/(double)usedInDocuments.size());
    }

    public Long getFrequencyForFile(File currentFile)
    {
        if (!frequency.containsKey(currentFile) || frequency.get(currentFile) == 0)
            return 0L;
        return frequency.get(currentFile);
    }
    /*
    public Long getBooleanFrequencyForFile(File currentFile)
    {
        if (!frequency.containsKey(currentFile) || frequency.get(currentFile) == 0)
            return 0L;
        return frequency.get(currentFile) > 0? 1L:0L;
    }*/
    public Long getLogFrequencyForFile(File currentFile)
    {
        if (!frequency.containsKey(currentFile) || frequency.get(currentFile) == 0)
            return 0L;
        return frequency.get(currentFile) ;
    }

    public void bumpFrequency(File file)
    {
        Long l = getFrequencyForFile(file);

        if (frequency != null)
        {
            if (l == null) {
                l = 0L;
            }
            frequency.put(file, l + 1);
        }
        usedInDocuments.add(file);
    }

    public double getTfIdf(File currentFile, int totalNrDocuments)
    {
        if (!frequency.containsKey(currentFile)) return 0;
        return getLogFrequencyForFile(currentFile) * getIdf(totalNrDocuments);
    }

}
