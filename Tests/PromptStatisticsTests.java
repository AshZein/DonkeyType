package Tests;

import Model.PromptStatistics;
import Model.TypingStatistics;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PromptStatisticsTests {

    @Test
    void totalWordsTest() {
        TypingStatistics t = new TypingStatistics();
        t.changePhrase("hi, this is a test.");
        PromptStatistics s = t.getState();
        assertEquals(new ArrayList<>(Arrays.asList("hi", "this", "is", "a", "test")), s.getTotalWords());
        t.addCharacter('c', false);
        assertEquals(new ArrayList<>(Arrays.asList("hi")), s.getMistypedWords());
    }
}
