package thelaboflieven.lidf;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Lieven on 29-12-2015.
 */
public class CommonWords
{
    public static boolean isCommonWord(String word)
    {
        //todo move into dictionary
        String[] commonWords = new String[] {
                "zal", "nog", "als","de","een", "het", "is", "van", "in", "er", "�", "tussen", "u", "null"
        };

        if (!StringUtils.isAlphanumeric(word))
            return true;
        for (String commonWord : commonWords)
        {
            if (word.equalsIgnoreCase(commonWord))
                return true;
        }
        return false;
    }
}
