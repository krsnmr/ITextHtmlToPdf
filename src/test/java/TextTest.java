import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TextTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void text_replace_isCorrect() {

        String templateString = "The ${animal} jumped over the ${target}.";
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("animal", "quick brown fox");
        valuesMap.put("target", "lazy dog");

        StringHelper helper = new StringHelper();
        String res = helper.ReplaceTemplateText(templateString, valuesMap);
        assertEquals("The quick brown fox jumped over the lazy dog.", res);
    }
}
