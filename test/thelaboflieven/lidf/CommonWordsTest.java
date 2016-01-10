package thelaboflieven.lidf;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lieven on 30-12-2015.
 */
public class CommonWordsTest {

    @Test
    public void testIsCommonWord() throws Exception {
        assertTrue(CommonWords.isCommonWord("The"));
        assertTrue(CommonWords.isCommonWord("this"));
        assertTrue(CommonWords.isCommonWord("that"));
        assertTrue(CommonWords.isCommonWord("of"));
        assertTrue(CommonWords.isCommonWord(".!"));
        assertFalse(CommonWords.isCommonWord("duck"));
    }
}