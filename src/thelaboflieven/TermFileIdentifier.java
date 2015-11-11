package thelaboflieven;

import java.io.File;

/**
 * Created by Lieven on 8-11-2015.
 */
public class TermFileIdentifier
{
    public TermFileIdentifier(String term, File file) {
        this.term = term;
        this.file = file;
    }

    public String term;
    public File file;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TermFileIdentifier that = (TermFileIdentifier) o;

        if (!file.equals(that.file)) return false;
        if (!term.equals(that.term)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = term.hashCode();
        result = 31 * result + file.hashCode();
        return result;
    }
}
