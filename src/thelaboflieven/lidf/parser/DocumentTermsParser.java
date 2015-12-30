package thelaboflieven.lidf.parser;

import thelaboflieven.IdfCouple;

import java.io.File;
import java.util.Map;

/**
 * Created by Lieven on 30-12-2015.
 */
public interface DocumentTermsParser {
    void processDocument(File file, Map<String, IdfCouple> terms) throws Exception;
    boolean canProcessFile(File filename);
}
