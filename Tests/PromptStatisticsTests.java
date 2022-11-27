package Tests;

import Model.PromptStatistics;
import Model.TypingStatistics;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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
    }
    @Test
    void mistypeWordsTest() {
        TypingStatistics t = new TypingStatistics();

        t.changePhrase("hi");
        t.addCharacter('a', false);
        t.changePhrase("bye");
        t.addCharacter('a', false);
        t.changePhrase("phrase");
        t.addCharacter('a', false);
        t.changePhrase("another");
        t.addCharacter('a', false);
        t.changePhrase("phrase");
        t.addCharacter('a', false);
        t.changePhrase("anotherone");
        t.addCharacter('x', false);
        t.changePhrase("aword");
        t.addCharacter('x', false);

        PromptStatistics s = t.getState();
        assertEquals(new HashSet<>(Arrays.asList("hi", "bye", "phrase", "another", "phrase", "anotherone", "aword")),
                new HashSet<>(s.getMistypedWords()));
    }
}
